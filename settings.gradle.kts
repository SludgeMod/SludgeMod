

pluginManagement {
    repositories {
        maven {
            name = "Fabric"
            url = uri("https://maven.fabricmc.net/")
        }
        gradlePluginPortal()
    }

    plugins {
        val kotlin_version: String by settings
        val loom_version: String by settings

        kotlin("jvm").version(kotlin_version)
        id("fabric-loom").version(loom_version)
    }
}
