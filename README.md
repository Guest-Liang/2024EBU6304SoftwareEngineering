# Description
Childbank is a simulated banking application that we developed for children and their parents to record account passwords and provide the child or parent with functions such as checking, withdrawing money and completing transactions.

# Software Architecture
This software is written in Java language , generated in the Java environment can be run in the jar package for the convenience of users . The graphical interface is built using Java Swing.

# Installation

The application needs to run in java environment, please make sure your java version is openjdk-21. Lower versions of jdk are untested and the software may not run properly. If you don't have Java environment please go to [https://www.oracle.com/java/technologies/downloads/#java21](https://www.oracle.com/java/technologies/downloads/#java21) to download and install it.

develop environment:
```powershell
java --version

Picked up JAVA_TOOL_OPTIONS: -Dfile.encoding=UTF-8
java 21 2023-09-19 LTS
Java(TM) SE Runtime Environment (build 21+35-LTS-2513)
Java HotSpot(TM) 64-Bit Server VM (build 21+35-LTS-2513, mixed mode, sharing)
```

# Instructions
## Run in the graphical interface
Open a terminal, enter the directory of this README.md, and run   
```powershell
java -cp childBank.jar Main
```

## If you have vscode environment:
Open the project in vscode, and run the `Main.java` file in the src folder.   
Use `run java`, do not use `run code`. Or simply just press `F5` key to run the program.