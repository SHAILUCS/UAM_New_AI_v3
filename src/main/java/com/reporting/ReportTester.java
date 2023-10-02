package com.reporting;

import java.io.IOException;
import java.util.HashMap;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.listener.TestNGKeys;

public class ReportTester {
	public static void main(String[] args) throws IOException {
		extentAndCustomReportTester();
		//extentPractice();
	} 
	
	public static void extentPractice() {
		
		// FIRST PART - INIT -> SET REPORT NAME AND PATH
		ExtentReports extent = new ExtentReports();
		ExtentSparkReporter spark = new ExtentSparkReporter("Report/Spark.html");
		extent.attachReporter(spark);
		
		// SECOND PART - ON TEST START -> PASS TEST NAME
		ExtentTest test = extent.createTest("MyFirstTest");
		
		// THIRD PART - ON TEST START -> PASS TEST NAME
		test.log(Status.PASS, "This is a logging event for MyFirstTest, and it passed!");
		test.getModel().setDescription("<a href='https://www.google.com' target='_blank'>GOOGLE</a>");
		
		// FOURTH PART - TEAR DOWN -> GENERATE REPORT
		extent.flush();

	}
	
	protected static void extentAndCustomReportTester(){
		
		//START
		//INITIALIZE THE REPORTER
		HashMap<TestNGKeys,String> testDataMap= new HashMap<>();
		String parallelFlag="Methods";
		testDataMap.put(TestNGKeys.parallel, parallelFlag);
		testDataMap.put(TestNGKeys.browserName, "FireFox");
		testDataMap.put(TestNGKeys.platform_Desktop_Mobile, "Platform");
		testDataMap.put(TestNGKeys.remoteURL, "");
		testDataMap.put(TestNGKeys.environment, "Preprodd");
		testDataMap.put(TestNGKeys.suite, "SuiteName");
		testDataMap.put(TestNGKeys.test, "UI_TEST");
		testDataMap.put(TestNGKeys.className, "test.Class");

		Reporter.onStart(testDataMap);

		//TODO
		testDataMap.put(TestNGKeys.description, "Test1");
		testDataMap.put(TestNGKeys.methodName, "UI_Rep_TotalAndPremiumNumberVolumeAndTAPCharges_634");
		testDataMap.put(TestNGKeys.priority, "2");
		Reporter.createTest(testDataMap);
		//ExtentManager.createTest(testDataMap);
		
		//object repository


		Reporter.FATAL("TESTING STATUSES");
		Reporter.ERROR("TESTING STATUSES");
		Reporter.FAIL("TESTING STATUSES");
		Reporter.PASS_ExitNode("HELLO EXISTING THE NODE");
		Reporter.NODE("Node0.0");
		Reporter.PASS_ExitNode("HELLO EXISTING THE NODE");
		Reporter.NODE("Node1.0");
		Reporter.INFO("Test1.1");
		Reporter.NODE("Node1.1");
		
		Reporter.PASS("Test1.1");
		Reporter.PASS("Test1.2");
		Reporter.PASS("Test1.3");
		
		Reporter.NODE("Node2.0");
		Reporter.PASS("Test2.1");
		Reporter.PASS("Test2.2");
		Reporter.WARNING("Test2.3");
		
		Reporter.WARNING("Test2.3");
		//com.wait(1);
		
		//CustomListener
		//Reporter.endTest();
		

		//TODO
		testDataMap.put(TestNGKeys.description, "discAgr_T09_Verification of 'Forecasting Method and Home Currency' and 'Client and Partner Currency' data from NGC - 'Maintain IOT client, Edit and Update/IOT Approve Addendum' pages with AUSTA - 'Create Agreement' page");
		testDataMap.put(TestNGKeys.methodName, "Test3123123123");
		testDataMap.put(TestNGKeys.priority, "1");
		Reporter.createTest(testDataMap);
		//ExtentManager.createTest(testDataMap);
		Reporter.SKIP("It depends on methods which got failed");
		//com.wait(3);
		//Reporter.endTest();
		
		
		

		//TODO
		testDataMap.put(TestNGKeys.description, "discAgr_T09_Verification of 'Forecasting Method and Home Currency' and 'Client and Partner Currency' data from NGC - 'Maintain IOT client, Edit and Update/IOT Approve Addendum' pages with AUSTA - 'Create Agreement' page");
		testDataMap.put(TestNGKeys.methodName, "Test3123123");
		testDataMap.put(TestNGKeys.priority, "1");
		Reporter.createTest(testDataMap);
		//ExtentManager.createTest(testDataMap);
		Reporter.SKIP("It depends on methods which got failed");
		//com.wait(3);
		//Reporter.endTest();

		

		//TODO
		testDataMap.put(TestNGKeys.description, "discAgr_T09_Verification of 'Forecasting Method and Home Currency' and 'Client and Partner Currency' data from NGC - 'Maintain IOT client, Edit and Update/IOT Approve Addendum' pages with AUSTA - 'Create Agreement' page");
		testDataMap.put(TestNGKeys.methodName, "Test3w54343");
		testDataMap.put(TestNGKeys.priority, "1");
		Reporter.createTest(testDataMap);
		//ExtentManager.createTest(testDataMap);
		Reporter.SKIP("It depends on methods which got failed");
		//com.wait(3);
		//Reporter.endTest();

		

		//TODO
		testDataMap.put(TestNGKeys.description, "discAgr_T09_Verification of 'Forecasting Method and Home Currency' and 'Client and Partner Currency' data from NGC - 'Maintain IOT client, Edit and Update/IOT Approve Addendum' pages with AUSTA - 'Create Agreement' page");
		testDataMap.put(TestNGKeys.methodName, "Test323321");
		testDataMap.put(TestNGKeys.priority, "1");
		Reporter.createTest(testDataMap);
		//ExtentManager.createTest(testDataMap);
		Reporter.SKIP("It depends on methods which got failed");
		//com.wait(3);
		//Reporter.endTest();

		
		

		//TODO
		testDataMap.put(TestNGKeys.description, "discAgr_T09_Verification of 'Forecasting Method and Home Currency' and 'Client and Partner Currency' data from NGC - 'Maintain IOT client, Edit and Update/IOT Approve Addendum' pages with AUSTA - 'Create Agreement' page");
		testDataMap.put(TestNGKeys.methodName, "Test3fsdf");
		testDataMap.put(TestNGKeys.priority, "1");
		Reporter.createTest(testDataMap);
		//ExtentManager.createTest(testDataMap);
		Reporter.FAIL("It depends on methods which got failed");
		//com.wait(3);
		//Reporter.endTest();

		

		//TODO
		testDataMap.put(TestNGKeys.description, "discAgr_T09_Verification of 'Forecasting Method and Home Currency' and 'Client and Partner Currency' data from NGC - 'Maintain IOT client, Edit and Update/IOT Approve Addendum' pages with AUSTA - 'Create Agreement' page");
		testDataMap.put(TestNGKeys.methodName, "Test3sdf");
		testDataMap.put(TestNGKeys.priority, "1");
		Reporter.createTest(testDataMap);
		//ExtentManager.createTest(testDataMap);
		Reporter.PASS("It depends on methods which got failed");
		//com.wait(3);
		//Reporter.endTest();

		
		
		

		//TODO
		testDataMap.put(TestNGKeys.description, "7233 - Forecast Operator Level Growth - Enable bulk update for all services and event types (FE)");
		testDataMap.put(TestNGKeys.methodName, "ForecastOperatorLevelGrowthEnableBulkUpdateForAllServicesAndEventTypesFE_7233");
		testDataMap.put(TestNGKeys.priority, "8");
		testDataMap.put(TestNGKeys.className, "tests.MainRegression.KPN_DealTracker7216");
		Reporter.createTest(testDataMap);
		//ExtentManager.createTest(testDataMap);
		Reporter.INFO("'Login Page' page is displayed http://10.184.40.120/pls/apex/f?p=10132:22::::::");
		Reporter.FAIL("'SHAILENDRA.RAJAWAT' got displayed on NGC Home Page");
		Reporter.SKIP("Navigation complete to_IOTRONHomePage http://10.184.40.120/pls/apex/f?p=10145:252:1838571049922::NO:::");
		Reporter.SKIP("<b style='font-size: small;font-family: monospace;'>Deal Tracker Module -&gt; Deal Tracker Summary</b><br>'<span style='color: black;font-style: italic;font-weight: bold;'>Deal Tracker</span>' page is displayed <br>http://10.184.40.120/pls/apex/f?p=22244:4:1838571049922::NO:4:P4_DISPLAY_REPORTS,P4_IOT_AGREEMENT_ID:N,1&amp;cs=16CD4420D3C13A7D72F75069CC63E760F");
		Reporter.SKIP("'Edit Market Volume' page is displayed http://10.184.40.120/pls/apex/f?p=22244:5:1838571049922::NO:5:P5_REPORTING_YEAR,P5_REPORTING_YEAR_SHORT,P5_NEXT_YEAR_SHORT:2017,17,18&cs=1EA532116412FCB1C6E05D782B261D12B");
		Reporter.SKIP("'<b>Edit Proportional Share</b>' page is displayed <br>http://10.184.40.120/pls/apex/f?p=22244:10:1838571049922::NO:10:P10_REPORTING_YEAR,P10_REPORTING_YEAR_SHORT,P10_NEXT_YEAR_SHORT:2017,17,18&amp;cs=12BACDC029DEAA64A184A81DB5D5FAB4E");
		Reporter.SKIP("'Edit Market Volume' page is displayed http://10.184.40.120/pls/apex/f?p=22244:5:1838571049922::NO:5:P5_REPORTING_YEAR,P5_REPORTING_YEAR_SHORT,P5_NEXT_YEAR_SHORT:2017,17,18&cs=1EA532116412FCB1C6E05D782B261D12B");
		Reporter.SKIP("'Edit Market Volume' page is displayed http://10.184.40.120/pls/apex/f?p=22244:5:1838571049922::NO:5:P5_REPORTING_YEAR,P5_REPORTING_YEAR_SHORT,P5_NEXT_YEAR_SHORT:2017,17,18&cs=1EA532116412FCB1C6E05D782B261D12B");
		Reporter.SKIP("'Edit Market Volume' page is displayed http://10.184.40.120/pls/apex/f?p=22244:5:1838571049922::NO:5:P5_REPORTING_YEAR,P5_REPORTING_YEAR_SHORT,P5_NEXT_YEAR_SHORT:2017,17,18&cs=1EA532116412FCB1C6E05D782B261D12B");
		Reporter.SKIP("'Edit Market Volume' page is displayed http://10.184.40.120/pls/apex/f?p=22244:5:1838571049922::NO:5:P5_REPORTING_YEAR,P5_REPORTING_YEAR_SHORT,P5_NEXT_YEAR_SHORT:2017,17,18&cs=1EA532116412FCB1C6E05D782B261D12B");
		Reporter.SKIP("'Edit Market Volume' page is displayed http://10.184.40.120/pls/apex/f?p=22244:5:1838571049922::NO:5:P5_REPORTING_YEAR,P5_REPORTING_YEAR_SHORT,P5_NEXT_YEAR_SHORT:2017,17,18&cs=1EA532116412FCB1C6E05D782B261D12B");
		Reporter.SKIP("'Edit Market Volume' page is displayed http://10.184.40.120/pls/apex/f?p=22244:5:1838571049922::NO:5:P5_REPORTING_YEAR,P5_REPORTING_YEAR_SHORT,P5_NEXT_YEAR_SHORT:2017,17,18&cs=1EA532116412FCB1C6E05D782B261D12B");
		Reporter.SKIP("'Edit Market Volume' page is displayed http://10.184.40.120/pls/apex/f?p=22244:5:1838571049922::NO:5:P5_REPORTING_YEAR,P5_REPORTING_YEAR_SHORT,P5_NEXT_YEAR_SHORT:2017,17,18&cs=1EA532116412FCB1C6E05D782B261D12B");
		//com.wait(2);
		//Reporter.endTest();

		
		


		//TODO
		testDataMap.put(TestNGKeys.description, "discAgr_T09_Verification of 'Forecasting Method and Home Currency' and 'Client and Partner Currency' data from NGC - 'Maintain IOT client, Edit and Update/IOT Approve Addendum' pages with AUSTA - 'Create Agreement' page");
		testDataMap.put(TestNGKeys.methodName, "Test3");
		testDataMap.put(TestNGKeys.priority, "1");
		Reporter.createTest(testDataMap);
		//ExtentManager.createTest(testDataMap);
		Reporter.SKIP("It depends on methods which got failed");
		//com.wait(3);
		//Reporter.endTest();

		//TODO
		testDataMap.put(TestNGKeys.description, "discAgr_T09_Verification of 'Forecasting Method and Home Currency' and 'Client and Partner Currency' data from NGC - 'Maintain IOT client, Edit and Update/IOT Approve Addendum' pages with AUSTA - 'Create Agreement' page");
		testDataMap.put(TestNGKeys.methodName, "Test3");
		testDataMap.put(TestNGKeys.priority, "1");
		Reporter.createTest(testDataMap);
		//ExtentManager.createTest(testDataMap);
		Reporter.SKIP("It depends on methods which got failed");
		//com.wait(3);
		//Reporter.endTest();

		//TODO
		testDataMap.put(TestNGKeys.description, "discAgr_T09_Verification of 'Forecasting Method and Home Currency' and 'Client and Partner Currency' data from NGC - 'Maintain IOT client, Edit and Update/IOT Approve Addendum' pages with AUSTA - 'Create Agreement' page");
		testDataMap.put(TestNGKeys.methodName, "Test3");
		testDataMap.put(TestNGKeys.priority, "1");
		Reporter.createTest(testDataMap);
		//ExtentManager.createTest(testDataMap);
		Reporter.SKIP("It depends on methods which got failed");
		//com.wait(3);
		//Reporter.endTest();

		//TODO
		testDataMap.put(TestNGKeys.description, "discAgr_T09_Verification of 'Forecasting Method and Home Currency' and 'Client and Partner Currency' data from NGC - 'Maintain IOT client, Edit and Update/IOT Approve Addendum' pages with AUSTA - 'Create Agreement' page");
		testDataMap.put(TestNGKeys.methodName, "Test3");
		testDataMap.put(TestNGKeys.priority, "1");
		Reporter.createTest(testDataMap);
		//ExtentManager.createTest(testDataMap);
		Reporter.SKIP("It depends on methods which got failed");
		//com.wait(3);
		//Reporter.endTest();

		//TODO
		testDataMap.put(TestNGKeys.description, "discAgr_T09_Verification of 'Forecasting Method and Home Currency' and 'Client and Partner Currency' data from NGC - 'Maintain IOT client, Edit and Update/IOT Approve Addendum' pages with AUSTA - 'Create Agreement' page");
		testDataMap.put(TestNGKeys.methodName, "Test3");
		testDataMap.put(TestNGKeys.priority, "1");
		Reporter.createTest(testDataMap);
		//ExtentManager.createTest(testDataMap);
		Reporter.SKIP("It depends on methods which got failed");
		//com.wait(3);
		//Reporter.endTest();

		//TODO
		testDataMap.put(TestNGKeys.description, "discAgr_T09_Verification of 'Forecasting Method and Home Currency' and 'Client and Partner Currency' data from NGC - 'Maintain IOT client, Edit and Update/IOT Approve Addendum' pages with AUSTA - 'Create Agreement' page");
		testDataMap.put(TestNGKeys.methodName, "Test3");
		testDataMap.put(TestNGKeys.priority, "1");
		Reporter.createTest(testDataMap);
		//ExtentManager.createTest(testDataMap);
		Reporter.SKIP("It depends on methods which got failed");
		//com.wait(3);
		//Reporter.endTest();

		//TODO
		testDataMap.put(TestNGKeys.description, "discAgr_T09_Verification of 'Forecasting Method and Home Currency' and 'Client and Partner Currency' data from NGC - 'Maintain IOT client, Edit and Update/IOT Approve Addendum' pages with AUSTA - 'Create Agreement' page");
		testDataMap.put(TestNGKeys.methodName, "Test3");
		testDataMap.put(TestNGKeys.priority, "1");
		Reporter.createTest(testDataMap);
		//ExtentManager.createTest(testDataMap);
		Reporter.SKIP("It depends on methods which got failed");
		//com.wait(3);
		//Reporter.endTest();

		//TODO
		testDataMap.put(TestNGKeys.description, "discAgr_T09_Verification of 'Forecasting Method and Home Currency' and 'Client and Partner Currency' data from NGC - 'Maintain IOT client, Edit and Update/IOT Approve Addendum' pages with AUSTA - 'Create Agreement' page");
		testDataMap.put(TestNGKeys.methodName, "Test3");
		testDataMap.put(TestNGKeys.priority, "1");
		Reporter.createTest(testDataMap);
		//ExtentManager.createTest(testDataMap);
		Reporter.SKIP("It depends on methods which got failed");
		//com.wait(3);
		//Reporter.endTest();

		//TODO
		testDataMap.put(TestNGKeys.description, "discAgr_T09_Verification of 'Forecasting Method and Home Currency' and 'Client and Partner Currency' data from NGC - 'Maintain IOT client, Edit and Update/IOT Approve Addendum' pages with AUSTA - 'Create Agreement' page");
		testDataMap.put(TestNGKeys.methodName, "Test3");
		testDataMap.put(TestNGKeys.priority, "1");
		Reporter.createTest(testDataMap);
		//ExtentManager.createTest(testDataMap);
		Reporter.SKIP("It depends on methods which got failed");
		//com.wait(3);
		//Reporter.endTest();

		//TODO
		testDataMap.put(TestNGKeys.description, "discAgr_T09_Verification of 'Forecasting Method and Home Currency' and 'Client and Partner Currency' data from NGC - 'Maintain IOT client, Edit and Update/IOT Approve Addendum' pages with AUSTA - 'Create Agreement' page");
		testDataMap.put(TestNGKeys.methodName, "Test3");
		testDataMap.put(TestNGKeys.priority, "1");
		Reporter.createTest(testDataMap);
		//ExtentManager.createTest(testDataMap);
		Reporter.SKIP("It depends on methods which got failed");
		//com.wait(3);
		//Reporter.endTest();

		//TODO
		testDataMap.put(TestNGKeys.description, "discAgr_T09_Verification of 'Forecasting Method and Home Currency' and 'Client and Partner Currency' data from NGC - 'Maintain IOT client, Edit and Update/IOT Approve Addendum' pages with AUSTA - 'Create Agreement' page");
		testDataMap.put(TestNGKeys.methodName, "Test3");
		testDataMap.put(TestNGKeys.priority, "1");
		Reporter.createTest(testDataMap);
		//ExtentManager.createTest(testDataMap);
		Reporter.SKIP("It depends on methods which got failed");
		//com.wait(3);
		//Reporter.endTest();

		//TODO
		testDataMap.put(TestNGKeys.description, "discAgr_T09_Verification of 'Forecasting Method and Home Currency' and 'Client and Partner Currency' data from NGC - 'Maintain IOT client, Edit and Update/IOT Approve Addendum' pages with AUSTA - 'Create Agreement' page");
		testDataMap.put(TestNGKeys.methodName, "Test3");
		testDataMap.put(TestNGKeys.priority, "1");
		Reporter.createTest(testDataMap);
		//ExtentManager.createTest(testDataMap);
		Reporter.SKIP("It depends on methods which got failed");
		//com.wait(3);
		//Reporter.endTest();

		//TODO
		testDataMap.put(TestNGKeys.description, "discAgr_T09_Verification of 'Forecasting Method and Home Currency' and 'Client and Partner Currency' data from NGC - 'Maintain IOT client, Edit and Update/IOT Approve Addendum' pages with AUSTA - 'Create Agreement' page");
		testDataMap.put(TestNGKeys.methodName, "Test3");
		testDataMap.put(TestNGKeys.priority, "1");
		Reporter.createTest(testDataMap);
		//ExtentManager.createTest(testDataMap);
		Reporter.SKIP("It depends on methods which got failed");
		//com.wait(3);
		//Reporter.endTest();

		//TODO
		testDataMap.put(TestNGKeys.description, "discAgr_T09_Verification of 'Forecasting Method and Home Currency' and 'Client and Partner Currency' data from NGC - 'Maintain IOT client, Edit and Update/IOT Approve Addendum' pages with AUSTA - 'Create Agreement' page");
		testDataMap.put(TestNGKeys.methodName, "Test3");
		testDataMap.put(TestNGKeys.priority, "1");
		Reporter.createTest(testDataMap);
		//ExtentManager.createTest(testDataMap);
		Reporter.SKIP("It depends on methods which got failed");
		//com.wait(3);
		//Reporter.endTest();

		//TODO
		testDataMap.put(TestNGKeys.description, "discAgr_T09_Verification of 'Forecasting Method and Home Currency' and 'Client and Partner Currency' data from NGC - 'Maintain IOT client, Edit and Update/IOT Approve Addendum' pages with AUSTA - 'Create Agreement' page");
		testDataMap.put(TestNGKeys.methodName, "Test3");
		testDataMap.put(TestNGKeys.priority, "1");
		Reporter.createTest(testDataMap);
		//ExtentManager.createTest(testDataMap);
		Reporter.SKIP("It depends on methods which got failed");
		//com.wait(3);
		//Reporter.endTest();

		//TODO
		testDataMap.put(TestNGKeys.description, "discAgr_T09_Verification of 'Forecasting Method and Home Currency' and 'Client and Partner Currency' data from NGC - 'Maintain IOT client, Edit and Update/IOT Approve Addendum' pages with AUSTA - 'Create Agreement' page");
		testDataMap.put(TestNGKeys.methodName, "Test3");
		testDataMap.put(TestNGKeys.priority, "1");
		Reporter.createTest(testDataMap);
		//ExtentManager.createTest(testDataMap);
		Reporter.SKIP("It depends on methods which got failed");
		//com.wait(3);
		//Reporter.endTest();

		
		
		
		
		Reporter.onExecutionFinish();
		//END
	}

}