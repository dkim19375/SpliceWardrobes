@file:Suppress("SpellCheckingInspection", "PropertyName")

import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.6.21"
    id("org.cadixdev.licenser") version "0.6.1"
    id("io.papermc.paperweight.userdev") version "1.3.6"
    id("com.github.johnrengelman.shadow") version "7.1.2"
}

val KT_VER = "1.6.21"

group = "me.dkim19375"
version = "1.0.1"

val basePackage = "me.dkim19375.${project.name.toLowerCase()}.libs"
val fileName = tasks.shadowJar.get().archiveFileName.get()

tasks.withType<JavaCompile> {
    sourceCompatibility = "17"
    targetCompatibility = "17"
    options.encoding = "UTF-8"
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        jvmTarget = "17"
    }
}

repositories {
    mavenCentral()
    mavenLocal()
    maven("https://libraries.minecraft.net/")
    maven("https://repo.triumphteam.dev/snapshots/")
    maven("https://repo.dmulloy2.net/repository/public/")
    maven("https://papermc.io/repo/repository/maven-public/")
    maven("https://oss.sonatype.org/content/repositories/central")
    maven("https://oss.sonatype.org/content/repositories/snapshots")
    maven("https://s01.oss.sonatype.org/content/repositories/releases/")
    maven("https://s01.oss.sonatype.org/content/repositories/snapshots/")
    maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
    maven("https://repo.extendedclip.com/content/repositories/placeholderapi/")
}

dependencies {
    paperDevBundle("1.18.2-R0.1-SNAPSHOT")

    compileOnly("org.jetbrains:annotations:23.0.0")
    compileOnly("io.papermc.paper:paper-api:1.18.2-R0.1-SNAPSHOT")

    implementation("me.mattstudios:triumph-config:1.0.5-SNAPSHOT")
    implementation("io.github.dkim19375:item-move-detection-lib:1.1.8")

    implementation("io.github.dkim19375:dkim-bukkit-core:3.3.38") {
        exclude(group = "org.jetbrains.kotlin")
        exclude(group = "org.jetbrains.kotlinx")
    }
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:$KT_VER") {
        exclude(module = "annotations")
    }
    implementation("dev.triumphteam:triumph-gui:3.1.2") {
        excludeAdventure()
    }
    implementation("net.kyori:adventure-platform-bukkit:4.1.0") {
        excludeAdventure()
    }
}

license {
    header.set(resources.text.fromFile(rootProject.file("HEADER")))
    include("**/*.kt")
}

val shadowJar = tasks.shadowJar.get()

val removeBuildJars by tasks.registering {
    doLast {
        File(project.rootDir, "build/libs").deleteRecursively()
    }
}

val server = "1.18"
val servers = setOf(
    "1.8",
    "1.16",
    "1.17",
    "1.18"
)

val copyFile by tasks.registering {
    doLast {
        val jar = shadowJar.archiveFile.get().asFile
        val pluginFolder = file(rootDir).resolve("../.TestServers/${server}/plugins")
        if (pluginFolder.exists()) {
            jar.copyTo(File(pluginFolder, shadowJar.archiveFileName.get()), true)
        }
    }
}

val deleteAll by tasks.registering {
    doLast {
        for (deleteServer in servers) {
            for (file in File("../.TestServers/${deleteServer}/plugins").listFiles() ?: emptyArray()) {
                if (file.name.startsWith(shadowJar.archiveBaseName.get())) {
                    file.delete()
                }
            }
        }
    }
}

tasks.processResources {
    outputs.upToDateWhen { false }
    expand("pluginVersion" to project.version)
}

val relocations = setOf(
    "kotlin",
    "kotlinx",
    "reactor",
    "com.google.gson",
    "org.yaml.snakeyaml",
    "dev.triumphteam.gui",
    "org.reactivestreams",
    "me.mattstudios.config",
    "me.dkim19375.dkimcore",
    "org.jetbrains.annotations",
    "me.dkim19375.dkimbukkitcore",
    "org.intellij.lang.annotations",
)

tasks.shadowJar {
    relocations.forEach { name ->
        relocate(name, "${basePackage}.$name")
    }
    exclude("DebugProbesKt.bin", "**/**.kotlin_builtins")
    mergeServiceFiles()
    finalizedBy(tasks.getByName("copyFile"))
}

tasks.assemble {
    dependsOn(tasks.reobfJar)
}


fun ExternalModuleDependency.excludeAdventure() {
    exclude(module = "adventure-api")
    exclude(module = "adventure-nbt")
    exclude(module = "adventure-bom")
    exclude(module = "adventure-examination-api")
    exclude(module = "adventure-text-serializer-gson")
    exclude(module = "adventure-text-serializer-legacy")
}