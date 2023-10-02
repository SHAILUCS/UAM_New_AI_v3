package com.reporting;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.testng.Assert;

import com.config.Config;
import com.config.FrameworkConfig;
import com.listener.TestNGKeys;
import com.reporting.pojos.Scenario;
import com.reporting.snapshot.SnapshotManager;
import com.reporting.snapshot.SnapshotsMovieMaker;
import com.selenium.Custom_ExceptionHandler;

public class Reporter {
	private static int testCounter = 1;
	static Map<Integer, List<Scenario>> testMap = new HashMap<Integer, List<Scenario>>();
	private static List<ArrayList<Scenario>> listOfList;

	static synchronized List<ArrayList<Scenario>> getListOfList() {
		return listOfList;
	}

	/**
	 * This method will setup the report before execution starts, This is framework
	 * internal method used by TestNG Listeners, It is not to be used in TestNG or
	 * Page Factory classes
	 * 
	 * @author Shailendra
	 */
	public static void onStart(HashMap<TestNGKeys, String> testDataMap) {

		if (listOfList == null) {
			listOfList = new ArrayList<ArrayList<Scenario>>();
		}

		Scenario.setInParallel(testDataMap.get(TestNGKeys.parallel));
		Scenario.setThreadCount(testDataMap.get(TestNGKeys.threadCount));
		Scenario.setTestNG_SuiteName(testDataMap.get(TestNGKeys.suite));
		// TODO Test.setTestNG_TestName(testDataMap.get(TestNGKeys.test));
		Scenario.setTestExecutionStartDate((new Date()).getTime());
		Scenario.setRunMode(testDataMap.get(TestNGKeys.remoteURL));
		ExtentManager.GetExtentReports(testDataMap);
	}

	/**
	 * This method will generate the HTML report once execution is completed for all
	 * tests, This is framework internal method used by TestNG Listeners, It is not
	 * to be used in TestNG or Page Factory classes
	 * 
	 * @author Shailendra
	 */
	public static synchronized void onExecutionFinish() {

		endTest();

		System.out.println(
				"===============================================================================\n"
				+ convertToHHMMSS(new Date().getTime()) + " | " + Thread.currentThread().getId() + " | INFO | "	
						+ "Custom Report Generation STARTED for TEST " + SnapshotManager.getName() + " on "
						+ new Date());

		File file = new File(FrameworkConfig.getResultFolderPath());
		deleteDirectory(file);
		long time_start = Long.MAX_VALUE;
		long time_end = Long.MIN_VALUE;
		// System.out.println("Finished: ");
		Scenario.setTestExecutionEndDate((new Date()).getTime());
		time_start = Math.min(Scenario.getTestExecutionStartDate(), time_start);
		time_end = Math.max(Scenario.getTestExecutionEndDate(), time_end);
		Scenario.setTestExecutionTime(timeConversion((time_end - time_start)));

		// Discontinued on 15 Jan 2019
		// CustomReportHTML_NonXL.createHTML_NonXl();

		CustomReportHTML_Redesign.createHTML();
		System.out.println(
				 convertToHHMMSS(new Date().getTime()) + " | " + Thread.currentThread().getId() + " | INFO | "
				+ "Custom Report Generation COMPLETED for TEST " + SnapshotManager.getName() + " on " + new Date()
				+ "\n===============================================================================");

		ExtentManager.flush();

	}

