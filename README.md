# Infinitic Spring Boot 3 Starter

Welcome to the Infinitic Spring Boot 3 Starter project! This repository provides an auto-configuration integration with
Spring Boot for the Infinitic library, which is designed to orchestrate services distributed on multiple servers, built
on top of Apache Pulsar. With Infinitic, you can easily manage complex scenarios, ensuring that failures don't disrupt
your workflows.

## Getting Started

**Installation:** Add the Infinitic dependency to your pom.xml or build.gradle file.

```xml
<!-- Example for Maven -->
<dependency>
    <groupId>com.tiarebalbi.infinitic</groupId>
    <artifactId>infinitic-spring-boot-3-starter</artifactId>
    <version>[version]</version>
</dependency>
```

**Configuration:** Configure the Infinitic integration in your Spring Boot application's configuration files.

```properties

# Enable auto-configure
infinitic.enabled=true
## Worker Setting
infinitic.worker.enabled=true
infinitic.worker.configuration=classpath:infinitic-worker.yml
## Client Setting
infinitic.client.enabled=true
infinitic.client.configuration=classpath:infinitic-client.yml
```

**Usage:** Start using the Infinitic library in your application code.

```kotlin
@Service
class InfiniticDemoService {

    @Autowired
    private lateinit var infiniticClient: InfiniticClient

    fun runProcess() {
        val event = this.infiniticClient.newWorkflow(AnnotatedWorkflow::class.java)
        val result = event.concatABC("Demo-")
        println(result)
    }
}
```

## Contributing

We welcome contributions to the Infinitic Spring Boot 3 Starter project. If you find a bug, have an enhancement idea, or
want to contribute in any other way, feel free to open an issue or submit a pull request.

