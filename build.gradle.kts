plugins {
    id("java")
    id("checkstyle")
    id("com.github.spotbugs") version "6.0.26"
}

spotbugs {
    toolVersion.set("4.9.7")
}

checkstyle {
    toolVersion = "10.12.5"
    configFile = file("config/checkstyle/google_checks.xml")
}

group = "nu.csse.sqe"
version = "1.0"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(11)
    }
}

tasks.compileJava {
    options.release = 11
}

tasks.test {
    useJUnitPlatform()
}