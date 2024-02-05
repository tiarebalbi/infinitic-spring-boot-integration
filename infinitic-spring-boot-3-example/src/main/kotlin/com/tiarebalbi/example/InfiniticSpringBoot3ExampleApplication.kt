package com.tiarebalbi.example

import com.tiarebalbi.example.demo.DemoService
import io.infinitic.clients.InfiniticClient
import org.springframework.beans.factory.InitializingBean
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@SpringBootApplication
class InfiniticSpringBoot3ExampleApplication

fun main(args: Array<String>) {
    runApplication<InfiniticSpringBoot3ExampleApplication>(*args)
}

@Configuration
class Config {

    @Bean
    fun initService(demoService: DemoService, client: InfiniticClient) = InitializingBean {
        val execution = client.dispatch(demoService::runDemoFlow)
        println("Execution id: ${execution.id}")
        println("Result: ${execution.await()}")
    }
}