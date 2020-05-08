import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

val archives_base_name: String by project
val minecraft_version: String by project
val yarn_mappings: String by project
val loader_version: String by project
val fabric_version: String by project
val fabric_kotlin_version: String by project
val mod_version: String by project
val maven_group: String by project
val rei_version: String by project
val lba_version: String by project
val hwyla_version: String by project
val teamreborn_energy_version: String by project

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

version = "$mod_version+$minecraft_version"
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
    mavenLocal()
    maven {
        url = uri("https://raw.githubusercontent.com/SludgeMod/maven-repo/master/")
    }
    maven { url = uri("https://maven.fabricmc.net/") }
    maven {
        name = "BuildCraft"
        url = uri("https://mod-buildcraft.com/maven/")
    }
    maven { url = uri("https://tehnut.info/maven") }
}

/**
 * This is only for extra local mods
 */
fun optionalRuntimeDependency(group: String, name: String, version: String) {
    dependencies.modRuntime(group, name, version) {
        exclude(group = "net.fabricmc")
        exclude(module = "fabric-api")
        exclude(module = "nbt-crafting")
    }
}

/**
 * For plugins, these are not required
 */
fun pluginDependency(group: String, name: String, version: String) {
    optionalRuntimeDependency(group, name, version)
    dependencies.modCompileOnly(group, name, version) {
        exclude(group = "net.fabricmc")
        exclude(module = "fabric-api")
        exclude(module = "nbt-crafting")
    }
}

/**
 * Required dependencies that will also be part of the jar file
 */
fun includedDependency(group: String, name: String, version: String) {
    dependencies.modImplementation(group, name, version)
    dependencies.include(group, name, version)
}

dependencies {
    minecraft(group = "com.mojang", name = "minecraft", version = minecraft_version)
    mappings(group = "net.fabricmc", name = "yarn", version = yarn_mappings, classifier = "v2")

    modImplementation(group = "net.fabricmc", name = "fabric-loader", version = loader_version)
    modImplementation(group = "net.fabricmc.fabric-api", name = "fabric-api", version = fabric_version)
    modImplementation(group = "net.fabricmc", name = "fabric-language-kotlin", version = fabric_kotlin_version)

    includedDependency(group = "teamreborn", name = "energy", version = teamreborn_energy_version)
    includedDependency(group = "alexiil.mc.lib", name = "libblockattributes-fluids", version = lba_version)

    pluginDependency(group = "me.shedaniel", name = "RoughlyEnoughItems", version = rei_version)
//  optionalRuntimeDependency(group = "mcp.mobius.waila", name = "Hwyla", version = hwyla_version)
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
        expand("version" to version)
    }
}

tasks.withType<KotlinCompile>().configureEach {
    kotlinOptions.jvmTarget = "1.8"
}

// Only for mixins
// see http://yodaconditions.net/blog/fix-for-java-file-encoding-problems-with-gradle.html
tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
}