	/**
	 * This method will create a new Test Object in the report, This is framework
	 * internal method used by TestNG Listeners, It is not to be used in TestNG or
	 * Page Factory classes
	 * 
	 * @author Shailendra
	 */
	public static synchronized void createTest(HashMap<TestNGKeys, String> testDataMap) {

		// Ending the previous test before creating a new test
		endTest();

		// Adding this logic, becoz report failed to run on local
		String env = "";
		env = Config.getEnvironment();

		Scenario s = new Scenario();
		s.setStatus(STATUS.PASS.value);
		s.setTestNG_TestName(testDataMap.get(TestNGKeys.test));
		s.setEnvironment(env);
		s.setPlatform(testDataMap.get(TestNGKeys.platform_Desktop_Mobile));
		s.setAppType(testDataMap.get(TestNGKeys.appType_Web_App));
		s.setScenarioName(testDataMap.get(TestNGKeys.description));
		s.setDescription("");
		s.setStartTime_Scenario((new Date()).getTime());
		s.setTimeStamp(convertToHHMMSS((new Date()).getTime()));
		s.setClassName(testDataMap.get(TestNGKeys.className));
		s.setMethodName(testDataMap.get(TestNGKeys.methodName));
		s.setPriority(testDataMap.get(TestNGKeys.priority));
		s.setDependsOn(testDataMap.get(TestNGKeys.dependsOn));
		s.setGroup(testDataMap.get(TestNGKeys.group));
		s.setParameters(testDataMap.get(TestNGKeys.parameters));
		s.setLocale(testDataMap.get(TestNGKeys.locale));
		s.setOsVersion(testDataMap.get(TestNGKeys.osVersion));
		
		System.out.println("****************************************************************************************");
		System.out.println(testCounter++ + " | "
				+ convertToHHMMSS(new Date().getTime()) + " | " + Thread.currentThread().getId() + " | " + "Scenario: "
				+ testDataMap.get(TestNGKeys.description) + "| Browser: " + testDataMap.get(TestNGKeys.browserName)
				+ "| Platform: " + testDataMap.get(TestNGKeys.platform_Desktop_Mobile) + "| Priority: "
				+ testDataMap.get(TestNGKeys.priority) + "| TestNG-Test: " + testDataMap.get(TestNGKeys.test)
				+ "| Environment: " + testDataMap.get(TestNGKeys.environment));
		getCurrentThreadTestList_FromMap().add(s);

		ExtentManager.createTest(testDataMap);
	}

	private static synchronized void endTest() {
		String testExecutionMoviePath = SnapshotsMovieMaker
				.createMovie(SnapshotManager.getSnapshotDestinationDirectoryName());
		
		ExtentManager.setMovieLink(testExecutionMoviePath);
		
		List<Scenario> testList = getCurrentThreadTestList_FromMap();
		if (testList.size() > 0) {
			Scenario s = testList.get(0);
			long endTime = (new Date()).getTime();
			s.setEndTime_Scenario(endTime);
			long startTime = s.getStartTime_Scenario_InMillSec();
			s.setExecutionTime_Scenario(timeConversion(endTime - startTime));
			s.setBrowserName(null);
			s.setOs_Desktop(null);
			s.setDeviceName_Mobile(null);
			s.setBrowserVersion(null);
			s.setAppium_desired_capabilities(null);
			if (testExecutionMoviePath != null) {
				s.setSnapshotURL(testExecutionMoviePath);
			}
			listOfList.add((ArrayList<Scenario>) getCurrentThreadTestList_FromMap());
			testMap.remove((int) (long) (Thread.currentThread().getId()));
		}
	}

	// called by @test methods
	private static List<Scenario> getCurrentThreadTestList_FromMap() {
		List<Scenario> tempListObj = testMap.get((int) (long) (Thread.currentThread().getId()));
		if (tempListObj == null) {
			tempListObj = new ArrayList<Scenario>();
			testMap.put((int) (long) (Thread.currentThread().getId()), tempListObj);
			// System.out.println("Created an ArrayList for storing the results thread:
			// "+Thread.currentThread().getId());
		} else {
			// System.out.println("Existing ArrayList size: "+tempListObj.size()+" for
			// storing the results thread: "+Thread.currentThread().getId());
		}
		return tempListObj;
	}

