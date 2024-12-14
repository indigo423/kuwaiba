# Kuwaiba Open Source Inventory

This repository is an experimental github fork of [Kuwaiba on sourceforge](https://sourceforge.net/projects/kuwaiba/)

It has been created to demonstrate the benefits of git and github actions for automatic CI/CD

# Kuwaiba Introduction
***  

Kuwaiba is an open source, enterprise-grade network inventory system.

This software is provided “AS IS”, with no warranty at all. Install it and use it at your own risk.

This software is dual-licensed EPLv1 and GPLv3, choose the one that fits your purposes, but keep in mind that we ship third-party libraries whose licenses are only compatible with GPL. See THIRDPARTY and LICENSE.EPL/LICENSE.GPL3 files for details.

Visit https://www.kuwaiba.org or subscribe to our YouTube channel (https://www.youtube.com/user/neotropicco) for more tutorials and guides on how to build, install and use the application.

Get superb enterprise support from Neotropic SAS! (https://www.neotropic.co)


# Changes in this repository

This is an experimental exercise to show some recommended changes to the project if it is migrated to github.
The intention is that the code should be suitable for a CI/CD pipeline using github actions which runs on each checkin without manual intervention. 

To build the code and all of its dependencies got to the top kuwaiba directory and type

```
mvn clean install
```

* The code in this repository was copied from a snapshot of subversion on 12th Dec 2024.
* No attempt has  been made to include the full subversion commit history although this can be done with a more sophisticated port.
* The flat layout of subversion has been replaced with tags corresponding to each version folder
* If a migration to github is planned, we would recommend further reordering so that the major releases are maintained in separate branches and the `components` dependencies are also branched as part of the full build.
* We have added a .gitignore to avoid checkin of target and built resources this includes ignoring node_modules/, webpack.config.js, webpack.generated.js. 
In general no IDE specific or any generated code should be checked in.
* We would recommend the build only contains IDE agnostic maven build instructions  and no IDE specific configuration. 
* We have added a parent pom.xml which will build the components and server projects together. 
A master build pom has also been added to the components dependencies. 
Note that this only builds modules necessary for 2.2.2-SNAPSHOT and the gmaps-flow/trunk module had to be fixed so it would compile.
* We have modified the trunk pom.xml to use 2.2.2-SNAPSHOT so that builds will be done on each checkin and the new artifacts will be used down stream.
* Ideally a dedicated maven repo ( artifactory / jfrog etc) would be set up to store the release and snapshot jars from a build
* The 2.2.x branch requires java 11. Lambock will not not let thecode compile with later java versions and there may be other problems. We note that kuwaiba 2.5 targets java 17. 
* We have only built the code but we have not tested the functionality. 
We note that the build contains no unit tests and would recommend that at least some final integration smoke tests are provided.
* This build does not sign the jars. For better security, jar signing should be introduced.
* We have not (yet) recreated a docker container for the code as part of the build. We would recommend that the container is built after the main build using the signed jars.

