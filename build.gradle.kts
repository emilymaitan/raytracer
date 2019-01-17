plugins {
    java            // enables java compilation support
    idea            // intellij-specific file generation
    application     // automatically launch main class
}

application {
    mainClassName = "raytracer.Raytracer"    // fully qualified main class name
}

group = "emilymaitan.GFX.raytracer"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testCompile("junit", "junit", "4.12")
    implementation(fileTree("./lib") { include("*.jar") }) // fallback for future jars
}

configure<JavaPluginConvention> {
    sourceCompatibility = JavaVersion.VERSION_1_8
}

tasks.named<Jar>("jar") {
    archiveName = "raytracer.jar"

    manifest {
        attributes(
                "Implementation-Title" to "GFX-Raytracer",
                "Implementation-Version" to version,
                "Main-Class" to "raytracer.Raytracer" // todo fix
        )
    }
}