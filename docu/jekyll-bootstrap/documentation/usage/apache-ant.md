---
layout: documentation
title: Usage Ant
---
{% include JB/setup %}

## Usage

### Apache-Ant
It is not recommended but When you want to try the antw loggers without to install *antw* you can integrate the loggers manually to your ant build.
Goto the [downloads](/downloads) page and download the *antw-common-0.6.jar* and the *antw-logger-0.6.jar* and add these jar files to the cmd line when execute ant.

    ant -lib antw-common-0.6.jar:antw-logger-0.6.jar -logger antw.logger.AntwLogger $@

You should see the same logging like when you use the antw app itself. But strongly recommended is to install the whole app.