	/**
	 * @author shailendra.rajawat Use this method to add the snapshot of passed web
	 *         element object in Report
	 */
	@SuppressWarnings("unused")
	private static synchronized void report(String Constan, String description, Object element) {
		// SessionId session = ((ChromeDriver)BaseTest.getDriver()).getSessionId();
		if (FrameworkConfig.ENABLE_CONSOLE_LOGS) {
			System.out.println(Thread.currentThread().getId() + " | " + Constan + " | " + description);
		}
		String url = "";
		Scenario r = new Scenario();
		r.setTimeStamp(convertToHHMMSS((new Date()).getTime()));
		r.setStatus(Constan);
		r.setDescription(description);
		r.setScenarioName("");

		if (Constan.equalsIgnoreCase(STATUS.FAIL.value) || Constan.equalsIgnoreCase(STATUS.FATAL.value)) {
			url = SnapshotManager.takeSnapShot("failure");
			r.setSnapshotURL(url);
		} else if (element != null) {
			url = SnapshotManager.takeSnapShot("", element);
			r.setSnapshotURL(url);
		}
		// System.out.println("List Size "+customTestList.size()+" for Thread:
		// "+Thread.currentThread().getId());
		getCurrentThreadTestList_FromMap().add(r);
		ExtentManager.logExtentTest(Constan, description, url);
		manageAssertions(Constan, description);
	}

	private static synchronized void report_ExitNode(STATUS status, String description) {
		if (FrameworkConfig.ENABLE_CONSOLE_LOGS) {
			System.out.println(convertToHHMMSS(new Date().getTime()) + " | " + Thread.currentThread().getId() + " | "
					+ status.value + " | " + description);
		}
		String url = "";

		Scenario r = new Scenario();
		r.setTimeStamp(convertToHHMMSS((new Date()).getTime()));
		r.setStatus(status.value);
		r.setDescription(description);
		r.setScenarioName("");
		if (status.value.equalsIgnoreCase(STATUS.FAIL.value) || status.value.equalsIgnoreCase(STATUS.FATAL.value)) {
			url = SnapshotManager.takeSnapShot("failure");
			r.setSnapshotURL(url);
		}
		// System.out.println("List Size "+customTestList.size()+" for Thread:
		// "+Thread.currentThread().getId());
		getCurrentThreadTestList_FromMap().add(r);

		ExtentManager.logExtentTest(status.value, description, url);
		manageAssertions(status.value, description);
	}

	/**
	 * Use this method to report a log in HTML report. Please note that the log will
	 * be put inside the Node.
	 * 
	 * @author Shailendra
	 */
	public static synchronized void PASS(String description) {
		report(STATUS.PASS, description);
	}

	/**
	 * Use this method to report a log in HTML report. Please note that the log will
	 * be put inside the Node.
	 * 
	 * @author Shailendra
	 */
	public static synchronized void FAIL(String description) {
		report(STATUS.FAIL, description);
	}

	/**
	 * Use this method to report a log in HTML report. Please note that the log will
	 * be put inside the Node.
	 * 
	 * @author Shailendra
	 */
	public static synchronized void ERROR(String description) {
		report(STATUS.ERROR, description);
	}

	/**
	 * Use this method to report a log in HTML report. Please note that the log will
	 * be put inside the Node.
	 * 
	 * @author Shailendra
	 */
	public static synchronized void FATAL(String description) {
		report(STATUS.FATAL, description);
	}

	/**
	 * Use this method to report a log in HTML report. Please note that the log will
	 * be put inside the Node.
	 * 
	 * @author Shailendra
	 */
	public static synchronized void INFO(String description) {
		report(STATUS.INFO, description);
	}

	/**
	 * Use this method to report a log in HTML report. Please note that the log will
	 * be put inside the Node.
	 * 
	 * @author Shailendra
	 */
	public static synchronized void SKIP(String description) {
		report(STATUS.SKIP, description);
	}

	/**
	 * Use this method to report a log in HTML report. Please note that the log will
	 * be put inside the Node.
	 * 
	 * @author Shailendra
	 */
	public static synchronized void WARNING(String description) {
		report(STATUS.WARNING, description);
	}

	/**
	 * Use this method to report a log in HTML report. Please note that the log will
	 * be put outside the Node.
	 * 
	 * @author Shailendra
	 */
	public static synchronized void PASS_ExitNode(String description) {
		report_ExitNode(STATUS.PASS, description);
	}

