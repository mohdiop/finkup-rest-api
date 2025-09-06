import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("org.springframework.boot") version "3.5.5"
	id("io.spring.dependency-management") version "1.1.7"
	kotlin("jvm") version "2.2.0"
	kotlin("plugin.spring") version "2.2.0"
	kotlin("plugin.jpa") version "2.2.0"
}

group = "com.mohdiop"
version = "1.0.0"

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

	// MySQL Connector
	runtimeOnly("com.mysql:mysql-connector-j:9.4.0")

	// Spring Validation
	implementation("org.springframework.boot:spring-boot-starter-validation")

	// Bcrypt
	implementation("org.mindrot:jbcrypt:0.4")
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
