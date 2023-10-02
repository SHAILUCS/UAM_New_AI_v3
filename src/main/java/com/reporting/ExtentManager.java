package com.reporting;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.aventstack.extentreports.AnalysisStrategy;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.config.Config;
import com.config.FrameworkConfig;
import com.listener.TestNGKeys;
import com.reporting.snapshot.SnapshotManager;
import com.selenium.Custom_ExceptionHandler;

class ExtentManager {
	private static ExtentReports extent;
	private static final String filePath = FrameworkConfig.getResultextenthtmlfilePath();
	static Map<Integer, ExtentTest> extentTestMap = new HashMap<Integer, ExtentTest>();

	// called by en execution finish, i.e. at suite end
	static void flush() {
		System.out.println(
				"===============================================================================\n"
				+ convertToHHMMSS(new Date().getTime()) + " | " + Thread.currentThread().getId() + " | INFO | "
				+ "Extent Report Generation STARTED for TEST "+SnapshotManager.getName()+ " on "+ new Date());
		try {
			extent.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(
				convertToHHMMSS(new Date().getTime()) + " | " + Thread.currentThread().getId() + " | INFO | "
				+ "Extent Report Generation COMPLETED for TEST "+SnapshotManager.getName()+ " on " + new Date()
				+ "\n===============================================================================");
	}
	
	/** Converts the passed millisecond value to hh:mm:ss.sss format */
	static String convertToHHMMSS(long millis) {
		String hhmmss = "";
		try {
			DateFormat formatter = new SimpleDateFormat("hh:mm:ss.sss");
			Calendar calendar = Calendar.getInstance();
			calendar.setTimeInMillis(millis);
			hhmmss = formatter.format(calendar.getTime());
		} catch (Exception e) {
			new Custom_ExceptionHandler(e);
		}
		return hhmmss;
	}


	// called by onStart
	static synchronized ExtentReports GetExtentReports(HashMap<TestNGKeys, String> testData) {
		if (extent != null)
			return extent; // avoid creating new instance of html file
		extent = new ExtentReports();
		extent.setSystemInfo("Test Environment", Config.getEnvironment());
		extent.setSystemInfo("Parallel Mode", testData.get(TestNGKeys.parallel));
		extent.setSystemInfo("Assertion Enabled", FrameworkConfig.ENABLE_ASSERTIONS + "");
		extent.setSystemInfo("Capturing Snapshots", FrameworkConfig.enableCaptureSnapshots() + "");
		extent.setAnalysisStrategy(AnalysisStrategy.TEST);
		ExtentSparkReporter spark = new ExtentSparkReporter(filePath);
		extent.attachReporter(spark);
		return extent;
	}

	// called by onStart
	static synchronized ExtentReports GetExtentReports(String suiteName, String inParallel) {
		if (extent != null)
			return extent; // avoid creating new instance of html file
		extent = new ExtentReports();
		extent.setSystemInfo("Test Environment", Config.getEnvironment());
		extent.setSystemInfo("Parallel Mode", inParallel);
		extent.setSystemInfo("Assertion Enabled", FrameworkConfig.ENABLE_ASSERTIONS + "");
		extent.setSystemInfo("Capturing Snapshots", FrameworkConfig.enableCaptureSnapshots() + "");
		// extent.attachReporter(getHtmlReporter("Environment: '"
		// +Config.getEnvironment() + "'"));
		extent.setAnalysisStrategy(AnalysisStrategy.TEST);
		ExtentSparkReporter spark = new ExtentSparkReporter(filePath);
		extent.attachReporter(spark);
		return extent;
	}

	// called by onTestStart
	static synchronized ExtentTest createTest(HashMap<TestNGKeys, String> testDataMap) {
		String startTag = "";
		String endTag = "";

		ExtentTest extentTest = extent.createTest(
				testDataMap.get(TestNGKeys.description) + " | OS: " + startTag + testDataMap.get(TestNGKeys.platform_Desktop_Mobile)
						+ endTag + "" + " | Browser: " + startTag + testDataMap.get(TestNGKeys.browserName) + " ["
						+ testDataMap.get(TestNGKeys.browserVersion) + endTag + "] |" 
						+ " | Locale: " + startTag	+ testDataMap.get(TestNGKeys.locale) + "" + " |",
				"| Class: " + startTag + testDataMap.get(TestNGKeys.className) + endTag + " | Method: " + startTag
						+ testDataMap.get(TestNGKeys.methodName) + endTag + " | Test: " + startTag
						+ testDataMap.get(TestNGKeys.test) + endTag + " | Priority: " + startTag
						+ testDataMap.get(TestNGKeys.priority) + endTag + " | Environment: " + startTag
						+ testDataMap.get(TestNGKeys.environment) + endTag + " | Depends on: " + startTag
						+ testDataMap.get(TestNGKeys.dependsOn) + endTag + " | Parameters: " + startTag
						+ testDataMap.get(TestNGKeys.parameters) + endTag + " | Group: " + startTag
						+ testDataMap.get(TestNGKeys.group) + endTag + " | Locale: " + startTag
						+ testDataMap.get(TestNGKeys.locale) + endTag + " |");
		extentTestMap.put((int) (long) (Thread.currentThread().getId()), extentTest);
		return extentTest;
	}

	// called by onTestStart
	/*
	 * static synchronized ExtentTest createTest(String name, String description,
	 * String testNG_testName, String testNG_suiteName) { ExtentTest extentTest =
	 * extent.createTest(name, description + " | TestNG-test : " + testNG_testName +
	 * " | TestNG-suite : " + testNG_suiteName); extentTestMap.put((int) (long)
	 * (Thread.currentThread().getId()), extentTest); return extentTest; }
	 */

	// called by @test methods
	static synchronized ExtentTest getExtentTest() {
		return extentTestMap.get((int) (long) (Thread.currentThread().getId()));
	}

	static synchronized ExtentTest createNode(String description) {
		ExtentTest extentNode = null;
		try {
			extentNode = getExtentTest().createNode(description);
			extentTestMap.put((int) (long) (Thread.currentThread().getId()), extentNode);
		} catch (Exception e) {
		}
		return extentNode;
	}

	static void logExtentTest(String Constan, String description, String url) {
		try {
			ExtentTest test = getExtentTest();
			if (Constan.equalsIgnoreCase(STATUS.FAIL.value) || Constan.equalsIgnoreCase(STATUS.FATAL.value)
					|| Constan.equalsIgnoreCase(STATUS.ERROR.value)) {
				if (url == null || "".equals(url)) {
					test.log(ExtentManager.getStatus(Constan), description);
				} else {
					test.log(ExtentManager.getStatus(Constan), description,
							MediaEntityBuilder.createScreenCaptureFromPath(url).build());
				}
			} else {
				test.log(ExtentManager.getStatus(Constan), description);
			}
		} catch (Exception e) {
			// e.printStackTrace();
		}
	}

	static Status getStatus(String status) {
		Status val = null;
		if (status.equalsIgnoreCase(STATUS.PASS.value)) {
			val = Status.PASS;
		} else if (status.equalsIgnoreCase(STATUS.FAIL.value)) {
			val = Status.FAIL;
		} else if (status.equalsIgnoreCase(STATUS.FATAL.value)) {
			val = Status.FAIL;
		} else if (status.equalsIgnoreCase(STATUS.ERROR.value)) {
			val = Status.FAIL;
		} else if (status.equalsIgnoreCase(STATUS.SKIP.value)) {
			val = Status.SKIP;
		} else if (status.equalsIgnoreCase(STATUS.INFO.value)) {
			val = Status.INFO;
		} else if (status.equalsIgnoreCase(STATUS.WARNING.value)) {
			val = Status.WARNING;
		}
		return val;
	}

	static void setMovieLink(String testExecutionMoviePath) {

		String movieTag = " <a href='" + testExecutionMoviePath + "' target='_blank'>MOVIE</a>";
		ExtentTest test = getExtentTest();
		if (test != null) {
			String desc = test.getModel().getDescription();

			if (desc == null) {
				desc = movieTag;
			} else {
				desc = desc + movieTag;
			}
			test.getModel().setDescription(desc);
		}
	}

}
