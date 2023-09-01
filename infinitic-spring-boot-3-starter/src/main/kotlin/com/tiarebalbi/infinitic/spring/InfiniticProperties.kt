package com.tiarebalbi.infinitic.spring

import org.springframework.boot.context.properties.ConfigurationProperties
import java.io.Serializable

/**
 * Represents the configuration properties for Infinitic Aut-Configuration.
 *
 * The InfiniticProperties class is a data class with properties that can be configured during application startup using
 * `@ConfigurationProperties` annotation. It has three main properties: `enabled`, `worker`, and `client`.
 *
 * @property enabled Indicates whether Infinitic is enabled or not. The default value is `true`.
 * @property worker Represents the properties of a worker.
 * @property client Represents the properties of a client.
 *
 * @author tiare.balbi
 */
@ConfigurationProperties(prefix = "infinitic")
data class InfiniticProperties(
    /**
     * Indicates whether infinitic is enabled or not. The Default value is true.
     */
    val enabled: Boolean = true,
    /**
     * Represents the properties of a worker.
     */
    val worker: WorkerProperties = WorkerProperties(enabled = false),
    /**
     * Represents the properties of a client.
     */
    val client: ClientProperties = ClientProperties(enabled = true)
) : Serializable {

    /**
     * Represents the properties of a worker.
     *
     * @property enabled Indicates whether the worker is enabled or not. The Default value is true.
     * @property executionMode The execution mode for the initialization of the worker
     * @property configuration The configuration file for the worker.
     */
    data class WorkerProperties(
        /**
         * The execution mode of the worker. The Default value is ExecutionMode.ASYNC.
         */
        val enabled: Boolean = true,

        /**
         * The execution mode for the initialization of the worker
         */
        val executionMode: ExecutionMode = ExecutionMode.ASYNC,

        /**
         * The configuration file for the worker.
         */
        val configuration: String? = null
    ) : Serializable {

        /**
         * Represents the execution mode of a process.
         *
         * The ExecutionMode enum class provides two options:
         *   - SYNC: Indicates synchronous execution mode, where the process is initialized sequentially.
         *   - ASYNC: Indicates asynchronous execution mode, where the process is initialized concurrently.
         */
        enum class ExecutionMode {
            SYNC,
            ASYNC;

            fun isAsync(): Boolean {
                return this == ASYNC
            }
        }
    }

    /**
     * Represents the properties of a client.
     *
     * @property enabled Indicates if the client is enabled or not.
     * @property configuration The configuration file for the client.
     */
    data class ClientProperties(
        /**
         * Indicates if the client is enabled or not. The Default value is true.
         */
        val enabled: Boolean = true,
        /**
         * The configuration file for the client.
         */
        val configuration: String? = null
    ) : Serializable
}
