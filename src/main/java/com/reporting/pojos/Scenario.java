package com.reporting.pojos;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.appium.driver.AppiumDriverFactory;
import com.selenium.Custom_ExceptionHandler;
import com.selenium.webdriver.DriverFactory;

public class Scenario {

	private List<Scenario> testList;

	private static String suiteName;
	private String testName;
	private String platform;
	private String deviceName_Mobile;
	private String appType;
	private String environment;
	private String className;
	private String methodName;
	private int methodId;
	private String priority;
	private String group;
	private String dependsOn;
	private String parameters = "";

	private String status;

	private String srNo;
	private String scenarioName;
	private String description;
	private static String inParallel;
	private static String threadCount;
	private String snapshotURL;
	private String timeStamp;
	private long startTime_Scenario;
	private long endTime_Scenario;
	private String executionTime_Scenario;
	private String browserName;
	private String os_Desktop;
	private String browser_version;
	private String osVersion;

	private String locale;
	private static String runMode;
	private static long testExecutionStartDate = 0l;
	private static long testExecutionEndDate = 0l;
	private static String testExecutionTime;
	private static long totalScenario = 0;
	private static long passedScenario = 0;
	private static long failedScenario = 0;
	private static long skippedScenario = 0;
	private static long errorScenario = 0;
	private static long warningScenario = 0;
	private static long fatalScenario = 0;

	private static double passingPercent = 0;
	private static double failurePercent = 0;
	private static double skipPercent = 0;

	private static long totalStep = 0;
	private static long passedStep = 0;
	private static long failedStep = 0;
	private static long skippedStep = 0;
	private static long errorStep = 0;
	private static long warningStep = 0;
	private static long fatalStep = 0;

	// MOBILE + APP + APPIUM PARAMETERS
	private String app_desired_capabilities;

	public String toString() {
		return "{" + scenarioName + ", " + description + ", " + status + ", " + testList + "}\n";
	}

	public List<Scenario> getList() {
		if (testList == null) {
			testList = new ArrayList<Scenario>();
		}
		return testList;
	}

	public String getEnvironment() {
		return environment;
	}

	public void setEnvironment(String environment) {
		this.environment = environment;
	}

	public String getGroup() {
		return group;
	}

	public void setGroup(String group) {
		this.group = group;
	}

	public String getDependsOn() {
		return dependsOn;
	}

	public void setDependsOn(String dependsOn) {
		this.dependsOn = dependsOn;
	}

	public String getParameters() {
		return parameters;
	}

	public void setParameters(String parameters) {
		this.parameters = parameters;
	}

	public String getSrNo() {
		if (srNo == null) {
			return "";
		}
		return srNo;
	}

	public void setSrNo(String srNo) {
		this.srNo = srNo;
	}

	public String getTestNG_MethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public int getTestNG_MethodId() {
		return methodId;
	}

