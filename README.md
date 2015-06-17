# VRL-NeuronalTopologyImporter-Plugin

This projects provides importing routines for NGX, HOC and SWC file types to UGX.
See Template #1 after installation.

[![Dependency Status](https://www.versioneye.com/user/projects/5581a17f386664002000024f/badge.svg?style=flat)](https://www.versioneye.com/user/projects/5581a17f386664002000024f)

## How To Build The Project

### 1. Dependencies

- JDK >= 1.6
- Internet Connection (other dependencies will be downloaded automatically)
- Optional: IDE with [Gradle](http://www.gradle.org/) support


### 2. Configuration

Specify correct path in `build.properties`, e.g.,
    
    # vrl property folder location (plugin destination)
    vrldir=/Users/myusername/.vrl/0.4.2/default

### 3. Build & Install

#### IDE

To build the project from an IDE do the following:

- open the  [Gradle](http://www.gradle.org/) project
- call the `installVRLPlugin` Gradle task to build and install the plugin
- restart VRL-Studio

#### Command Line

Building the project from the command line is also possible.

Navigate to the project folder and call the `installVRLPlugin` [Gradle](http://www.gradle.org/)
task to build and install the plugin.

##### Bash (Linux/OS X/Cygwin/other Unix-like OS)

    cd Path/To/VRL-NeuronalTopologyImporter-Plugin/VRL-NeuronalTopologyImporter-Plugin
    ./gradlew installVRLPlugin
    
##### Windows (CMD)

    cd Path\To\VRL-NeuronalTopologyImporter-Plugin\VRL-NeuronalTopologyImporter-Plugin
    gradlew installVRLPlugin

Finally, restart VRL-Studio
