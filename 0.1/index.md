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

