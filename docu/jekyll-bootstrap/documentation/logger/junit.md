---
layout: documentation
title: Junit Logger
---
{% include JB/setup %}
{% include themes/antw/example.md %}

##### JunitLogger
This logger log all tests (that is shown in the tree view as well) into a file called *junit_plain.csv*. This is a comma separated value file. Consisting of 6 columns

    TestSuite, TestCase, DurationInSec, TestGroup, Status, TestFailureMessage

The test group are a kind of category based on the duration. It is currently a hardcoded value.

    FLASH	<	FAST	<	ONE_MIN		<	THREE_MIN	<	SEVEN_MIN	<	UUH
    1 sec	<	10 sec	<	1 min		<	3 min		< 	7 min		<	?

![JunitLogger](junit_logger.png)
