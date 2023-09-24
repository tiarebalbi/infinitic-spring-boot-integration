package com.tiarebalbi.example.demo

import io.infinitic.annotations.Name

@Name("annotatedService")
interface AnnotatedService {
    @Name("bar")
    fun foo(str1: String, str2: String): String
}

@Suppress("unused")
class AnnotatedServiceImpl : AnnotatedService {
    override fun foo(str1: String, str2: String) = str1 + str2
}
