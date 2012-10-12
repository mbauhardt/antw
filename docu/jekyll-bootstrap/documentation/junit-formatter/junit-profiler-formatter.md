---
layout: documentation
title: JUnit Profiler Formatter
---
{% include JB/setup %}

### JUnit Formatter
It is also recommended but optional to enable the antw junit formatter in your ant project when you use  *antw*. 
The class *antw.junit.JUnitProfilerFormatter* extends the *JUnitFormatter* but profile your code and print some profiling messages about your code.

#### antw.junit.JUnitProfilerFormatter
Goto the [downloads](/downloads) page and download the *antw-junit-0.6-fat.jar* and *antw-profiler-0.6-fat.jar* add this jar files to your classpath.
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

The [tree logger](/documentation/logger/tree.html), [junit profiler logger](/documentation/logger/junit-profiler.html), [message logger](/documentation/logger/message.html) and [apache ant default logger](/documentation/logger/ant-default.html) will filter and log these messages.