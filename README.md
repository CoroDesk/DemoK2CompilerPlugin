# Simple Kotlin K2 Compiler Plugin
This project defines a Kotlin K2 Compiler Plugin in the `:compiler` module. It validates the instantiations of classes with the type `com.names.inspector.Dog` and prints a warning in the build output log if a Dog is defined with a name ending in `a`.

The `:app` module uses the Kotlin K2 Compiler Plugin, which is loaded from the local Maven repository.

## Testing the Plugin

To test the K2 compiler, you first need to publish it to the local Maven repository using the command:
```
./gradlew :compiler:publishAllToLocalMaven
```

Follow these steps:

1. Run `./gradlew :compiler:publishAllToLocalMaven` to publish the K2 Compiler Plugin and its dependencies to your local Maven. This works successfully. ✅
2. Run `./gradlew :app:clean :app:compileKotlinJvm` to see the compiler plugin in action. This also works successfully. ✅
3. Run `./gradlew :app:clean :app:build` to attempt the multiplatform build process. However, this currently fails on iOS targets. ❌