	/**
	 * Use this method to report a log in HTML report. Please note that the log will
	 * be put outside the Node.
	 * 
	 * @author Shailendra
	 */
	public static synchronized void FAIL_ExitNode(String description) {
		report_ExitNode(STATUS.FAIL, description);
	}

	/**
	 * Use this method to report a log in HTML report. Please note that the log will
	 * be put outside the Node.
	 * 
	 * @author Shailendra
	 */
	public static synchronized void ERROR_ExitNode(String description) {
		report_ExitNode(STATUS.ERROR, description);
	}

	/**
	 * Use this method to report a log in HTML report. Please note that the log will
	 * be put outside the Node.
	 * 
	 * @author Shailendra
	 */
	public static synchronized void FATAL_ExitNode(String description) {
		report_ExitNode(STATUS.FATAL, description);
	}

	/**
	 * Use this method to report a log in HTML report. Please note that the log will
	 * be put outside the Node.
	 * 
	 * @author Shailendra
	 */
	public static synchronized void INFO_ExitNode(String description) {
		report_ExitNode(STATUS.INFO, description);
	}

	/**
	 * Use this method to report a log in HTML report. Please note that the log will
	 * be put outside the Node.
	 * 
	 * @author Shailendra
	 */
	public static synchronized void SKIP_ExitNode(String description) {
		report_ExitNode(STATUS.SKIP, description);
	}

	/**
	 * Use this method to report a log in HTML report. Please note that the log will
	 * be put outside the Node.
	 * 
	 * @author Shailendra
	 */
	public static synchronized void WARNING_ExitNode(String description) {
		report_ExitNode(STATUS.WARNING, description);
	}

	/**
	 * This method will add an Step to the Test. Which will then displayed in the
	 * HTML Report
	 * 
	 * @param Status      any of the Constants provided by STATUS enum
	 * @param description Detailed note about the step
	 * @author shailendra.rajawat 05 Jan 2018
	 */
	private static synchronized void report(STATUS Status, String description) {
		// SessionId session = ((ChromeDriver)BaseTest.getDriver()).getSessionId();
		String Constan = Status.value;
		try {
			if (FrameworkConfig.ENABLE_CONSOLE_LOGS) {
				System.out.println(convertToHHMMSS(new Date().getTime()) + " | " + Thread.currentThread().getId()
						+ " | " + Constan + " | " + description);
			}
			String url = "";
			List<Scenario> customResultsList = getCurrentThreadTestList_FromMap();

			Scenario r = new Scenario();
			r.setTimeStamp(convertToHHMMSS((new Date()).getTime()));
			r.setStatus(Constan);
			r.setDescription(description);
			r.setScenarioName("");
			if (Constan.equalsIgnoreCase(STATUS.FAIL.value) || Constan.equalsIgnoreCase(STATUS.FATAL.value)
					|| Constan.equalsIgnoreCase(STATUS.ERROR.value)) {
				url = SnapshotManager.takeSnapShot("failure");
				r.setSnapshotURL(url);
			} else {
				url = SnapshotManager.takeSnapShot("Reported-" + Constan);
				r.setSnapshotURL(url);
			}

			int size = customResultsList.size();
			Scenario t = customResultsList.get(size - 1);
			String status = t.getStatus();
			if (status.equalsIgnoreCase(STATUS.NODE.value)) {
				List<Scenario> nodeList = t.getList();
				nodeList.add(r);
			} else {
				// System.out.println("List Size "+customTestList.size()+" for Thread:
				// "+Thread.currentThread().getId());
				customResultsList.add(r);
			}

			ExtentManager.logExtentTest(Constan, description, url);
			manageAssertions(Constan, description);
		} catch (Exception e) {
		}
	}

