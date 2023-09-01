package com.tiarebalbi.infinitic.spring

import io.infinitic.clients.InfiniticClient
import io.infinitic.pulsar.config.ClientConfig
import io.infinitic.workers.InfiniticWorker
import io.infinitic.workers.config.WorkerConfig
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.InitializingBean
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.io.ResourceLoader

/**
 * Configuration class for Infinitic Auto-Configuration.
 *
 * This class is responsible for configuring the Infinitic library based on the provided application properties.
 * It creates an InfiniticClient and configures it with the specified client configuration file.
 *
 * This class is annotated with `@Configuration` to indicate that it should be processed by Spring's application context.
 * It is also annotated with `@ConditionalOnProperty` to conditionally enable this configuration based on the value of the
 * "infinitic.enabled" property. If the property is not present or has a value other than "true", this configuration will
 * not be loaded.
 *
 * Configuration properties can be provided using the `InfiniticProperties` class, which is enabled with the
 * `@EnableConfigurationProperties` annotation.
 *
 * The InfiniticAutoConfiguration class requires two constructor arguments: `InfiniticProperties` and `ResourceLoader`.
 *
 * The `InfiniticProperties` argument is used to access the configuration properties for the Infinitic library,
 * including the location of the client configuration file.
 *
 * The `ResourceLoader` argument is used to load the client configuration file from the classpath or file system.
 *
 * The `configureClient` method is annotated with `@Bean`, `@ConditionalOnProperty`, and `@ConditionalOnMissingBean`.
 * It creates and configures an InfiniticClient bean if the "infinitic.client.enabled" property is present and has a
 * value of "true". It uses the `getClientConfig` method to obtain the client configuration.
 *
 * The `getClientConfig` method retrieves the client configuration file location from the `InfiniticProperties` object,
 * loads the file using the `ResourceLoader`, and creates a `ClientConfig` object from the loaded file.
 *
 * @param properties the InfiniticProperties object containing the configuration properties for the Infinitic library
 * @param resourceLoader the ResourceLoader object used to load the client configuration file
 * @author tiare.balbi
 */
@Configuration
@ConditionalOnProperty(
    name = ["infinitic.enabled"],
    havingValue = "true"
)
@EnableConfigurationProperties(InfiniticProperties::class)
class InfiniticAutoConfiguration(
    private val properties: InfiniticProperties,
    private val resourceLoader: ResourceLoader
) {
    private val logger = LoggerFactory.getLogger(this::class.java)

    @Bean
    @ConditionalOnProperty(name = ["infinitic.worker.enabled"], havingValue = "true")
    @ConditionalOnMissingBean
    fun configureWorker() = InitializingBean {
        val config = getWorkerConfig()
        InfiniticWorker.fromConfig(config).use {
            logger.info("Starting infinitic worker")
            if (properties.worker.executionMode.isAsync()) {
                it.startAsync()
            } else {
                it.start()
            }
        }
    }

    @Bean
    @ConditionalOnProperty(name = ["infinitic.client.enabled"], havingValue = "true")
    @ConditionalOnMissingBean
    fun configureClient(): InfiniticClient {
        logger.info("Configuring infinitic client")
        return InfiniticClient.fromConfig(getClientConfig(), getWorkerConfig())
    }

    private fun getClientConfig(): ClientConfig {
        val configuration =
            properties.client.configuration
                ?: throw IllegalStateException("Unable to find client configuration file for infinitic.")
        val configurationFile = resourceLoader.getResource(configuration)
        return ClientConfig.fromFile(configurationFile.file.absolutePath)
    }

    private fun getWorkerConfig(): WorkerConfig {
        val configuration = properties.worker.configuration
            ?: throw IllegalStateException("Unable to find worker infinitic configuration")
        val configurationFile = resourceLoader.getResource(configuration)
        return WorkerConfig.fromFile(configurationFile.file.absolutePath)
    }
}
