/**
 * CustomListener_HMap.java
 * All public methods of this class will be called by TestNG framework
 * Their calling order is explained in comments 
 * */
package com.listener;

import java.io.File;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;

import org.testng.IExecutionListener;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.appium.driver.AppiumDriverFactory;
import com.config.Config;
import com.config.FrameworkConfig;
import com.mail.MailManager;
import com.reporting.Reporter;
import com.reporting.ReportingHistoryManager_Html;
import com.reporting.pojos.Scenario;
import com.reporting.snapshot.SnapshotManager;
import com.selenium.Custom_ExceptionHandler;
import com.selenium.webdriver.DriverFactory;
import com.util.Util;

public class Custom_TestNGListener implements ITestListener,IExecutionListener{

	private String parallelFlag;
	private String suiteName;
	private String testName ;
	
	/**
	 * <pre>
	 * Calling order of this method is : 1.
	 * When the Execution is just started, i.e. In terms of testng.xml file, 
	 * when jvm hits <b>"suite"</b> tag this method will be triggered.
	 * <b>Initializes</b> the SnapshotManager, CustomReporter classes, and remove
	 * the older execution files
	 * </pre>
	 */
	public void onExecutionStart() {
		//System.out.println("onExecutionStart for Thread: "+Thread.currentThread().getId());
		deleteFolderContentRecursively(new File(FrameworkConfig.getDownloadsPath()),FrameworkConfig.DOWNLOADS_FOLDER_NAME);
		SnapshotManager.initialize();
	}

	/**
	 * <pre>
	 * Calling order of this method is : 2.
	 * When jvm hits <b>"test"</b> tag in testng.xml file, this method 
	 * will be triggered,
	 * <b>Instantiates</b> the class variables/objects parallelFlag, suiteName, 
	 * testName and extentReport.
	 * <b>Sets</b> the environment value to the Constant class variable, 
	 * for further use in framework(mainly to access login credentials 
	 * from test data)  
	 * <b>Setups</b> the DriverFactory and SnapshotManager classes   
	 * </pre> 
	 */
	public void onStart(ITestContext context) {
		// System.out.println("bro onStart "+Thread.currentThread().getId());
		HashMap<TestNGKeys, String> testDataMap = INIT_TEST_DATA_MAP(context);
		
		parallelFlag = testDataMap.get(TestNGKeys.parallel);
		suiteName = testDataMap.get(TestNGKeys.suite);
		testName = testDataMap.get(TestNGKeys.test);
		Config.setEnvironmentInfoSheet(testDataMap.get(TestNGKeys.environment));

		Reporter.onStart(testDataMap);

		if (parallelFlag.equals("none") || parallelFlag.equals("tests")) {
			DriverFactory.setUp(testDataMap);
			SnapshotManager.setUp(context.getName());
		}
		
		Scenario.setTotalScenario(context.getSuite().getAllMethods().size());
	}

	/**
	 * <pre>
	 * Calling order of this method is : 3.
	 * When jvm hits <b>"include"</b> tag in testng.xml file, this method 
	 * will be triggered,
	 * <b>Initilizes</b> the testData hash map
	 * <b>Setups</b> the DriverFactory and SnapshotManager classes
	 * Performs login to opened app based on environment and user   
	 * </pre> 
	 */
	public void onTestStart(ITestResult result) {
		
		//Reporter.WARNING("Listener onTestStart");
		
		//System.out.println("onTestStart "+Thread.currentThread().getId());
		//System.out.println("Started: "+Arrays.toString(result.getMethod().getMethodsDependedUpon()));
		
		HashMap<TestNGKeys,String> testDataMap=INIT_TEST_DATA_MAP(result);
		
		Config.setLocale(testDataMap.get(TestNGKeys.locale));
		
		Reporter.createTest(testDataMap);
		
		if(!parallelFlag.equals("none") && !parallelFlag.equals("tests")){
			DriverFactory.setUp(testDataMap);
			SnapshotManager.setUp(result.getName()+"_"+Util.getTimeStamp_InMilliSec());
		}else{
			SnapshotManager.setRunningMethodName(result.getName());
		}
		
		AppiumDriverFactory.startAppiumServer(testDataMap);
		AppiumDriverFactory.startAppiumDriver(testDataMap);
	}
	

