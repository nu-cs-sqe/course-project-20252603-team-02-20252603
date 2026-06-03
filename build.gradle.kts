import com.github.spotbugs.snom.Confidence
import com.github.spotbugs.snom.Effort

plugins {
    id("java")
    id("checkstyle")
    id("com.github.spotbugs") version "6.0.26"
    jacoco
    id("info.solidsoft.pitest") version "1.15.0"
}

spotbugs {
    toolVersion.set("4.9.7")
}

checkstyle {
    toolVersion = "10.12.5"
    configFile = file("config/checkstyle/google_checks.xml")
}

tasks.jacocoTestReport {
    reports {
        xml.required = false
        csv.required = false
        html.outputLocation = layout.buildDirectory.dir("reports/jacoco")
    }
    dependsOn(tasks.test)
}

tasks.build {
    dependsOn("pitest")
}

pitest {
    targetClasses = setOf("model.*")
    targetTests = setOf("model.*")
    junit5PluginVersion = "1.2.1"
    pitestVersion = "1.15.0"

    threads = 4
    outputFormats = setOf("HTML")
    timestampedReports = false
    testSourceSets.set(listOf(sourceSets.test.get()))
    mainSourceSets.set(listOf(sourceSets.main.get()))
    jvmArgs.set(listOf("-Xmx1024m"))
    useClasspathFile.set(true)
    fileExtensionsToFilter.addAll("xml")
    exportLineCoverage = true
}

group = "nu.csse.sqe"
version = "1.0"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testImplementation("org.easymock:easymock:3.1")
    compileOnly("com.github.spotbugs:spotbugs-annotations:4.8.3")
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
    finalizedBy(tasks.jacocoTestReport)
    finalizedBy(tasks.pitest)
}