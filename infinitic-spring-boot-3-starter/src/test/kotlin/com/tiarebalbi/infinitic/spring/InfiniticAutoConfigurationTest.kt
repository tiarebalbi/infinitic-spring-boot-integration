package com.tiarebalbi.infinitic.spring

import com.tiarebalbi.infinitic.tests.AnnotatedWorkflow
import io.infinitic.clients.InfiniticClient
import io.infinitic.workers.InfiniticWorker
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest(classes = [InfiniticAutoConfiguration::class])
class InfiniticAutoConfigurationTest {
    @Autowired
    private lateinit var infiniticClient: InfiniticClient

    @Autowired
    private lateinit var infiniticWorker: InfiniticWorker

    @Test
    fun `should be able to trigger flow`() {
        val event: AnnotatedWorkflow =
            this.infiniticClient.newWorkflow(AnnotatedWorkflow::class.java, setOf("unit-test"))
        val result = this.infiniticClient.dispatch(event::concatABC, "Demo-")

        assertThat(result.await()).isEqualTo("Demo-abc")
    }
}