	public void setMethodId(int methodId) {
		this.methodId = methodId;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public static String getTestNG_SuiteName() {
		return suiteName;
	}

	public static void setTestNG_SuiteName(String suiteName) {
		Scenario.suiteName = suiteName;
	}

	public String getTestNG_TestName() {
		return testName;
	}

	public void setTestNG_TestName(String testName) {
		this.testName = testName;
	}

	public String getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(String timeStamp) {
		this.timeStamp = timeStamp;
	}

	public static long getTotalStep() {
		return totalStep;
	}

	public static void setTotalStep(long totalStep) {
		Scenario.totalStep = totalStep;
	}

	public static long getPassedStep() {
		return passedStep;
	}

	public static void setPassedStep(long passedStep) {
		Scenario.passedStep = passedStep;
	}

	public static long getFailedStep() {
		return failedStep;
	}

	public static void setFailedStep(long failedStep) {
		Scenario.failedStep = failedStep;
	}

	public static long getSkippedStep() {
		return skippedStep;
	}

	public static void setSkippedStep(long skippedStep) {
		Scenario.skippedStep = skippedStep;
	}

	public static long getErrorStep() {
		return errorStep;
	}

	public static void setErrorStep(long errorStep) {
		Scenario.errorStep = errorStep;
	}

	public static long getWarningStep() {
		return warningStep;
	}

	public static void setWarningStep(long warningStep) {
		Scenario.warningStep = warningStep;
	}

	public static long getFatalStep() {
		return fatalStep;
	}

	public static void setFatalStep(long fatalStep) {
		Scenario.fatalStep = fatalStep;
	}

	public static long getErrorScenario() {
		return errorScenario;
	}

	public static void setErrorScenario(long errorScenario) {
		Scenario.errorScenario = errorScenario;
	}

	public static long getWarningScenario() {
		return warningScenario;
	}

	public static void setWarningScenario(long warningScenario) {
		Scenario.warningScenario = warningScenario;
	}

	public static long getFatalScenario() {
		return fatalScenario;
	}

	public static void setFatalScenario(long fatalScenario) {
		Scenario.fatalScenario = fatalScenario;
	}

	public static double getPassingPercent() {
		return passingPercent;
	}

	public static void setPassingPercent(double passingPercent) {
		Scenario.passingPercent = passingPercent;
	}

	public static double getFailurePercent() {
		return failurePercent;
	}

	public static void setFailurePercent(double failurePercent) {
		Scenario.failurePercent = failurePercent;
	}

	public static double getSkipPercent() {
		return skipPercent;
	}

	public static void setSkipPercent(double skipPercent) {
		Scenario.skipPercent = skipPercent;
	}

	public long getEndTime_Scenario_InMillSec() {
		return endTime_Scenario;
	}

	public String getEndTime_Scenario_InHHMMSS() {
		return convertToHHMMSS(endTime_Scenario);
	}

	public void setEndTime_Scenario(long endTime) {
		this.endTime_Scenario = endTime;
	}

	public String getSnapshotURL() {
		if (snapshotURL == null) {
			return "";
		}
		return snapshotURL;
	}

	public void setSnapshotURL(String snapshotURL) {
		this.snapshotURL = snapshotURL;
	}

	public static String getTestExecutionTime() {

		return testExecutionTime;
	}

	public static void setTestExecutionTime(String testExecutionTime) {
		Scenario.testExecutionTime = testExecutionTime;
	}

	public static String getInParallel() {
		return inParallel;
	}

	public static void setInParallel(String inParallel) {
		Scenario.inParallel = inParallel;
	}

	public String getStatus() {
		return status;
	}

	public String getScenarioName() {
		return scenarioName;
	}

	public String getDescription() {
		return description;
	}

	public long getStartTime_Scenario_InMillSec() {
		return startTime_Scenario;
	}

	public String getStartTime_Scenario_InHHMMSS() {
		return convertToHHMMSS(startTime_Scenario);
	}

	public static long getTestExecutionStartDate() {
		return testExecutionStartDate;
	}

	public static long getTestExecutionEndDate() {
		return testExecutionEndDate;
	}

	public static long getTotalScenario() {
		return totalScenario;
	}

	public static long getPassedScenario() {
		return passedScenario;
	}

	public static long getFailedScenario() {
		return failedScenario;
	}

	public static long getSkippedScenario() {
		return skippedScenario;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public void setScenarioName(String scenarioName) {
		this.scenarioName = scenarioName;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setStartTime_Scenario(long l) {
		this.startTime_Scenario = l;
	}

	public static void setTestExecutionStartDate(long l) {
		if (Scenario.testExecutionStartDate == 0l) {
			Scenario.testExecutionStartDate = l;
		}
	}

	public static void setTestExecutionEndDate(long l) {
		Scenario.testExecutionEndDate = l;
	}

	public void setBrowserVersion(String browser_version) {
		if (browser_version == null || browser_version.trim().equals("")
				|| browser_version.trim().toLowerCase().equals("latest")) {
			this.browser_version = DriverFactory.getBrowserVersion();
		} else {
			this.browser_version = browser_version;
		}
	}

	public String getBrowserVersion() {
		if (browser_version == null || browser_version.trim().equals("")) {
			return "";
		}
		return browser_version;

	}

	public String getOsVersion() {
		return osVersion;
	}

	public void setOsVersion(String osVersion) {
		if (osVersion == null || osVersion.trim().equals("")) {
			this.osVersion = DriverFactory.getOsVersion();
		} else {
			this.osVersion = osVersion;
		}
	}

	public void setBrowserName(String browser) {
		this.browserName = DriverFactory.getBrowserName();
		// this.browser = browser;
	}

	public String getBrowserName() {
		return browserName;
	}

	public void setOs_Desktop(String os_Desktop) {
		this.os_Desktop = DriverFactory.getOsName();
		// this.platform = platform;
	}

	public String getOs_Desktop() {
		return os_Desktop;
	}

	public static void setTotalScenario(long totalScenario) {
		Scenario.totalScenario = totalScenario;
	}

	public static void setPassedScenario(long passedScenario) {
		Scenario.passedScenario = passedScenario;
	}

	public static void setFailedScenario(long failedScenario) {
		Scenario.failedScenario = failedScenario;
	}

	public static void setSkippedScenario(long skippedScenario) {
		Scenario.skippedScenario = skippedScenario;
	}

	public String getExecutionTime_Scenario() {
		if (executionTime_Scenario == null) {
			return "";
		}
		return executionTime_Scenario;
	}

	public void setExecutionTime_Scenario(String executionTime_Scenario) {
		this.executionTime_Scenario = executionTime_Scenario;
	}

	public String getTestNG_Priority() {
		return priority;
	}

	public void setPriority(String priority) {
		this.priority = priority;
	}

	/**
	 * @return
	 */
	public static void setThreadCount(String threadCount) {
		Scenario.threadCount = threadCount;
	}

	public static String getThreadCount() {
		if (threadCount == null)
			return "";
		return threadCount;
	}

	public String getLocale() {
		return locale;
	}

	public void setLocale(String locale) {
		this.locale = locale;
	}

	public static void setRunMode(String remoteUrl) {

		if (remoteUrl == null || "".equals(remoteUrl)) {
			Scenario.runMode = "LOCAL";
		} else {
			Scenario.runMode = "GRID";
		}
	}

	public static String getRunMode() {
		return runMode;
	}

	public void setPlatform(String platform) {
		if (platform == null || "".equals(platform)) {
			this.platform = "Desktop";
		} else {
			this.platform = platform;
		}
	}

	public String getPlatform() {
		return platform;
	}

	public String getAppium_desired_capabilities() {
		return app_desired_capabilities;
	}

	public void setAppium_desired_capabilities(String app_desired_capabilities) {
		if (app_desired_capabilities == null || "".equals(app_desired_capabilities)) {
			String temp = AppiumDriverFactory.getDesiredCapabilities();
			if (temp == null) {
				this.app_desired_capabilities = "";
			} else {
				this.app_desired_capabilities = temp;
			}
		} else {
			this.app_desired_capabilities = app_desired_capabilities;
		}
	}

	public void setDeviceName_Mobile(String deviceName_Mobile) {
		if (deviceName_Mobile == null || "".equals(deviceName_Mobile)) {
			String temp = DriverFactory.getDeviceName_Mobile();
			String temp1 = AppiumDriverFactory.getDeviceName_Mobile();
			if (temp != null && !"".equals(temp)) {
				this.deviceName_Mobile = temp;
			}else if(!"".equals(temp1)) {
				this.deviceName_Mobile = temp1;
			}else {
				this.deviceName_Mobile = "";
			}
		} else {
			this.deviceName_Mobile = deviceName_Mobile;
		}
	}

	public String getDeviceName_Mobile() {
		return deviceName_Mobile;
	}

	public void setAppType(String appType) {
		if (appType == null || "".equals(appType)) {
			this.appType = "Web";
		} else {
			this.appType = appType;
		}
	}

	public String getAppType() {
		return appType;
	}

	/** Converts the passed millisecond value to hh:mm:ss.sss format */
	private String convertToHHMMSS(long millis) {
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

}