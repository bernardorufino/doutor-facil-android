Environment Setup
=================

Java
----
Download and install JDK 7 (preferrably 7u51, aliased as 1.7.0_51)
[here](http://www.oracle.com/technetwork/java/javase/downloads/jdk7-downloads-1880260.html), since we'll be using
language level 7.


Android
-------
Download the Android SDK from [here](https://developer.android.com/sdk) under "USE AN EXISTING IDE". Open the
Android SDK Manager and be sure to have Android 4.4.2 (API 19) checked for download as well as the following
installed packages:
![SDK Manager](https://dl.dropboxusercontent.com/s/9du38t5jw49i6y2/Screenshot%202014-03-27%2001.29.20.png)


Gradle
------
Since Gradle is our dependency manager, you need to download it and follow install instructions on its
[home page](http://www.gradle.org/downloads)


Variables
---------
Make sure you have the following environment variables set:

* **ANDROID_SDK**: The root location of the Android SDK.
* **ANDROID_HOME**: Just an alias for ANDROID_SDK, make it point to ANDROID_SDK.
* **GRADLE_HOME**: The root path for the Gradle installation folder.
* **JAVA_HOME**: See http://stackoverflow.com/a/6588410 for OSX. For Windows, please search.

Also add the following paths to your **PATH** variable:

* ${ANDROID_SDK}/tools
* ${ANDROID_SDK}/platform-tools
* ${GRADLE_HOME}/bin


IntelliJ
--------

We'll be using IntelliJ as our main IDE, so please download the version 13.1.1 (the latest as of writing this guide)
at this [link](http://www.jetbrains.com/idea/download). Note that there were notable changes from 13 and 13.1.0,
so please update. If it doesn't install itself with the Gradle plugin, please install it manually (Google it,
enough links!).


Git
---

If you are reading this guide locally, great! Just skip this part. If you are following this guide through GitHub,
please [install and setup Git properly](https://help.github.com/articles/set-up-git) and then do
```
git clone https://github.com/bernardorufino/doutor-facil-android.git
```

Extra configurations
--------------------

Once with the project cloned locally, open it with IntelliJ, you should have some errors regarding JDK and Android
SDK locations, just follow the steps to proper setup them and PLEASE SETUP THE JDK NAME TO 1.7 since this convention is
version-controlled in git, thus, we need to follow it. (See this
[post](http://cschablin.blogspot.com.br/2011/06/sharing-intellij-idea-project-files.html) for more info).

Ready
-----

If everything worked out, great! If not [google it](http://www.google.com).