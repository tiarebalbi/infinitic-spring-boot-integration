package com.tiarebalbi.example.demo

import io.infinitic.annotations.Name
import io.infinitic.workflows.Workflow

@Name("annotatedWorkflow")
interface AnnotatedWorkflow {
    @Name("bar")
    fun concatABC(input: String): String
}

@Suppress("unused")
class AnnotatedWorkflowImpl : Workflow(), AnnotatedWorkflow {
    private val service = newService(AnnotatedService::class.java)

    override fun concatABC(input: String): String {
        var str = input

        str = service.foo(str, "a")
        str = service.foo(str, "b")
        str = service.foo(str, "c")

        return str // should be "${input}abc"
    }
}
