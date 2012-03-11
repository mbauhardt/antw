package antw.util;

import junit.framework.Test;

import org.apache.tools.ant.taskdefs.optional.junit.JUnitTest;
import org.apache.tools.ant.taskdefs.optional.junit.JUnitVersionHelper;

public class TestUtil {

    public static String getSimpleClassName(String fullName) {
        return fullName.substring(fullName.lastIndexOf("."));
    }

    public static String getFullQualifiedName(Test test) {
        return getNameOfClass(test) + "." + getNameOfTestCase(test);
    }

    public static String getSuiteName(JUnitTest unitTest) {
        return unitTest.getName();
    }

    public static String getSuiteName(Test test) {
        return getNameOfClass(test);
    }

    public static String getNameOfTestCase(Test test) {
        if (test == null) {
            return "<null>";
        }
        return JUnitVersionHelper.getTestCaseName(test);
    }

    public static String getNameOfClass(Test test) {
        if (test == null) {
            return "<null>";
        }
        return JUnitVersionHelper.getTestCaseClassName(test);
    }
}
