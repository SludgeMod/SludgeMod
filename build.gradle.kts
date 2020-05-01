import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

val archives_base_name: String by project
val minecraft_version: String by project
val yarn_mappings: String by project
val loader_version: String by project
val fabric_version: String by project
val fabric_kotlin_version: String by project
val mod_version: String by project
val maven_group: String by project

plugins {
    kotlin("jvm")
    id("fabric-loom")
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

base {
    archivesBaseName = archives_base_name
}

version = mod_version
group = maven_group

minecraft {

}

tasks.jar {
    val licenseSpec = copySpec {
        from("${project.rootDir}/LICENSE")
    }

    metaInf.with(licenseSpec)
}

repositories {
    maven { url = uri("https://maven.fabricmc.net/") }
}

dependencies {
    minecraft(group = "com.mojang", name = "minecraft", version = minecraft_version)

    mappings(group = "net.fabricmc", name = "yarn", version = yarn_mappings, classifier = "v2")

    modImplementation(group = "net.fabricmc", name = "fabric-loader", version = loader_version)
    modImplementation(group = "net.fabricmc.fabric-api", name = "fabric-api", version = fabric_version)
    modImplementation(group = "net.fabricmc", name = "fabric-language-kotlin", version = fabric_kotlin_version)
}

// Loom will automatically attach sourcesJar to a RemapSourcesJar task and to the "build" task
// if it is present.
// If you remove this task, sources will not be generated.
val sourcesJar = tasks.create<Jar>("sourcesJar") {
    archiveClassifier.set("sources")
    from(sourceSets["main"].allSource)
}

tasks.getByName<ProcessResources>("processResources") {
    filesMatching("fabric.mod.json") {
        expand(
            mutableMapOf(
                "version" to version
            )
        )
    }
    inputs.property("version", version)
}

tasks.withType<KotlinCompile>().configureEach {
    kotlinOptions.jvmTarget = "1.8"
}

// Only for mixins
// see http://yodaconditions.net/blog/fix-for-java-file-encoding-problems-with-gradle.html
tasks.withType<JavaCompile>().configureEach {
    options.encoding = "UTF-8"
}