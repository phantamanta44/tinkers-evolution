plugins {
    id("st.evening.mc.prelude.build") version "1.0.0"
    id("maven-publish")
}

group = "xyz.phanta.tconevo"
version = "1.1.7"

preludeBuild {
    modId = "tconevo"
    mod {
        constantsClass = "TconEvoConsts"
        accessTransformerFile = "tconevo_at.cfg"
        loadingPluginClass = "coremod.TconEvoCoreMod"
    }
    minecraft {
        mcVersion = "1.12.2"
        deobf {
            mappingsChannel = "stable"
            mappingsVersion = "39"
        }
    }
}

/*
 * Configure dependencies
 */

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
        name = "Thiakil Maven"
        url = uri("https://maven.thiakil.com/")
    }
    maven {
        name = "BlameJared Maven"
        url = uri("https://maven.blamejared.com/")
    }
    maven {
        name = "ShadowFacts Maven"
        url = uri("https://maven.shadowfacts.net/")
    }
    maven {
        name = "ModMaven"
        url = uri("https://modmaven.dev/")
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
    compileOnly("CraftTweaker2:ZenScript:4.1.9.491")
    compileOnly("CraftTweaker2:CraftTweaker2-API:4.1.14.519")
    compileOnly(deobf("com.azanor.baubles:Baubles:1.12-1.5.2"))
    compileOnly(deobf("curse.maven:codechicken-lib-1-8-242818:2779848")) // 3.2.3.358
    compileOnly(deobf("cofh:RedstoneFlux:1.12-2.1.0.7:universal"))
    compileOnly(deobf("curse.maven:brandons-core-231382:3051539")) // 2.4.19.214
    compileOnly(deobf("curse.maven:draconic-evolution-223565:3051542")) // 2.3.27.353
    compileOnly(deobf("vazkii.botania:Botania:r1.10-363.148"))
    compileOnly(deobf("curse.maven:librarianlib-continuous-1058274:6617935")) // 1.12.2-4.22-2.0-3
    compileOnly(deobf("curse.maven:natural-pledge-247704:2740703")) // r3.1.2
    compileOnly(deobf("cofh:CoFHCore:1.12.2-4.6.3.27:universal"))
    compileOnly(deobf("cofh:ThermalFoundation:1.12.2-2.6.3.27:universal"))
    compileOnly(deobf("cofh:ThermalExpansion:1.12.2-5.5.4.43:universal"))
    compileOnly(deobf("curse.maven:industrial-foregoing-266515:2745321")) // 1.12.13-237
    compileOnly(deobf("curse.maven:applied-energistics-2-223794:2747063")) // rv6-stable-7
    compileOnly(deobf("curse.maven:mekanism-ce-399904:7054604")) // 9.12.12
    compileOnly(deobf("curse.maven:mekanism-generators-813408:7054621")) // 9.12.12
    compileOnly(deobf("curse.maven:actually-additions-228404:2844115")) // r151-2
    compileOnly(deobf("curse.maven:thaumcraft-223628:2629023")) // 6.1.BETA26
    compileOnly(deobf("curse.maven:astral-sorcery-241721:2971187")) // 1.10.24
    compileOnly(deobf("curse.maven:blood-magic-224791:2822288")) // 2.4.3-105
    compileOnly(deobf("com.progwml6.natura:natura:1.12.2-4.3.2.69"))
    compileOnly(deobf("curse.maven:projecte-226410:2702991")) // 1.4.1
    compileOnly(deobf("curse.maven:valkyriecompat-289532:2691540")) // 2.0.20.1
    compileOnly(deobf("curse.maven:valkyrielib-245480:2691542")) // 2.0.20.1
    compileOnly(deobf("curse.maven:environmental-tech-245453:2691536")) // 2.0.20.1
    compileOnly(deobf("net.industrial-craft:industrialcraft-2:2.8.222-ex112"))
    compileOnly(deobf("curse.maven:advanced-solar-panels-252714:2652182")) // 4.2.1
    runtimeOnly(deobf("curse.maven:advanced-solar-panels-patcher-400399:3401706")) // 1.2.1
    compileOnly(deobf("curse.maven:natural-absorption-224296:2678478")) // 1.0.0
    compileOnly(deobf("curse.maven:redstone-repository-revolved-300750:3483422")) // 2.0.0
    compileOnly(deobf("curse.maven:solar-flux-reborn-246974:3050838")) // 12.4.11
    compileOnly(deobf("com.enderio.core:EnderCore:1.12.2-0.5.78"))
    compileOnly(deobf("com.enderio:EnderIO:1.12.2-5.3.72")) {
        exclude("com.enderio", module = "ap")
        exclude("com.enderio.core", module = "EnderCore")
    }
    compileOnly(deobf("curse.maven:ender-io-endergy-304346:4674241")) // 1.12.2-5.3.72
    compileOnly(deobf("net.sengir.forestry:forestry_1.12.2:5.8.0.311"))
    compileOnly(deobf("morph.avaritia:Avaritia:1.12.2-3.3.0.33:universal")) { exclude(group = "codechicken") }
    compileOnly("net.darkhax.bookshelf:Bookshelf-1.12.2:2.3.590")
    compileOnly("net.darkhax.gamestages:GameStages-1.12.2:2.0.123")
    compileOnly(deobf("curse.maven:tinkers-tool-leveling-250957:2630860")) // 1.1.0
    compileOnly(deobf("curse.maven:elenai-dodge-2-442962:3343308")) // 1.1.0
    compileOnly(deobf("curse.maven:reborncore-237903:3330308")) // 1.12.2-3.19.5
    compileOnly(deobf("curse.maven:techreborn-233564:2966851")) // 1.12.2-2.27.3.1084
    compileOnly(deobf("curse.maven:hbms-nuclear-tech-mod-extended-edition-708939:5254559")) // 1.12.2-2.0.2
}

