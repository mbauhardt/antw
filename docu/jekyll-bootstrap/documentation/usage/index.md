---
layout: documentation
title: Antw - Usage
---
{% include JB/setup %}

## Usage

### Antw
The recommended way to use *antw* is to download/install the whole antw project, see [documentation](http://www.antw.cc/documentation/get-remove/#installation). 
There are two commands to use *antw*.

    antw - executes apache ant with some special loggers
    antw-update - update your antw installation

#### Junit Formatter
It is also recommended but optional to enable the antw junit formatter in your ant project when you use  *antw*. There are two junit formatter you can use. One is the *antw.junit.JUnitFormatter* and the other is class is *antw.junit.JUnitProfilerFormatter*.
The *JUnitProfileFormatter* extends the *JUnitFormatter* but profile your code and print some profiling messages about your code.

##### antw.junit.JUnitFormatter
Goto the download page

    http://www.antw.cc/downloads

and download the *antw-junit-0.6-fat.jar* and add this jar files to your classpath. After that enable the formatter within your *test target* in your build.xml

    <formatter classname="antw.junit.JUnitFormatter" usefile="false"/>

##### antw.junit.JUnitProfilerFormatter
Goto the download page

    http://www.antw.cc/downloads

and download the *antw-junit-0.6-fat.jar* and *antw-profiler-0.6-fat.jar* add this jar files to your classpath.
To enable the profiler you need to add the profiler and the formatter jar to your *test target* in your build.xml.

	<target name="test" depends="jar, unit-jar">
		<mkdir dir="build/reports" />
		<junit haltonfailure="true" fork="yes" forkmode="once">
			<jvmarg value="-javaagent:lib/provided/antw-profiler-0.6-fat.jar"/>
			<sysproperty key="antw-transform.properties" value="antw-transform.properties"/>
			<formatter classname="antw.junit.JUnitProfilerFormatter" usefile="false"/>
			
			<classpath>
				<path refid="compile.classpath" />
				<path refid="artifact.classpath" />
			</classpath>

			<batchtest fork="yes" todir="build/reports">
				<fileset dir="src/test/java">
					<include name="**/*Test.java" />
				</fileset>
			</batchtest>
		</junit>
    </target>


### Apache-Ant
It is not recommended but When you want to try the antw loggers without to install *antw* you can integrate the loggers manually to your ant build.
Goto the download page

    http://www.antw.cc/downloads

and download the *antw-common-0.6.jar* and the *antw-logger-0.6.jar* and add these jar files to the cmd line when execute ant.

    ant -lib antw-common-0.6.jar:antw-logger-0.6.jar -logger antw.logger.AntwLogger $@

You should see the same logging like when you use the antw app itself. But strongly recommended is to install the whole app.


