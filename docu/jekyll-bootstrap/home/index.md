---
layout: page
title: Home
group: main_navigation
icon: icon-home icon-black
permalink: /
weight: 0
---
{% include JB/setup %}

### What is antw
Antw is a wrapper which contains [apache ant](http://ant.apache.org) with some nice features. 
Antw attach at runtime a bunch of loggers to log some helpful statistics about your ant build. 
All statistics are logged into files under directory called *antw* located in your build directory (usually *build/antw*).

The current main features of antw are

+ many loggers which logs very helpful statistics about your ant targets
+ junit formatters to log statictics about your junit tests
+ a built in profiler which logs profiling statistics about your code   