configurations {
    runtimeClasspath { extendsFrom(compileOnly.get()) }
}

/*
 * Checks
 */

abstract class CheckCrossContaminationTask : DefaultTask() {
    companion object {
        val PKG_PATTERN: Regex = Regex("""\s*(import|package)\s+([a-zA-Z_$][\w$]*(?:\.[a-zA-Z_$][\w$]*)*)\s*;\s*""")

        fun getIntegrationModule(qn: String): String? {
            if (!qn.startsWith("xyz.phanta.tconevo.integration.")) return null
            if (qn[32].isUpperCase()) return null
            if (qn.endsWith("Hooks")) return null
            val k = qn.indexOf('.', 31)
            return qn.substring(31, if (k == -1) qn.length else k)
        }
    }

    data class SourceUnit(
        val file: File,
        val intModule: String?,
        val intLocal: Boolean,
        val imports: List<String>
    )

    @get:InputFiles
    abstract val sources: ConfigurableFileCollection

    @TaskAction
    fun check() {
        // very rudimentary, but actually parsing the java seems excessive
        val units = mutableMapOf<String, SourceUnit>()
        sources.forEach { srcFile ->
            srcFile.bufferedReader().use { src ->
                var pkgTemp: String? = null
                var intLocalAnnot = false
                val imports = mutableListOf<String>()
                for (line in src.lineSequence()) {
                    val match = PKG_PATTERN.matchEntire(line)
                    if (match != null) {
                        when (match.groupValues[1]) {
                            "package" -> {
                                if (pkgTemp != null) {
                                    throw IllegalStateException("Duplicate package declarations in $srcFile")
                                } else {
                                    pkgTemp = match.groupValues[2]
                                }
                            }
                            "import" -> imports += match.groupValues[2]
                        }
                    } else if (line == "@IntegrationLocal") {
                        intLocalAnnot = true
                    }
                }
                val pkg = pkgTemp ?: throw IllegalStateException("No package declaration in $srcFile")
                val intModule = getIntegrationModule(pkg)
                units["$pkg.${srcFile.nameWithoutExtension}"] = SourceUnit(
                    srcFile,
                    intModule,
                    intModule == null || intLocalAnnot,
                    imports
                )
            }
        }

        units.values.forEach { unit ->
            unit.imports.forEach { import ->
                val mod = getIntegrationModule(import)
                if (mod != null && mod != unit.intModule) {
                    val importUnit = units[import]
                        ?: throw IllegalStateException("Unknown import $import in ${unit.file}")
                    if (!importUnit.intLocal) {
                        throw IllegalStateException("Illegal cross-reference to $import in ${unit.file}")
                    }
                }
            }
        }
    }
}

val checkCrossContamination: TaskProvider<*> = tasks.register<CheckCrossContaminationTask>("checkCrossContamination") {
    sources.from(sourceSets[SourceSet.MAIN_SOURCE_SET_NAME].allJava)
}

tasks.check {
    dependsOn(checkCrossContamination)
}
