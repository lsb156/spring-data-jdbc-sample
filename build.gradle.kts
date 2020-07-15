import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "2.3.1.RELEASE"
    id("io.spring.dependency-management") version "1.0.9.RELEASE"
    kotlin("jvm") version "1.3.72"
    kotlin("plugin.spring") version "1.3.72"
}

group = "com.ssabae.springdatajdbc"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_11

repositories {
    mavenCentral()
    maven {
        setUrl("https://repo.spring.io/milestone/")
    }
    maven {
        name = "navercorp.release"
        setUrl("http://repo.navercorp.com/maven2/")
    }
    maven {
        name = "navercorp.snapshot"
        setUrl("http://repo.navercorp.com/m2-snapshot-repository/")
    }
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("org.springframework.boot:spring-boot-starter")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.data:spring-data-jdbc:2.0.0.RC2")
    implementation("org.springframework.data:spring-data-relational:2.0.0.RC2")
    implementation("org.springframework.data:spring-data-commons:2.3.0.RC2")
    implementation("org.liquibase:liquibase-core")

    compileOnly("org.projectlombok:lombok")
    annotationProcessor("org.projectlombok:lombok")

    runtimeOnly("com.h2database:h2")

    testImplementation("org.springframework.boot:spring-boot-starter-test") {
        exclude(group = "org.junit.vintage", module = "junit-vintage-engine")
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "11"
    }
}