	/**
	 * Use this method to create a Node in HTML report. Once you create a Node all
	 * subsequent logs will be put with-in this node, creating a parent-child
	 * hierarchicy in the HTML report. It will increase the readability of the reports.
	 * 
	 * @author Shailendra
	 */
	public static synchronized void NODE(String description) {
		// SessionId session = ((ChromeDriver)BaseTest.getDriver()).getSessionId();
		if (FrameworkConfig.ENABLE_CONSOLE_LOGS) {
			System.out.println(convertToHHMMSS(new Date().getTime()) + " | " + Thread.currentThread().getId() + " | "
					+ STATUS.NODE.value + " | " + description);
		}

		Scenario r = new Scenario();
		r.setTimeStamp(convertToHHMMSS((new Date()).getTime()));
		r.setStatus(STATUS.NODE.value);
		r.setDescription(description);
		r.setScenarioName("");

		// System.out.println("List Size "+customTestList.size()+" for Thread:
		// "+Thread.currentThread().getId());
		getCurrentThreadTestList_FromMap().add(r);
		ExtentManager.createNode(description);
	}

	private static void manageAssertions(String Constan, String description) {
		if (FrameworkConfig.ENABLE_ASSERTIONS) {
			if (Constan.equalsIgnoreCase(STATUS.FAIL.value) || Constan.equalsIgnoreCase(STATUS.FATAL.value)
					|| Constan.equalsIgnoreCase(STATUS.ERROR.value)) {
				Assert.fail(description);
			}
		}
	}

	private static boolean deleteDirectory(File dir) {
		boolean bool = false;
		if (dir.isDirectory()) {
			File[] children = dir.listFiles();
			for (int i = 0; i < children.length; i++) {
				boolean success = deleteDirectory(children[i]);
				if (!success) {
					return false;
				}
			}
		}
		// either file or an empty directory
		if (dir.getName().equals(FrameworkConfig.HTML_REPORT_FOLDER_NAME)) {
			// System.out.println("Skipping file or directory : " + dir.getName());
			bool = true;
		} else {
			// System.out.println("Removing file or directory : " + dir.getName());
			bool = dir.delete();
		}
		return bool;
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

	/**
	 * Converts the passed millisecond value to hh:mm:ss.sss format, you need to
	 * pass the difference of two time stamps only
	 */
	private static String timeConversion(long milliseconds) {
		int seconds = (int) milliseconds / 1000;
		double sss = (double) milliseconds / 1000 - seconds;

		String sssInString = ".000";
		try {
			sssInString = (sss + "").substring(1, 5);
		} catch (Exception e) {
		}

		final int MINUTES_IN_AN_HOUR = 60;
		final int SECONDS_IN_A_MINUTE = 60;
		int minutes = (int) (seconds / SECONDS_IN_A_MINUTE);
		seconds -= minutes * SECONDS_IN_A_MINUTE;
		int hours = minutes / MINUTES_IN_AN_HOUR;
		minutes -= hours * MINUTES_IN_AN_HOUR;
		return prefixZeroToDigit(hours) + ":" + prefixZeroToDigit(minutes) + ":" + prefixZeroToDigit((int) seconds)
				+ sssInString;
	}

	/** private method to be used by {@link timeConversion} method */
	private static String prefixZeroToDigit(int num) {
		int number = num;
		if (number <= 9) {
			String sNumber = "0" + number;
			return sNumber;
		} else
			return "" + number;
	}

	/**
	 * This method will delete all the contents of the passed directory, but will
	 * skip the folder which is passed in skipDirectory variable
	 */
	static boolean deleteFolderContentRecursively(File dir, String skipDirectory) {
		boolean bool = false;
		try {
			if (dir.isDirectory()) {
				File[] children = dir.listFiles();
				for (int i = 0; i < children.length; i++) {
					boolean success = deleteFolderContentRecursively(children[i], skipDirectory);
					if (!success) {
						return false;
					}
				}
			}
			// either file or an empty directory
			if (skipDirectory != null && dir.getName().equals(skipDirectory)) {
				// System.out.println("Skipping file or directory : " +
				// dir.getName());
				bool = true;
			} else {
				// System.out.println("Removing file or directory : " +
				// dir.getName());
				bool = dir.delete();
			}
		} catch (Exception e) {
			new Custom_ExceptionHandler(e, "Passed Data, dir: '" + dir + "' skipDirectory: '" + skipDirectory + "'");
		}
		return bool;
	}

}
