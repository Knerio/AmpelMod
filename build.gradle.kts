import xyz.jpenilla.resourcefactory.bukkit.BukkitPluginYaml

plugins {
  `java-library`
  id("io.papermc.paperweight.userdev") version "1.5.13"
  id("xyz.jpenilla.run-paper") version "2.2.3"
  id("xyz.jpenilla.resource-factory-bukkit-convention") version "1.1.1"
}

group = "de.derioo.chals.timer"
version = "0.0.0"
description = "A Timer Mod"

java {
  toolchain.languageVersion = JavaLanguageVersion.of(17)
}

dependencies {
  paperweight.paperDevBundle("1.20.4-R0.1-SNAPSHOT")
  compileOnly("org.projectlombok:lombok:1.18.30")
  annotationProcessor("org.projectlombok:lombok:1.18.30")
}

tasks {
  assemble {
    dependsOn(reobfJar)
  }

  compileJava {
    options.encoding = Charsets.UTF_8.name()

    options.release = 17
  }
  javadoc {
    options.encoding = Charsets.UTF_8.name()
  }


  reobfJar {
    outputJar = layout.buildDirectory.file("libs/Timer.jar")
  }

}

bukkitPluginYaml {
  main = "de.derioo.chals.timer.Timer"
  load = BukkitPluginYaml.PluginLoadOrder.STARTUP
  authors.add("Dario")
  apiVersion = "1.20"
}
