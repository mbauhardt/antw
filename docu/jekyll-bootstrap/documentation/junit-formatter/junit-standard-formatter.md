---
layout: documentation
title: JUnit Standard Formatter
---
{% include JB/setup %}


### JUnit Formatter
It is also recommended but optional to enable the antw junit formatter in your ant project when you use  *antw*. There are two junit formatter you can use. 
One is the *antw.junit.JUnitStandardFormatter* and the other is class is *antw.junit.JUnitProfilerFormatter*.

#### antw.junit.JUnitStandardFormatter
Goto the [downloads](/downloads) page and download the *antw-junit-0.6-fat.jar* and add this jar files to your classpath. After that enable the formatter within your *test target* in your build.xml

	<target name="test" depends="jar, unit-jar">
		<mkdir dir="build/reports" />
		<junit haltonfailure="true" fork="yes" forkmode="once">
			<formatter classname="antw.junit.JUnitStandardFormatter" usefile="false"/>
			
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

The [tree logger](/documentation/logger/tree.html), [junit logger](/documentation/logger/junit.html), [message logger](/documentation/logger/message.html) and [apache ant default logger](/documentation/logger/ant-default.html) will filter and log these messages.