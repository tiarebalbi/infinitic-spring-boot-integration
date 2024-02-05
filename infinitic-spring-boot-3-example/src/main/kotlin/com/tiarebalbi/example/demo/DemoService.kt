package com.tiarebalbi.example.demo

import io.infinitic.clients.InfiniticClient
import org.springframework.stereotype.Service

@Service
class DemoService(private val infiniticClient: InfiniticClient) {
    fun runDemoFlow(): String {
        val event = this.infiniticClient.newWorkflow(AnnotatedWorkflow::class.java)
        val result = event.concatABC("Demo-")
        println("Result: $result")

        return result
    }
}