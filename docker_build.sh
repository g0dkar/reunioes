#!/usr/bin/env bash

# https://vaneyckt.io/posts/safer_bash_scripts_with_set_euxo_pipefail/
set -Eeuo pipefail

build=false
push=false

# Script adapted from: https://github.com/fmahnke/shell-semver/blob/master/increment_version.sh
while getopts ":bp" Option; do
  # shellcheck disable=SC2220
  case $Option in
  b) build=true ;;
  p) push=true ;;
  esac
done

if [[ $build == true ]]; then
  echo ">>> Running maven build..."
  ./mvnw clean verify package
fi

# WARNING: If JAVA_HOME is not set, mvnw will ALWAYS print a warning about it. That's the reason we grep the output.
ARTIFACT_ID=$(./mvnw help:evaluate -Dexpression=project.artifactId -q -DforceStdout | grep -E '^[a-z][a-z0-9]+(-[a-z0-9]+)*$')
VERSION=$(./mvnw help:evaluate -Dexpression=project.version -q -DforceStdout | grep -E '^[0-9]+\.[0-9]+\.[0-9]+$')
REPOSITORY="g0dkar"

echo ">>> Building ${REPOSITORY}/$ARTIFACT_ID:$VERSION..."

docker build \
  -t ${REPOSITORY}/${ARTIFACT_ID}:${VERSION} \
  -t ${REPOSITORY}/${ARTIFACT_ID}:latest \
  --build-arg finalName=${ARTIFACT_ID}-${VERSION} .

if [[ $push == true ]]; then
  echo ">>> Pushing ${REPOSITORY}/$ARTIFACT_ID:$VERSION..."
  docker push ${REPOSITORY}/${ARTIFACT_ID}:${VERSION}
  docker push ${REPOSITORY}/${ARTIFACT_ID}:latest
fi
