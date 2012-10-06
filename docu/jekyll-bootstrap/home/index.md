---
layout: page
title: Home
group: main_navigation
icon: icon-home icon-black
permalink: /
weight: 0
---
{% include JB/setup %}


<div class="row-fluid">
	<div class="span5">
		<h3>What Is Antw</h3>
		<p>
			Antw is a wrapper which contains <a href="http://ant.apache.org">apache ant</a> with some nice features.
			The main goal is to have an application which provides an easy installer for <i>apache ant</i> and a better logging to see statistics about your build.
			Antw provides those statistics. All reports are logged into files located in your build directory (usually <b>build/antw</b>).
		</p>
	</div>
	<div class="span7">
		<ul class="thumbnails">
		  <li class="span2">
		    <a href="tree.png" class="thumbnail">
		      <img src="tree.png" alt="">
		    </a>
		  </li>
		</ul>
	</div>
</div>



<div class="row-fluid">&nbsp;</div>
<div class="row-fluid">&nbsp;</div>
<div class="row-fluid">&nbsp;</div>

<div class="row-fluid">
	<div class="span4">
		<h4>Installer</h4>
		<p>
			The installer will install the latest stable version of <a href="http://ant.apache.org">apache ant</a>. 
			You need no administrator privileges to install antw. 
			Furthermore you have an easy to use updater which updates your antw/apache ant installation.    		
		</p>
	</div>
	<div class="span4">&nbsp;</div>
	<div class="span4">&nbsp;</div>
</div>

<div class="row-fluid">
	<div class="span4">
		<h4>Many Build Logger's</h4>
		<p>
			Antw provides many build loggers to log statistics about your ant build. The default logger, which log to standard output, is a kind of tree. 
			It shows only the target names incl. call count and duration. 
			The rest of the build loggers create report files which contains more detailed informations about your ant targets. 		
		</p>
	</div>
	
	<div class="span4">
		<h4>Different JUnit Formatter</h4>
		<p>
			Some JUnit formatter's are also included within <i>antw</i> which provides a cleaner format about the running junit tests.
			The formatting of the running tests contains test duration, test groups or profiling information.
		</p>
	</div>

	<div class="span4">
		<h4>Lightweight Profiler</h4>
		<p>
			A very cool feature is the lightweight profiler. The profiler profiles the running junit tests.
			Creates a snapshot about the current method stack trace for every thread and provide a call count and duration about each method in this stack.
		</p>	
	</div>
</div>