	/**
	 * <pre>
	 * Calling order of this method is also : 3.
	 * When jvm hits <b>"include"</b> tag in testng.xml file, this method 
	 * will be triggered, The main difference here is, when a method is 
	 * failed all the methods which are marked as dependent will be skipped 
	 * <b>Initilizes</b> the testData hash map
	 * <b>Adds</b> the appropriate message to the report
	 * <b>Finishes off</b> the CustomReporter Test > [Steps] cycle, by passing null as we don't have any snapshots to create a movie
	 * </pre> 
	 */
	public void onTestSkipped(ITestResult result) {
		//System.out.println("onTestSkip "+Thread.currentThread().getId());
		INIT_TEST_DATA_MAP(result);
		Reporter.SKIP("It depends on methods which got failed: '"+(result.getMethod().getMethodsDependedUpon().length>0?Arrays.toString(result.getMethod().getMethodsDependedUpon()):null)+"'");
		Reporter.onExecutionFinish();
	}

	/**
	 * <pre>
	 * Calling order of this method is : 3.5
	 * When jvm completely finishes <b>"include"</b> tag in testng.xml file, this method 
	 * will be triggered,
	 * Performs the logout based on environment
	 * <b>Creates</b> snapshot movie
	 * <b>Finishes off</b> the CustomReporter Test > [Steps] cycle, by passing the snapshot movie path
	 * <b>Tears down</b> the DriverFactory, SnapshotManager classes
	 * </pre> 
	 */
	public void onTestSuccess(ITestResult result){
		//System.out.println("onTestSuccess "+Thread.currentThread().getId());
		
		//HashMap<TestNGKeys,String> testDataMap=INIT_TEST_DATA_MAP(result);
	
		Reporter.onExecutionFinish();
		
		if(!parallelFlag.equals("none") && !parallelFlag.equals("tests")){
			DriverFactory.tearDown();
			SnapshotManager.tearDown();
		}
		
		AppiumDriverFactory.stopAppiumDriver();
		AppiumDriverFactory.stopAppiumServer();
	}

	/**
	 * <pre>
	 * Calling order of this method is also : 3.5.
	 * When jvm encounter any unhandeled exceptions during the execution of 
	 * <b>"include"</b> tag in testng.xml file, The test will be considered 
	 * as failure and this method will be triggered,
	 * 
	 * ERROR line will be added with stack trace in the html report, also
	 * the same stack trace can be seen in 
	 * [TestNG Results of running suite]>>[Failure Exception] section
	 * 
	 * Performs the logout based on environment
	 * <b>Creates</b> snapshot movie
	 * <b>Finishes off</b> the CustomReporter Test > [Steps] cycle, by passing the snapshot movie path
	 * <b>Tears down</b> the DriverFactory, SnapshotManager classes
	 * </pre> 
	 */
	public void onTestFailure(ITestResult result) {

		//System.out.println("onTestFailure "+Thread.currentThread().getId());
		//HashMap<TestNGKeys,String> testDataMap=INIT_TEST_DATA_MAP(result);
		
		result.getThrowable().printStackTrace();
		String readableStackTrace=result.getThrowable().getMessage();
		for (StackTraceElement elem : result.getThrowable().getStackTrace()) {
			readableStackTrace=readableStackTrace+"<br/>"+elem.toString();
		}
		Reporter.ERROR( "- PREMATURE EXECUTION STOPPED : Failure Exception :- "+readableStackTrace+"");

		Reporter.onExecutionFinish();
		
		if(!parallelFlag.equals("none") && !parallelFlag.equals("tests")){
			DriverFactory.tearDown();
			SnapshotManager.tearDown();
		}
		
		AppiumDriverFactory.stopAppiumDriver();
		AppiumDriverFactory.stopAppiumServer();
		
	}
	

