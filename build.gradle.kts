plugins {
    java
    id("io.quarkus")
    id("io.freefair.lombok") version "8.4"
}

repositories {
    mavenCentral()
    maven("https://repository.cybine.de/repository/maven-public/")
}

val quarkusPlatformGroupId: String by project
val quarkusPlatformArtifactId: String by project
val quarkusPlatformVersion: String by project
val quarkusUtilsVersion: String by project

dependencies {
    implementation(enforcedPlatform("${quarkusPlatformGroupId}:${quarkusPlatformArtifactId}:${quarkusPlatformVersion}"))

    implementation("io.quarkus:quarkus-agroal")
    implementation("io.quarkus:quarkus-arc")
    implementation("io.quarkus:quarkus-hibernate-orm")
    implementation("io.quarkus:quarkus-hibernate-validator")
    implementation("io.quarkus:quarkus-jdbc-postgresql")
    implementation("io.quarkus:quarkus-liquibase")
    implementation("io.quarkus:quarkus-mutiny")
//    implementation("io.quarkus:quarkus-oidc")
    implementation("io.quarkus:quarkus-quartz")
    implementation("io.quarkus:quarkus-rest")
    implementation("io.quarkus:quarkus-rest-client")
    implementation("io.quarkus:quarkus-rest-client-jackson")
    implementation("io.quarkus:quarkus-rest-jackson")
    implementation("io.quarkus:quarkus-smallrye-openapi")

    implementation("net.sf.biweekly:biweekly:0.6.6")

    implementation("de.cybine.quarkus:action-processor:${quarkusUtilsVersion}")
    implementation("de.cybine.quarkus:api-common:${quarkusUtilsVersion}")
    implementation("de.cybine.quarkus:api-query:${quarkusUtilsVersion}")
    implementation("de.cybine.quarkus:common:${quarkusUtilsVersion}")
    implementation("de.cybine.quarkus:datasource-query:${quarkusUtilsVersion}")
    implementation("de.cybine.quarkus:type-converter:${quarkusUtilsVersion}")

    testImplementation("io.quarkus:quarkus-junit5")
    testImplementation("io.rest-assured:rest-assured")
}

group = "de.cybine"
version = "1.0-SNAPSHOT"

java {
    sourceCompatibility = JavaVersion.VERSION_21
    targetCompatibility = JavaVersion.VERSION_21
}

tasks.withType<Test> {
    systemProperty("java.util.logging.manager", "org.jboss.logmanager.LogManager")
}
tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
    options.compilerArgs.add("-parameters")
}
