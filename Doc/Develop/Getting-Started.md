# Getting Started

To contribute to or extend **Combat Cooldown Adjuster**, ensure your environment meets the Snapshot 26.x+ standards.

## 🛠️ Requirements
- **JDK 25**: We use features like Pattern Matching and Virtual Threads.
- **Gradle 9.3+**: Required for Loom 1.15 compatibility.
- **Loom 1.15+**: Our primary build tool.

## 📦 Dependency (Gradle)
Add **DasikLibrary** if you are building an addon that relies on our GameRule system.

```gradle
repositories {
    maven { url = "https://maven.rifaditya.com/repository/maven-public/" }
}

dependencies {
    modImplementation "net.dasik:dasik-library:${project.dasik_library_version}"
}
```

## 🧪 Testing
We recommend using **Prism Launcher** for client testing and a headless Linux container for server verification.

```powershell
# Run the client
./gradlew runClient

# Run the server
./gradlew runServer
```
