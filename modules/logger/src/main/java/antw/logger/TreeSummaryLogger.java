package antw.logger;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.tools.ant.BuildEvent;
import org.apache.tools.ant.taskdefs.Tstamp;

import antw.common.util.Constants;
import antw.common.util.TimeUtil;

public class TreeSummaryLogger extends TreeLogger {

    private static final String HEADER_FORMAT = "%-25s %-25s %-35s %-35s %-35s %-35s%n";
    private static final String DURATION_FORMAT = "%-25s %-25s %-35s %-35s %-35s %-35s%n";

    private static final Pattern DURATION_PATTERN = Pattern
	    .compile("([0-9\\s,]+)(hrs|min|sec|ms)");

    private String _lastTestSuite;
    private Map<String, List<String>> _testModules = new LinkedHashMap<String, List<String>>();
    private Map<String, List<String>> _testSuites = new LinkedHashMap<String, List<String>>();
    private Map<String, Long> _testCases = new LinkedHashMap<String, Long>();

    public TreeSummaryLogger() {
	super("treesummary", new LoggerContext());
    }

    public TreeSummaryLogger(LoggerContext context) {
	super("treesummary", context);
    }

    @Override
    protected void printTestSuite(String message) {
	super.printTestSuite(message);
	_lastTestSuite = parseTestSuite(message);
	String module = _lastProject.getName();
	if (!_testModules.containsKey(module)) {
	    _testModules.put(module, new ArrayList<String>());
	}
	_testModules.get(module).add(_lastTestSuite);
	_testSuites.put(_lastTestSuite, new ArrayList<String>());
    }

    private String parseTestSuite(String message) {
	String[] split = message.substring(
		message.indexOf(Constants.TEST_SUITE_LABEL)).split("\\s+");
	return split[1];
    }

    @Override
    protected void printTestCase(String message) {
	super.printTestCase(message);
	long duration = parseDuration(message);
	String testCase = parseTestCase(message);
	_testSuites.get(_lastTestSuite).add(testCase);
	_testCases.put(testCase, duration);
    }

    private String parseTestCase(String message) {
	String[] split = message.substring(
		message.indexOf(Constants.TEST_CASE_LABEL)).split("\\s+");
	return split[1];
    }

    private long parseDuration(String message) {
	Matcher matcher = DURATION_PATTERN.matcher(message);
	matcher.find();
	String group = matcher.group();
	return TimeUtil.convertTimeDuration(group.trim());
    }

    @Override
    public void buildFinished(BuildEvent event) {
	if (event.getException() != null) {
	    newLine(3);
	    err(event.getException(), false);
	    newLine(3);
	    out("BUILD FAILED :(");
	    out("Total Time: " + _context.getProjects().getDurationAsString());
	} else {
	    newLine(3);
	    out("BUILD SUCCESSFUL :)");
	    out("Total Time: " + _context.getProjects().getDurationAsString());
	}

	printTestSummary();
	newLine(2);
    }

    private void printTestSummary() {
	newLine();
	newLine();
	out("Test Summary");
	newLine();

	logHeader();
	List<Duration> durations = computeAverageDuration();
	for (Duration duration : durations) {
	    out(DURATION_FORMAT,
		    new Object[] {
			    duration._module,
			    duration._testCaseCount,
			    TimeUtil.formatTimeDuration(duration._overAllDuration),
			    TimeUtil.formatTimeDuration(duration._averageDuration),
			    duration._fastestTestSuite,
			    duration._slowestTestSuite });
	}

    }

    private class Duration {
	String _module;
	int _testCaseCount;
	long _overAllDuration;
	long _averageDuration;
	String _slowestTestSuite;
	String _fastestTestSuite;
    }

    private List<Duration> computeAverageDuration() {
	List<Duration> durations = new ArrayList<TreeSummaryLogger.Duration>();
	Set<String> modules = _testModules.keySet();
	for (String module : modules) {
	    long fastestTestSuiteDuration = Long.MAX_VALUE;
	    long slowestTestSuiteDuration = 0;
	    long moduleDuration = 0;
	    int testCaseCount = 0;
	    String fastestTestSuite = "";
	    String slowestTestSuite = "";
	    List<String> testsuites = _testModules.get(module);
	    for (String testsuite : testsuites) {
		long testSuiteDuration = 0;
		List<String> testcases = _testSuites.get(testsuite);
		for (String testcase : testcases) {
		    Long duration = _testCases.get(testcase);
		    testSuiteDuration += duration;
		    moduleDuration += duration;
		    testCaseCount++;
		}
		if (testSuiteDuration < fastestTestSuiteDuration) {
		    fastestTestSuiteDuration = testSuiteDuration;
		    fastestTestSuite = testsuite.substring(testsuite
			    .lastIndexOf(".") + 1);
		}
		if (testSuiteDuration > slowestTestSuiteDuration) {
		    slowestTestSuiteDuration = testSuiteDuration;
		    slowestTestSuite = testsuite.substring(testsuite
			    .lastIndexOf(".") + 1);
		    ;
		}
	    }
	    Duration durationPackage = new Duration();
	    durationPackage._module = module;
	    durationPackage._testCaseCount = testCaseCount;
	    durationPackage._overAllDuration = moduleDuration;
	    durationPackage._averageDuration = moduleDuration / testCaseCount;
	    durationPackage._fastestTestSuite = (fastestTestSuite + " ("
		    + TimeUtil.formatTimeDuration(fastestTestSuiteDuration) + ")");
	    durationPackage._slowestTestSuite = (slowestTestSuite + " ("
		    + TimeUtil.formatTimeDuration(slowestTestSuiteDuration) + ")");
	    durations.add(durationPackage);
	}
	return durations;
    }

    public static void main(String[] args) {
	Pattern pattern = Pattern.compile("([0-9\\s,]+)(hrs|min|sec|ms)");
	String s = "  [testcase] testValidateArgumentTypeTheSameOrNumeric_invalid                                 0 ms            PASSED    ";
	Matcher matcher = pattern.matcher(s);
	matcher.find();
	System.out.println(matcher.group().trim());
    }

    private void logHeader() {
	out(HEADER_FORMAT, new Object[] { "Module", "Test Case Count",
		"Overall Duration", "Average Duration Test Case",
		"Fastest Test Suite", "Slowest Test Suite" });
	String line = "";
	for (int i = 0; i < 180; i++) {
	    line += "-";
	}
	out(line);
    }
}
