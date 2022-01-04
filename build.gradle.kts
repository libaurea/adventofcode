plugins {
    application
}

repositories {
    mavenLocal()
    mavenCentral()
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
    }
}

dependencies {
    implementation("com.google.guava:guava:30.1.1-jre")
    implementation("commons-io:commons-io:2.11.0")
    testImplementation("org.junit.jupiter:junit-jupiter:5.7.2")
}

application {
    mainClass.set(System.getProperty("mainClass") ?: "de.libaurea.adventofcode.Main")
}

tasks.named<Test>("test") {
    useJUnitPlatform()
}
