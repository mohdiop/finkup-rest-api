import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("org.springframework.boot") version "3.5.5"
	id("io.spring.dependency-management") version "1.1.7"
	kotlin("jvm") version "2.2.0"
	kotlin("plugin.spring") version "2.2.0"
	kotlin("plugin.jpa") version "2.2.0"
}

group = "com.mohdiop"
version = providers.environmentVariable("API_VERSION").toString()

java {
	sourceCompatibility = JavaVersion.VERSION_21
}

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	testImplementation("org.springframework.boot:spring-boot-starter-test")

	// Spring Security
	implementation("org.springframework.boot:spring-boot-starter-security")

	// JWT
	implementation("io.jsonwebtoken:jjwt-api:0.13.0")
	implementation("io.jsonwebtoken:jjwt-impl:0.13.0")
	implementation("io.jsonwebtoken:jjwt-jackson:0.13.0")

	// MySQL Connector
	runtimeOnly("com.mysql:mysql-connector-j:9.4.0")

	// Spring Validation
	implementation("org.springframework.boot:spring-boot-starter-validation")

	// Swagger
	implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.8.13")

	// WebFlux
	implementation("org.springframework:spring-webflux")

}

kotlin {
	jvmToolchain(21)
}

tasks.withType<KotlinCompile> {
	compilerOptions {
		jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_21)
		freeCompilerArgs.add("-Xjsr305=strict")
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}
