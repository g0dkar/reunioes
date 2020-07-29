package br.com.rafaellins.reunioes.util

import ai.responde.backend.questionnaries.util.Environment.CONTAINERIZED
import ai.responde.backend.questionnaries.util.SetupEnvironment.disableLogos
import ai.responde.backend.questionnaries.util.SetupEnvironment.setupSpringProfile
import br.com.rafaellins.reunioes.util.SetupEnvironment.disableLogos
import br.com.rafaellins.reunioes.util.SetupEnvironment.setupSpringProfile
import java.nio.file.Files
import java.nio.file.Path

/**
 * Contains useful setup functions like [setupSpringProfile] and [disableLogos].
 */
internal object SetupEnvironment {
    /** Logger. */
    private val log = logger()

    /** Current [Region] extracted from the Environment Variables. */
    private val CURRENT_REGION = Region.fromEnv()

    /** Current [Environment] extracted from the Environment Variables. */
    private val CURRENT_ENV = Environment.fromEnv()

    /**
     * Sets the `spring.profiles.active` system property given a [Region] and [Environment].
     */
    fun setupSpringProfile(
        region: Region = CURRENT_REGION,
        environment: Environment = CURRENT_ENV
    ) {
        val activeProfiles = mutableListOf(
            environment.suffix,
            region.suffix,
            "${environment.suffix}-${region.suffix}"
        )

        if (isContainerized()) {
            activeProfiles.add(CONTAINERIZED.suffix)
            activeProfiles.add("${CONTAINERIZED.suffix}-${environment.suffix}")
            activeProfiles.add("${CONTAINERIZED.suffix}-${environment.suffix}-${region.suffix}")
        }

        setProperty("spring.profiles.active", activeProfiles.joinToString())
    }

    /**
     * Disables printing of logos on startup (Spring Banner and jooq Logo - reduces log pollution).
     */
    fun disableLogos() {
        setProperty("org.jooq.no-logo", "true")
        setProperty("spring.main.banner-mode", "off")
    }

    /**
     * Disables the favicon.
     */
    fun disableFavIcon() {
        setProperty("spring.mvc.favicon.enabled", "false")
    }

    /**
     * Returns `true` if it is detected this app is running in a container.
     *
     * From: https://stackoverflow.com/questions/20010199/how-to-determine-if-a-process-runs-inside-lxc-docker
     *
     * **Note:** Only Docker and LXC supported.
     */
    private fun isContainerized(): Boolean =
        Path.of("/proc/${ProcessHandle.current().pid()}/cgroup")
            .takeIf { Files.exists(it) }
            ?.let { String(Files.readAllBytes(it)).lines() }
            ?.any { it.matches(Regex("\\d+:cpu:/(docker|lxc)/.+")) }
            ?: false

    /**
     * Sets a property and prints a log line about it.
     */
    private fun setProperty(property: String, value: String) =
        log.info("Setting $property = \"$value\"")
            .also { System.setProperty(property, value) }
}

/**
 * Regions where this app might be deployed (loosely based on what AWS offers)
 */
internal enum class Region(val suffix: String) {
    /** European Union */
    EU("eu"),

    /** United States */
    US("us"),

    /** South America */
    SA("sa");

    companion object {
        val DEFAULT = EU
        const val REGION_ENV = "RSP_REGION"

        /**
         * Tries to extract which region this app is running based on the [REGION_ENV] environment variable.
         * Returns [DEFAULT] if it's not present or has an invalid value.
         */
        fun fromEnv(): Region =
            System.getenv(REGION_ENV)
                ?.let {
                    try {
                        valueOf(it.toUpperCase().trim())
                    } catch (e: IllegalArgumentException) {
                        null
                    }
                }
                ?: DEFAULT
    }
}

/**
 * Environments where this app might be deployed
 */
internal enum class Environment(val suffix: String) {
    /** Localhost development */
    TEST("test"),

    /** Localhost development */
    LOCAL("local"),

    /** Added when the app is detected to be Containerized */
    CONTAINERIZED("containerized"),

    /** Development */
    DEVELOPMENT("dev"),

    /** Staging or Pre-Prod */
    STAGING("staging"),

    /** Production */
    LIVE("live");

    companion object {
        val DEFAULT = LOCAL
        const val ENVIRONMENT_ENV = "RSP_ENV"

        /**
         * Tries to extract which environment this app is running based on the [ENVIRONMENT_ENV] environment variable.
         * Returns [DEFAULT] if it's not present or has an invalid value.
         */
        fun fromEnv(): Environment =
            System.getenv(ENVIRONMENT_ENV)
                ?.let {
                    try {
                        valueOf(it.toUpperCase().trim())
                    } catch (e: IllegalArgumentException) {
                        null
                    }
                }
                ?: DEFAULT
    }
}
