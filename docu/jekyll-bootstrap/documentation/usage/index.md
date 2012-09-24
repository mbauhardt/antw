---
layout: documentation
title: Antw - Usage
---
{% include JB/setup %}

## Usage

### Antw
The recommended way to use *antw* is to download/install the whole antw project, see above. 
There are two commands to use *antw*.

    antw - executes apache ant with some special loggers
    antw-update - update your antw installation

##### Optional
It is also recommended but optional to enable the antw junit formatter in your ant project when you use  *antw*. Goto the download page

    https://github.com/mbauhardt/antw/downloads

and download the *antw-common.jar* and the *antw-junit.jar* and add these jar files to your classpath. After that enable the formatter

    <formatter classname="antw.junit.JUnitFormatter" usefile="false"/>

### Apache-Ant
When you want to try the antw loggers without to install *antw* you can try to integrate the loggers manually to your ant build.
Download the *antw-common.jar* and the *antw-logger.jar* and add these jar files to the cmd line when execute ant.

    ant -lib antw-common-0.5.jar:antw-logger-0.5.jar -logger antw.logger.AntwLogger $@

You should see the same logging like when you use the antw app itself. But strongly recommended is to install the whole app.


