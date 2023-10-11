plugins {
    java
    id("org.springframework.boot") version "2.7.16"
    id("io.spring.dependency-management") version "1.0.15.RELEASE"
    id("info.solidsoft.pitest") version "1.15.0"
    jacoco
}

group = "co.edu.unisabana"
version = "0.0.1-SNAPSHOT"

java {
    sourceCompatibility = JavaVersion.VERSION_11
}

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("info.solidsoft.gradle.pitest:gradle-pitest-plugin:1.15.0")
    compileOnly("org.projectlombok:lombok")
    implementation("com.h2database:h2")

    runtimeOnly("com.mysql:mysql-connector-j")
    annotationProcessor("org.projectlombok:lombok")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
}

tasks.withType<Test> {
    useJUnitPlatform()
}
jacoco {
    toolVersion = "0.8.8"
}
tasks.test {
    dependsOn(tasks.pitest)
    finalizedBy(tasks.jacocoTestReport) // report is always generated after tests run
}
tasks.jacocoTestReport {
    dependsOn(tasks.test) // tests are required to run before generating the report
    reports {
        xml.required = false
        csv.required = false
    }
    classDirectories.setFrom(classDirectories.files.map {
        fileTree(it).matching {
            exclude("**/dto/**")
            exclude("**/entity/**")
            exclude("**/model/**")
        }
    })
}
tasks {
    pitest {
        junit5PluginVersion.set("1.0.0")
        mutators.set(setOf("STRONGER"))
        targetClasses.set(setOf("co.edu.unisabana.example.service.*"))
        targetTests.set(setOf("co.edu.unisabana.example.service.*"))
        excludedClasses.set(setOf("*.model.*", "*.exception.*", "*.vo.*"))
        threads.set(Runtime.getRuntime().availableProcessors())
        outputFormats.set(setOf("XML", "HTML"))
        //coverageThreshold.set(88)
        //testStrengthThreshold.set(80)
    }
}
