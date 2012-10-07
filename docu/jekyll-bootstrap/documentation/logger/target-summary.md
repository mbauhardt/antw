---
layout: documentation
title: Target Summary Logger
---
{% include JB/setup %}
{% include themes/antw/example.md %}

##### TargetSummaryLogger
This logger will log a summary about your build into a file called *target-summary.txt*. 
The table that is logged shows the project, the regarding target that is executed in this project and a duration in seconds and in percent. 
But you can not sum up all the seconds or the all the percentage of every module to get a 100% coverage. 
When a target depends on few other targets, then this target has a duration of the sum of all depending targets. 
Targets that are under 5% of the duration from the build will be excluded.

![TargetSummaryLogger](target_summary_logger.png)
