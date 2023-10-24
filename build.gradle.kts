import org.jetbrains.gradle.ext.Gradle
import org.jetbrains.gradle.ext.compiler
import org.jetbrains.gradle.ext.runConfigurations
import org.jetbrains.gradle.ext.settings

val resourcesDir: String = "src/main/resources"

plugins {
    id("java-library")
    id("maven-publish")
    id("org.jetbrains.gradle.plugin.idea-ext") version "1.1.7"
    id("eclipse")
    id("com.gtnewhorizons.retrofuturagradle") version "1.3.24"
}

/*
 * Load project properties
 */

class Props {
    private fun propS(propName: String): String = providers.gradleProperty(propName).get()

    private fun optS(propName: String): String? = providers.gradleProperty(propName).orNull

    private fun propSL(propName: String): List<String> = providers.gradleProperty(propName)
        .map { strVal -> strVal.split(',').map { it.trim() } }
        .getOrElse(listOf())

    private fun propB(propName: String): Boolean = providers.gradleProperty(propName).map {
        when (val strVal = it.lowercase()) {
            "true" -> true
            "false" -> false
            else -> throw IllegalArgumentException("Not a boolean value: $strVal")
        }
    }.get()

    val modId: String = propS("mod.id")
    val modPackage: String = propS("mod.package")
    val modVersion: String = propS("mod.version")

    val modATs: String? = optS("mod.access_transformers")
    val modConstClass: String = propS("mod.const_class")
    val modLoadingPlugin: String? = optS("mod.loading_plugin")?.let { "$modPackage.$it" }

    val mcVersion: String = propS("minecraft.version")
    val mcDeobfMappingsChannel: String = propS("minecraft.deobf.mappings.channel")
    val mcDeobfMappingsVersion: String = propS("minecraft.deobf.mappings.version")

    val buildSources: Boolean = propB("build.buildSources")
    val buildDocs: Boolean = propB("build.buildDocs")
}

val props = Props()

inline fun <T> withProps(f: Props.() -> T): T = props.run(f)

/*
 * Configure project
 */

group = props.modPackage
version = props.modVersion

base {
    archivesName = withProps { "$modId-$mcVersion" }
}

/*
 * Configure build system
 */

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(8)
        vendor = JvmVendorSpec.AZUL // RFG requires an Azul JVM for its build tasks
    }

    if (props.buildSources) {
        withSourcesJar()
    }
    if (props.buildDocs) {
        withJavadocJar()
    }
}

tasks.compileJava {
    options.encoding = "UTF-8"
}

minecraft {
    // Build config
    mcVersion = props.mcVersion
    mcpMappingChannel = props.mcDeobfMappingsChannel
    mcpMappingVersion = props.mcDeobfMappingsVersion

    useDependencyAccessTransformers = true

    injectedTags.apply {
        put("MOD_ID", props.modId)
        put("VERSION", props.modVersion)
    }

    // Runtime config
    username = "Player"
    extraRunJvmArguments.add("-ea:${props.modPackage}")
    props.modLoadingPlugin?.let { extraRunJvmArguments.add("-Dfml.coreMods.load=$it") }
}

tasks.injectTags {
    outputClassName = withProps { "$modPackage.$modConstClass" }
}

tasks.processResources {
    inputs.property("version", props.modVersion)

    filesMatching("mcmod.info") {
        expand(
            "modId" to props.modId,
            "modVersion" to props.modVersion,
            "mcVersion" to props.mcVersion
        )
    }
}

tasks.deobfuscateMergedJarToSrg {
    props.modATs?.let { accessTransformerFiles.from("$resourcesDir/META-INF/$it") }
}

tasks.srgifyBinpatchedJar {
    props.modATs?.let { accessTransformerFiles.from("$resourcesDir/META-INF/$it") }
}

tasks.jar {
    manifest {
        props.modATs?.let { attributes("FMLAT" to it) }
        props.modLoadingPlugin?.let {
            attributes("FMLCorePlugin" to it, "FMLCorePluginContainsFMLMod" to "true")
        }
    }
}

/*
 * Configure dependencies
 */

configurations {
    
}

repositories {
    mavenLocal()
    maven {
        name = "CurseMaven"
        url = uri("https://www.cursemaven.com/")
    }
    maven {
        name = "DVS1 Repo"
        url = uri("https://dvs1.progwml6.com/files/maven/")
    }
    maven {
        name = "OpenComputers"
        url = uri("https://maven.cil.li")
    }
    maven {
        name = "CoFH Maven"
        url = uri("https://maven.covers1624.net")
    }
    maven {
        name = "Thiakil Maven"
        url = uri("https://maven.thiakil.com/")
    }
    maven {
        name = "BlameJared Maven"
        url = uri("https://maven.blamejared.com/")
    }
    maven {
        name = "Bluexin Maven"
        url = uri("https://maven.bluexin.be/repository/snapshots/")
    }
    maven {
        name = "tterrag Maven"
        url = uri("https://maven.tterrag.com/")
    }
    maven {
        name = "Darkhax Maven"
        url = uri("https://maven.mcmoddev.com")
    }
}

