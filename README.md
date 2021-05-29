# Overview
This was an experimental project for me to play with Javalin and Kotlin.

Toy microservice that models SKU / product information.

## References
* Kotlin https://kotlinlang.org/docs/home.html
* Javalin https://javalin.io/
    * A simple web framework for Java and Kotlin, inspired by Sinatra, uses Jetty

* Junit 5 https://junit.org/junit5/docs/current/user-guide/
    * https://github.com/tipsy/javalin-testing-example
* I referenced this blog post pretty extensively https://phauer.com/2018/best-practices-unit-testing-kotlin/

* Mockito-kotlin - switch to this from MockK, I really struggled with the documentation on MockK and couldn't seem
to effectively debug the issues I was having trying to mock Javalin stuff.
    * https://github.com/mockito/mockito-kotlin
    
* Valiktor - library for validating inputs to my rest endpoints, I tried to use the built in stuff from the Javalin
docs but I couldn't test it (on POST bodies) and I didn't see a clear path to more customization of the
error messages.
    * https://github.com/valiktor/valiktor
    * https://blog.jdriven.com/2019/10/customising-form-validation-in-javalin-using-valiktor/

### Monitoring
* Micrometer https://javalin.io/plugins/micrometer
    * https://micrometer.io/docs

### Compile and Package References
* This guide was super important and the key to me getting a Jar constructed that I could use: https://kotlinlang.org/docs/maven.html#self-contained-jar-file
    * Prior to find this, I went down a lot of dead ends
    * Important for creating the self-contained Jar https://maven.apache.org/plugins/maven-assembly-plugin/usage.html
* Nice reference on the difference between some of the maven plugin options https://medium.com/@randilfernando/when-to-use-maven-jar-maven-assembly-or-maven-shade-ffc3f76ba7a6
* Maven + Javalin https://javalin.io/tutorials/maven-setup
    * https://javalin.io/tutorials/docker

* Inspecting the Jar
```text
jar tf target/product-service-1.0-SNAPSHOT.jar
unzip -l target/product-service-1.0-SNAPSHOT.jar
```

# Run Locally

## Build
```text
mvn clean package
```

Outcome:
```text

```

## Run Javalin
```text
java -jar target/product-service-1.0-SNAPSHOT-jar-with-dependencies.jar
```

Outcome:
```text

```