	/**
	 * <pre>
	 * Calling order of this method is : 4.
	 * When jvm finishes the <b>"test"</b> tag in testng.xml file, this method 
	 * will be triggered,
	 * <b>Tear down</b> the DriverFactory and SnapshotManager classes   
	 * </pre> 
	 * */
	public void onFinish(ITestContext context) {
		//System.out.println("onFinish "+Thread.currentThread().getId());
		
		if(parallelFlag.equals("none") || parallelFlag.equals("tests")){
			DriverFactory.tearDown();
			SnapshotManager.tearDown();
		}
		
	}

	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
	}
	
	/**
	 * <pre>
	 * Calling order of this method is : 5.
	 * When jvm finishes the <b>"suite"</b> tag in testng.xml file, this method 
	 * will be triggered,
	 * <b>Generates</b> the Custom and Extent HTML Report, Automation Coverage Matrix report 
	 * <b>Manages</b> Test Run History, Reporting History
	 * <b>Sends</b> the Notification mail attaching the html reports   
	 * </pre> 
	 * */
	public void onExecutionFinish() {
		//TestRunHistoryManager_Excel.manageTestRunHistoryExcel();
		ReportingHistoryManager_Html.maintainReportingHistory();
		MailManager.sendNotificationMail("Suite["+suiteName+"] Test["+testName+"] Env["+Config.getEnvironment()+"] "+new Date());
		ReportingHistoryManager_Html.printReportPathInConsole();
	}

	/**
	 * Initializes the testDataMap Hashmap from context object
	 * When jvm hits <b>"test"</b> tag in testng.xml file, this method 
	 * will be triggered,
	 * */
	private HashMap<TestNGKeys, String> INIT_TEST_DATA_MAP(ITestContext context){
		HashMap<TestNGKeys,String> testDataMap= new HashMap<>();

		testDataMap.put(TestNGKeys.JSON_FILE_NAME, context.getCurrentXmlTest().getParameter(TestNGKeys.JSON_FILE_NAME.value));
		testDataMap.put(TestNGKeys.JSON_OBJECT_NAME, context.getCurrentXmlTest().getParameter(TestNGKeys.JSON_OBJECT_NAME.value));
		
		testDataMap.put(TestNGKeys.browserName, context.getCurrentXmlTest().getParameter(TestNGKeys.browserName.value));
		testDataMap.put(TestNGKeys.browserVersion, context.getCurrentXmlTest().getParameter(TestNGKeys.browserVersion.value));
		testDataMap.put(TestNGKeys.platform_Desktop_Mobile, context.getCurrentXmlTest().getParameter(TestNGKeys.platform_Desktop_Mobile.value));
		testDataMap.put(TestNGKeys.os_Desktop, context.getCurrentXmlTest().getParameter(TestNGKeys.os_Desktop.value));
		testDataMap.put(TestNGKeys.deviceName_Mobile, context.getCurrentXmlTest().getParameter(TestNGKeys.deviceName_Mobile.value));
		testDataMap.put(TestNGKeys.osVersion, context.getCurrentXmlTest().getParameter(TestNGKeys.osVersion.value));
		testDataMap.put(TestNGKeys.remoteURL, context.getCurrentXmlTest().getParameter(TestNGKeys.remoteURL.value));
		testDataMap.put(TestNGKeys.environment, context.getCurrentXmlTest().getParameter(TestNGKeys.environment.value));
		testDataMap.put(TestNGKeys.appType_Web_App, context.getCurrentXmlTest().getParameter(TestNGKeys.appType_Web_App.value));
		testDataMap.put(TestNGKeys.locale, context.getCurrentXmlTest().getParameter(TestNGKeys.locale.value));
		
		testDataMap.put(TestNGKeys.suite, context.getSuite().getName());
		testDataMap.put(TestNGKeys.test, context.getCurrentXmlTest().getName());
		testDataMap.put(TestNGKeys.threadCount, context.getCurrentXmlTest().getThreadCount()+""); 
		testDataMap.put(TestNGKeys.parallel, context.getSuite().getParallel());
		return testDataMap;
	}
	
	/**
	 * Initializes the testDataMap Hashmap from result object
	 * When jvm hits <b>"include"</b> tag in testng.xml file, this method 
	 * will be triggered
	 * */
	private HashMap<TestNGKeys,String> INIT_TEST_DATA_MAP(ITestResult result){
		HashMap<TestNGKeys,String> testDataMap= new HashMap<>();
		
		testDataMap.put(TestNGKeys.JSON_FILE_NAME, result.getMethod().findMethodParameters(result.getTestContext().getCurrentXmlTest()).get(TestNGKeys.JSON_FILE_NAME.value));
		testDataMap.put(TestNGKeys.JSON_OBJECT_NAME, result.getMethod().findMethodParameters(result.getTestContext().getCurrentXmlTest()).get(TestNGKeys.JSON_OBJECT_NAME.value));
		
		testDataMap.put(TestNGKeys.remoteURL, result.getMethod().findMethodParameters(result.getTestContext().getCurrentXmlTest()).get(TestNGKeys.remoteURL.value));
		testDataMap.put(TestNGKeys.browserVersion, result.getMethod().findMethodParameters(result.getTestContext().getCurrentXmlTest()).get(TestNGKeys.browserVersion.value));
		testDataMap.put(TestNGKeys.platform_Desktop_Mobile, result.getMethod().findMethodParameters(result.getTestContext().getCurrentXmlTest()).get(TestNGKeys.platform_Desktop_Mobile.value));
		testDataMap.put(TestNGKeys.os_Desktop, result.getMethod().findMethodParameters(result.getTestContext().getCurrentXmlTest()).get(TestNGKeys.os_Desktop.value));
		testDataMap.put(TestNGKeys.deviceName_Mobile, result.getMethod().findMethodParameters(result.getTestContext().getCurrentXmlTest()).get(TestNGKeys.deviceName_Mobile.value));
		testDataMap.put(TestNGKeys.osVersion, result.getMethod().findMethodParameters(result.getTestContext().getCurrentXmlTest()).get(TestNGKeys.osVersion.value));
		testDataMap.put(TestNGKeys.browserName, result.getMethod().findMethodParameters(result.getTestContext().getCurrentXmlTest()).get(TestNGKeys.browserName.value));
		testDataMap.put(TestNGKeys.appType_Web_App, result.getMethod().findMethodParameters(result.getTestContext().getCurrentXmlTest()).get(TestNGKeys.appType_Web_App.value));
		testDataMap.put(TestNGKeys.locale, result.getMethod().findMethodParameters(result.getTestContext().getCurrentXmlTest()).get(TestNGKeys.locale.value));
		testDataMap.put(TestNGKeys.environment, result.getMethod().findMethodParameters(result.getTestContext().getCurrentXmlTest()).get(TestNGKeys.environment.value));
		
		testDataMap.put(TestNGKeys.parameters, Arrays.toString(result.getParameters()));
		testDataMap.put(TestNGKeys.test, result.getMethod().getXmlTest().getName());
		testDataMap.put(TestNGKeys.className, result.getMethod().getRealClass().getName());
		testDataMap.put(TestNGKeys.methodName, result.getMethod().getMethodName());
		testDataMap.put(TestNGKeys.priority, result.getMethod().getPriority()+"");
		//testDataMap.put(TestNGKeys.remoteURL, result.getTestContext().getCurrentXmlTest().getAllParameters().get(TestNGKeys.remoteURL.value));
		testDataMap.put(TestNGKeys.group,Arrays.toString(result.getMethod().getGroups()));
		testDataMap.put(TestNGKeys.dependsOn,Arrays.toString(result.getMethod().getMethodsDependedUpon()));
		
		// In case you forgot to provide description to a test method, then
		// method name will be shown in HTML report instead of {null}
		if (result.getMethod().getDescription()==null || result.getMethod().getDescription().trim().equals("")) {
			testDataMap.put(TestNGKeys.description, result.getMethod().getMethodName());	
		}else{
			testDataMap.put(TestNGKeys.description, result.getMethod().getDescription());
		}
		
		return testDataMap;
	}

	/**
	 * This method will delete all the contents of the passed directory, but
	 * will skip the folder which is passed in skipDirectory variable
	 */
	private static boolean deleteFolderContentRecursively(File dir, String skipDirectory) {
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