dependencies { // THE BEAST
    @Suppress("UNCHECKED_CAST")
    fun <T> deobf(depSpec: T): T = rfg.deobf(depSpec) as T
    
    api("io.github.phantamanta44.libnine:libnine-1.12.2:1.2.1")
    api(deobf("mezz.jei:jei_1.12.2:4.15.0.293"))
    api(deobf("slimeknights.mantle:Mantle:1.12-1.3.3.56"))
    api(deobf("slimeknights:TConstruct:1.12.2-2.13.0.184"))
    compileOnly(deobf("curse.maven:constructs-armory-287683:2882794")) // 1.2.5.4
    compileOnly(deobf("com.azanor.baubles:Baubles:1.12-1.5.2"))
    compileOnly(deobf("curse.maven:codechicken-lib-1-8-242818:2779848")) // 3.2.3.358
    compileOnly(deobf("cofh:RedstoneFlux:1.12-2.1.0.7:universal"))
    compileOnly(deobf("curse.maven:brandons-core-231382:3051539")) // 2.4.19.214
    compileOnly(deobf("curse.maven:draconic-evolution-223565:3051542")) // 2.3.27.353
    compileOnly(deobf("vazkii.botania:Botania:r1.10-363.148"))
    compileOnly(deobf("com.teamwizardry.librarianlib:librarianlib-1.12.2:4.19.1"))
    compileOnly(deobf("curse.maven:natural-pledge-247704:2740703")) // r3.1.2
    compileOnly(deobf("cofh:CoFHCore:1.12.2-4.6.3.27:universal"))
    compileOnly(deobf("cofh:ThermalFoundation:1.12.2-2.6.3.27:universal"))
    compileOnly(deobf("cofh:ThermalExpansion:1.12.2-5.5.4.43:universal"))
    compileOnly(deobf("curse.maven:industrial-foregoing-266515:2745321")) // 1.12.13-237
    compileOnly(deobf("curse.maven:applied-energistics-2-223794:2747063")) // rv6-stable-7
    compileOnly(deobf("curse.maven:mekanism-268560:2835175")) // 9.8.3.390
    compileOnly(deobf("curse.maven:mekanism-generators-268566:2835177")) // 9.8.3.390
    compileOnly(deobf("curse.maven:actually-additions-228404:2844115")) // r151-2
    compileOnly(deobf("curse.maven:thaumcraft-223628:2629023")) // 6.1.BETA26
    compileOnly(deobf("curse.maven:astral-sorcery-241721:2971187")) // 1.10.24
    compileOnly(deobf("curse.maven:blood-magic-224791:2822288")) // 2.4.3-105
    compileOnly(deobf("com.progwml6.natura:natura:1.12.2-4.3.2.69"))
    compileOnly(deobf("curse.maven:projecte-226410:2702991")) // 1.4.1
    compileOnly(deobf("curse.maven:valkyriecompat-289532:2691540")) // 2.0.20.1
    compileOnly(deobf("curse.maven:valkyrielib-245480:2691542")) // 2.0.20.1
    compileOnly(deobf("curse.maven:environmental-tech-245453:2691536")) // 2.0.20.1
    compileOnly(deobf("net.industrial-craft:industrialcraft-2:2.8.91-ex112"))
    compileOnly(deobf("curse.maven:advanced-solar-panels-252714:2652182")) // 4.2.1
    compileOnly(deobf("curse.maven:natural-absorption-224296:2678478")) // 1.0.0
    compileOnly(deobf("curse.maven:redstone-repository-revolved-300750:3483422")) // 2.0.0
    compileOnly(deobf("curse.maven:solar-flux-reborn-246974:3050838")) // 12.4.11
    compileOnly(deobf("com.enderio.core:EnderCore:1.12.2-0.5.76"))
    compileOnly(deobf("com.enderio:EnderIO:1.12.2-5.3.68")) {
        exclude(group = "com.enderio", module = "ap")
        exclude(group = "deobf.com.enderio.core")
    }
    compileOnly(deobf("net.sengir.forestry:forestry_1.12.2:5.8.0.311"))
    compileOnly(deobf("morph.avaritia:Avaritia:1.12.2-3.3.0.33:universal")) { exclude(group = "codechicken") }
    compileOnly("net.darkhax.bookshelf:Bookshelf-1.12.2:2.3.590")
    compileOnly("net.darkhax.gamestages:GameStages-1.12.2:2.0.123")
    compileOnly(deobf("curse.maven:tinkers-tool-leveling-250957:2630860")) // 1.1.0
    compileOnly(deobf("curse.maven:elenai-dodge-2-442962:3343308")) // 1.1.0
}

configurations {
    runtimeClasspath { extendsFrom(compileOnly.get()) }
}

/*
 * Configure artifact publication
 */

publishing {
    publications {
        create<MavenPublication>("maven") {
            artifactId = withProps { "$modId-$mcVersion" }
            from(components["java"])
        }
    }
}

/*
 * Configure IDE integration
 */

eclipse {
    classpath {
        isDownloadSources = true
        isDownloadJavadoc = true
    }
}

idea {
    module {
        isDownloadJavadoc = true
        isDownloadSources = true
        inheritOutputDirs = true // Fix resources in IJ-Native runs
    }
    project {
        settings {
            runConfigurations {
                add(Gradle("1. Run Client").apply {
                    setProperty("taskNames", listOf("runClient"))
                })
                add(Gradle("2. Run Server").apply {
                    setProperty("taskNames", listOf("runServer"))
                })
                add(Gradle("3. Run Obfuscated Client").apply {
                    setProperty("taskNames", listOf("runObfClient"))
                })
                add(Gradle("4. Run Obfuscated Server").apply {
                    setProperty("taskNames", listOf("runObfServer"))
                })
            }
            compiler {
                afterEvaluate {
                    javac {
                        javacAdditionalOptions = "-encoding utf8"
                        moduleJavacAdditionalOptions = mapOf(
                            (project.name + ".main") to
                                tasks.compileJava.get().options.compilerArgs.joinToString(" ") { "\"$it\"" }
                        )
                    }
                }
            }
        }
    }
}

tasks.processIdeaSettings {
    dependsOn(tasks.injectTags)
}
