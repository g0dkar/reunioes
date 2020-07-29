FROM adoptopenjdk:11-jre-hotspot

MAINTAINER Rafael M. Lins <rafael.lins777@gmail.com>

ARG finalName=backend-questionaries-1.0.0

LABEL service=backend-questionaries

# Updates the image
COPY src/docker/install_updates /install_updates

# rwxrwxrwx
RUN ["chmod", "777", "/install_updates"]
RUN ["/install_updates"]

# Custom java.security (no DNS cache, non-blocking and safe SecureRandom)
# Reference: https://tersesystems.com/blog/2015/12/17/the-right-way-to-use-securerandom/
COPY src/docker/java.security ${JAVA_HOME}/conf/security/java.security

# Port we will expose
EXPOSE 8080/tcp
EXPOSE 7091/tcp

# Copy the fat jar into the image
COPY target/${finalName}.jar /app.jar

# Copy the launcher script
COPY src/docker/launch_app /launch_app

# Use 512MB of memory and enable JMX
ENV MEM_SIZE_KB=524288
ENV JMX_PORT=7091

# r-xr--r--
RUN ["chmod", "544", "/launch_app"]

# Entry point
ENTRYPOINT ["/launch_app", "/app.jar"]
