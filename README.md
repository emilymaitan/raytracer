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

# Features # 
- Parse and validate XML scene files. 
- Create images of scene files and ray directions. 
- Phong illumination. 

# Troubleshooting

**Gradle isn't working!**
Make sure that you have your JAVA_HOME set, and the wrapper file lies at gradle/wrapper/.
The gradlew script is auto-generated but very verbose, check its error messages!

# Q&A

**Some functions are implemented, but not used! Why?**
I'm using this same project for Lab4b. I didn't clean up the debug functions, 
nor the already existing code for upcoming features (shadows, more Surface types, etc.).

**Why JAVA?**
Several reasons! Most importantly, JAVA is the language I have most experience with. 
It runs on virtually every machine, and provides built-in XML, Image IO and Unit testing.