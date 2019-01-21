# Readme for Devs

# Build & Run
This project uses the build automation tool `gradle` due to its 
compatibility with Java and its performance and ease of use / understandability. 
This project comes WITHOUT ANY external libraries, but instead with a gradle 
wrapper script which downloads these dependencies for you.

From the same directory as this README is in, open your shell of preference and
type in the following commands: 

- `./gradlew build`
    - this will download all external jar files, and build the project
- `./gradlew run --args "arg0 arg1 arg2"`
    - this will automatically execute the main method as specified in the `build.gradle.kts` file
    - other tasks include `clean` (remove all build files) and `javadoc`
    - arguments: {file <'full/file/path'> | scene {example1|example2} | color}
    
For Windows, use `gradlew.bat` instead.

# Known Bugs #
- Camera transformations are not correctly implemented.

# Fixed Bugs #
- Phong illumination now correctly calculates specular highlights.
- All light sources are now correctly parsed and parallel light has been fixed.
- Matrix multiplication now works properly, and non-square multiplication is supported!
- Proper error message for not existing files!

# New Features # 
- Parse Triangles. 
- Shadows.
- Basic Reflection.

# Unfinished / Buggy Features #
- Textures currently only work on spheres.
- Transformation matrices have been implemented, but don't apply correctly.

# Main Sources for this project
- "Shirley/Marshner": Fundamentals of Computer Graphics
- "scratchapixel": https://www.scratchapixel.com 
- Youtube Series "Grafik in Java": https://www.youtube.com/playlist?list=PLNmsVeXQZj7rvhHip_LTM-J-5wHw7dWnw

# Troubleshooting

**Gradle isn't working!**
Make sure that you have your JAVA_HOME set, and the wrapper file lies at gradle/wrapper/.
The gradlew script is auto-generated but very verbose, check its error messages!

# Q&A

**Why JAVA?**
Several reasons! Most importantly, JAVA is the language I have most experience with. 
It runs on virtually every machine, and provides built-in XML, Image IO and Unit testing.