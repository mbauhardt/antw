---
title: antw version 0.1
---

## Installation
You can install *antw* via *curl* or *wget*  

via curl

    curl -L https://raw.github.com/mbauhardt/antw/latest/src/main/scripts/antw-checkout.sh | sh

or via wget

    wget --no-check-certificate https://raw.github.com/mbauhardt/antw/latest/src/main/scripts/antw-checkout.sh -O - | sh


This will install *antw* under directory

    ~/.antw

After installation add the bin folder to your PATH

    export PATH=$PATH:~/.antw/install/bin



## Desinstallation
Remove the directory *.antw*

    rm -rf ~/.antw



## Usage
There are two commands to use *antw*.

    antw - executes apache ant with some special loggers
    antw-update - update your antw installation


## Example
For example you have a multiproject that has a root project with a core module and two separatly modules.

    project
        build.xml
        core
            build.xml
        modules
            module_1
                build.xml
            module_2
                build.xml

You have a *target* jar that depends on compile and that depends on clean. you see the following logging

[Build Log](antw_usage.png)
