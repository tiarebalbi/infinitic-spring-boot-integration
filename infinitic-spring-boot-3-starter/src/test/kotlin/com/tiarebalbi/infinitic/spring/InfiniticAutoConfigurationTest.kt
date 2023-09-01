package com.tiarebalbi.infinitic.spring

import com.tiarebalbi.infinitic.tests.AnnotatedWorkflow
import io.infinitic.clients.InfiniticClient
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest(classes = [InfiniticAutoConfiguration::class], properties = ["infinitic.enabled=true"])
class InfiniticAutoConfigurationTest {
    @Autowired
    private lateinit var infiniticClient: InfiniticClient

    @Test
    fun `should be able to trigger flow`() {
        val event = this.infiniticClient.newWorkflow(AnnotatedWorkflow::class.java)
        val result = event.concatABC("Demo-")

        assertThat(result).isEqualTo("Demo-abc")
    }
}
