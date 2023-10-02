package com.reporting;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;

import com.config.Config;
import com.config.FrameworkConfig;
import com.reporting.pojos.Scenario;
import com.reporting.pojos.TestNGClass_POJO;
import com.reporting.pojos.TestNGSuite_POJO;
import com.reporting.pojos.TestNGTests_POJO;

/**
 * @author shailendra.rajawat created date 24 Aug 2018
 */
public class CustomReportHTML_Redesign {

	private static List<ArrayList<Scenario>> listOfList;
	private static final String htmlreportFolderPath = FrameworkConfig.getResultFolderPath();
	private static String reportRedesignTemplateFilePath = FrameworkConfig.getReportRedesignTemplateFilePath();

	private final static String projectPlaceholder = "<!-- PROJECT -->";

	private final static String paralleModePlaceholder = "<!-- paralleMode -->";
	private final static String threadCount = "<!-- threadCount -->";
	private final static String assertionEnabledPlaceholder = "<!-- assertionEnabled -->";
	private final static String capturingSnapshotsPlaceholder = "<!-- capturingSnapshots -->";
	private final static String implicitWait = "<!-- implicitWait -->";
	private final static String explicitWait = "<!-- explicitWait -->";
	private final static String width = "<!-- width -->";
	private final static String height = "<!-- height -->";

	private final static String envPlaceholder = "<!-- Environment -->";
	private final static String suitePlaceholder = "<!-- Suite -->";

	private final static String execStartPlaceholder = "<!-- execStartPlaceholder -->";
	private final static String execEndPlaceholder = "<!-- execEndPlaceholder -->";
	private final static String execTimePlaceholder = "<!-- execTimePlaceholder -->";

	private final static String resultPlaceholder = "<!-- INSERT_RESULTS -->";

	private final static String totalScenarioPlaceholder = "<!-- TOTAL_SCENARIOS -->";
	private final static String passScenarioPlaceholder = "<!-- PASSED_SCENARIOS -->";
	private final static String failScenarioPlaceholder = "<!-- FAILED_SCENARIOS -->";
	private final static String skippedScenarioPlaceholder = "<!-- SKIPPED_SCENARIOS -->";
	private final static String errorScenarioPlaceholder = "<!-- ERROR_SCENARIOS -->";
	private final static String warningScenarioPlaceholder = "<!-- WARNING_SCENARIOS -->";
	private final static String fatalScenarioPlaceholder = "<!-- FATAL_SCENARIOS -->";

	private final static String totalStepPlaceholder = "<!-- TOTAL_Step -->";
	private final static String passStepPlaceholder = "<!-- PASSED_Step -->";
	private final static String failStepPlaceholder = "<!-- FAILED_Step -->";
	private final static String skippedStepPlaceholder = "<!-- SKIPPED_Step -->";
	private final static String errorStepPlaceholder = "<!-- ERROR_Step -->";
	private final static String warningStepPlaceholder = "<!-- WARNING_Step -->";
	private final static String fatalStepPlaceholder = "<!-- FATAL_Step -->";

	/**
	 * Added pass, fail and skip percent in Dashboard @author shailendra Oct 23,
	 * 2019
	 */
	private final static String percentageBar = "<!-- percentageBar -->";
	private final static String quickAccess_ItemsPlaceholder = "<!-- quickAccess_items -->";

	/**
	 * Code to create trend chart in the reporting history html pages
	 * 
	 * @author Shailendra
	 * @date Jul 18 203
	 */
	private final static String TOTAL_END_PLACEHOLDER_CHART = "<!-- TOTAL-CHART-END -->";
	private final static String PASS_END_PLACEHOLDER_CHART = "<!-- PASS-CHART-END -->";
	private final static String FAIL_END_PLACEHOLDER_CHART = "<!-- FAIL-CHART-END -->";
	private final static String SKIP_END_PLACEHOLDER_CHART = "<!-- SKIP-CHART-END -->";
	private final static String RUN_MODE = "<!-- Run_mode -->";

	private static String scenario, status, description, errorScreenshotURL, startTime, endTime, executionTime, browserName, browserVersion,
			appType, platform,deviceName_Mobile, os_Desktop, osVersion, timeStamp, methodNameTestNG, classNameTestNG, groupNameTestNG, localeTestNG, testNameTestNG, envNameTestNG,
			priorityTestNG, dependsOnTestNG, parametersTestNG;

	private static Map<Integer, String> priorityWiseStatus = new HashMap<Integer, String>() {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		{
			put(1, STATUS.FATAL.value);
			put(2, STATUS.ERROR.value);
			put(3, STATUS.FAIL.value);
			put(4, STATUS.SKIP.value);
			put(5, STATUS.INFO.value);
			put(6, STATUS.PASS.value);
			put(7, STATUS.WARNING.value);
		}
	};

	private static Map<String, Integer> statusWisePriority = new HashMap<String, Integer>() {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		{
			put(STATUS.FATAL.value, 1);
			put(STATUS.ERROR.value, 2);
			put(STATUS.FAIL.value, 3);
			put(STATUS.SKIP.value, 4);
			put(STATUS.INFO.value, 5);
			put(STATUS.PASS.value, 6);
			put(STATUS.WARNING.value, 7);
		}
	};;

	static synchronized void createHTML() {

		listOfList = Reporter.getListOfList();

		assignSerialNumber();

		try {
			String reportIn = new String(Files.readAllBytes(Paths.get(reportRedesignTemplateFilePath)));

			// SETTING REPORT DASHBOARD DATA
			prepareDashboardCountData();

			reportIn = reportIn.replaceFirst(projectPlaceholder, Matcher.quoteReplacement(FrameworkConfig.PROJECT));
			reportIn = reportIn.replaceFirst(paralleModePlaceholder, Scenario.getInParallel());
			reportIn = reportIn.replaceFirst(threadCount, Scenario.getThreadCount());
			reportIn = reportIn.replaceFirst(assertionEnabledPlaceholder, FrameworkConfig.ENABLE_ASSERTIONS + "");
			reportIn = reportIn.replaceFirst(capturingSnapshotsPlaceholder,
					FrameworkConfig.enableCaptureSnapshots() + "");
			reportIn = reportIn.replaceFirst(implicitWait, FrameworkConfig.WAIT_IMPLICIT + "");
			reportIn = reportIn.replaceFirst(explicitWait, FrameworkConfig.WAIT_EXPLICIT + "");
			reportIn = reportIn.replaceFirst(width, FrameworkConfig.WIDTH + "");
			reportIn = reportIn.replaceFirst(height, FrameworkConfig.HEIGHT + "");

			reportIn = reportIn.replaceFirst(envPlaceholder, "<span class='" + Config.getEnvironment() + "'>"
					+ Config.getEnvironment() + "</span>");
			reportIn = reportIn.replaceFirst(suitePlaceholder,
					Matcher.quoteReplacement(Scenario.getTestNG_SuiteName()));
			reportIn = reportIn.replaceFirst(RUN_MODE,
					Matcher.quoteReplacement(Scenario.getRunMode()));
			// reportIn = reportIn.replaceFirst(testPlaceholder,Test.getTestNG_TestName());

			reportIn = reportIn.replaceFirst(execStartPlaceholder,
					new SimpleDateFormat("dd/MM/yyyy hh:mm:ss a").format(Scenario.getTestExecutionStartDate()));
			reportIn = reportIn.replaceFirst(execEndPlaceholder,
					new SimpleDateFormat("dd/MM/yyyy hh:mm:ss a").format(Scenario.getTestExecutionEndDate()));
			reportIn = reportIn.replaceFirst(execTimePlaceholder, Scenario.getTestExecutionTime());

			reportIn = reportIn.replaceFirst(totalScenarioPlaceholder,
					getDashBoardNumberColor(Scenario.getTotalScenario(), "total"));
			reportIn = reportIn.replaceFirst(passScenarioPlaceholder,
					getDashBoardNumberColor(Scenario.getPassedScenario(), "pass"));
			reportIn = reportIn.replaceFirst(failScenarioPlaceholder,
					getDashBoardNumberColor(Scenario.getFailedScenario(), "fail"));
			reportIn = reportIn.replaceFirst(skippedScenarioPlaceholder,
					getDashBoardNumberColor(Scenario.getSkippedScenario(), "skip"));
			reportIn = reportIn.replaceFirst(errorScenarioPlaceholder,
					getDashBoardNumberColor(Scenario.getErrorScenario(), "error"));
			reportIn = reportIn.replaceFirst(warningScenarioPlaceholder,
					getDashBoardNumberColor(Scenario.getWarningScenario(), "warn"));
			reportIn = reportIn.replaceFirst(fatalScenarioPlaceholder,
					getDashBoardNumberColor(Scenario.getFatalScenario(), "fatal"));

			reportIn = reportIn.replaceFirst(totalStepPlaceholder,
					getDashBoardNumberColor(Scenario.getTotalStep(), "total"));
			reportIn = reportIn.replaceFirst(passStepPlaceholder,
					getDashBoardNumberColor(Scenario.getPassedStep(), "pass"));
			reportIn = reportIn.replaceFirst(failStepPlaceholder,
					getDashBoardNumberColor(Scenario.getFailedStep(), "fail"));
			reportIn = reportIn.replaceFirst(skippedStepPlaceholder,
					getDashBoardNumberColor(Scenario.getSkippedStep(), "skip"));
			reportIn = reportIn.replaceFirst(errorStepPlaceholder,
					getDashBoardNumberColor(Scenario.getErrorStep(), "error"));
			reportIn = reportIn.replaceFirst(warningStepPlaceholder,
					getDashBoardNumberColor(Scenario.getWarningStep(), "warn"));
			reportIn = reportIn.replaceFirst(fatalStepPlaceholder,
					getDashBoardNumberColor(Scenario.getFatalStep(), "fatal"));

			reportIn = reportIn.replaceFirst(percentageBar, generateDashboardPercentageBarData());

			/**
			 * Code to create trend chart in the reporting history html pages
			 * 
			 * @author Shailendra
			 * @date Jul 18 203
			 */
			reportIn = reportIn.replaceFirst(TOTAL_END_PLACEHOLDER_CHART,
					Scenario.getTotalScenario() + TOTAL_END_PLACEHOLDER_CHART);
			reportIn = reportIn.replaceFirst(PASS_END_PLACEHOLDER_CHART,
					(Scenario.getPassedScenario() + Scenario.getWarningScenario()) + PASS_END_PLACEHOLDER_CHART);
			reportIn = reportIn.replaceFirst(FAIL_END_PLACEHOLDER_CHART,
					(Scenario.getFailedScenario() + Scenario.getFatalScenario() + Scenario.getErrorScenario())
							+ FAIL_END_PLACEHOLDER_CHART);
			reportIn = reportIn.replaceFirst(SKIP_END_PLACEHOLDER_CHART,
					Scenario.getSkippedScenario() + SKIP_END_PLACEHOLDER_CHART);

			String stepsAndTitle = "";
			int scenario_counter_for_id = 1;
			for (List<Scenario> listOfResultObjs : listOfList) {

				Scenario test_title = listOfResultObjs.get(0);
				
				scenario = test_title.getScenarioName();
				status = test_title.getStatus();
				startTime = test_title.getStartTime_Scenario_InMillSec() != 0
						? Reporter.convertToHHMMSS(test_title.getStartTime_Scenario_InMillSec())
						: "";
				endTime = test_title.getEndTime_Scenario_InMillSec() != 0 ? Reporter.convertToHHMMSS(test_title.getEndTime_Scenario_InMillSec())
						: "";
				executionTime = test_title.getExecutionTime_Scenario();
				platform = test_title.getPlatform();
				os_Desktop = test_title.getOs_Desktop();
				deviceName_Mobile = test_title.getDeviceName_Mobile();
				osVersion = test_title.getOsVersion();
				browserName = test_title.getBrowserName();
				browserVersion = test_title.getBrowserVersion();
				appType = test_title.getAppType();
				timeStamp = test_title.getTimeStamp();
				errorScreenshotURL = test_title.getSnapshotURL();
				classNameTestNG = test_title.getClassName();
				methodNameTestNG = test_title.getTestNG_MethodName();
				priorityTestNG = test_title.getTestNG_Priority();
				groupNameTestNG = test_title.getGroup();
				localeTestNG = test_title.getLocale();
				testNameTestNG = test_title.getTestNG_TestName();
				envNameTestNG = test_title.getEnvironment();
				dependsOnTestNG = test_title.getDependsOn();
				parametersTestNG = test_title.getParameters();

				test_title.setMethodId(scenario_counter_for_id);
				
				String title_SECTION = "<input type='checkbox' class='highlight-chbox' title='Click to highlight'  onclick='checkUncheckTitle(this)'>"
						+ "<div class='title' id = 'qa_" + methodNameTestNG + "_" + scenario_counter_for_id++ + "'>" + "<div class='test-content'>" + "<div class='title-testStatus'>" + "<span class='"+status+"'>" + status
						+ "</span>" + "</div>"

						+ "<div class='title-startTime'>" + "<span >" + startTime + "</span>" + "</div>"

						+ "<div class='title-Platform'>" 
							+ "<span >" 
							+ deviceName_Mobile 
							+ " " 
							+(((os_Desktop==null || os_Desktop.equals("")))?"": (os_Desktop + ((osVersion==null || osVersion.equals(""))?(""):("["+ osVersion + "]")))) 
							+ " "
							+ platform+"["+appType+"]"
							+ "</span>" 
						+ "</div>"

						+ "<div class='title-Browser'>" + "	<span >" + browserName+ ((browserVersion.equals(""))?(""):("["+ browserVersion + "]")) + "</span>" + "</div>"

						+ constructScreenshotElementTag(errorScreenshotURL)

						+ "<div class='title-text'>" + "	<span >"
						+ (scenario != null ? Matcher.quoteReplacement(scenario) : "") + "  </span>" + "<button class ='downloadTestData' title='Click to download logs for this test' onclick='downloadTestData(this)'>&DownArrowBar;</button>" + "</div>"
						+ "<div class='title-testNGInfo'>" + "	<table border='0'>" + "		<tbody>	"
						+ "<tr>"
						+ "<td>"
						+ "<b>Start / End / Elapsed:</b>"
						+ "</td>"
						+ "<td>"
						+ "<span class='title-start-time-scenario'>" + startTime + "</span> / "
						+ "<span class='title-end-time-scenario'>" + endTime + "</span> / "
						+ "<span class='title-exec-time-scenario'>" + executionTime + "</span>"
						+ "</td>"
						+ "</tr>" 
						+ "<tr><td><b>Method:</b></td><td>" + methodNameTestNG + "</td></tr>"
						+ "<tr><td><b>Test:</b></td><td>" + testNameTestNG + "</td></tr>"
						+ "<tr><td><b>Class:</b></td><td>" + classNameTestNG + "</td></tr>"
						+ "<tr><td><b>Priority:</b></td><td>" + priorityTestNG + "</td></tr>"
						+ "<tr><td><b>Environment:</b></td><td>" + envNameTestNG + "</td></tr>"
						+ "<tr><td><b>Depends On:</b></td><td>" + dependsOnTestNG + "</td></tr>"
						+ "<tr><td><b>Parameters :</b></td><td>"+ parametersTestNG+ "</td></tr>" 
						+ "<tr><td><b>Group:</b></td><td>" + groupNameTestNG + "</td></tr>"
						+ "<tr><td><b>Locale:</b></td><td>" + localeTestNG + "</td></tr>"
						+ "<tr><td><b>Steps:</b></td><td><!-- stepsCounter --></td></tr>" 
						+ "</tbody>"
						+ "	</table>" + "	</div>" + "	</div>" 
						+ "<button title='Click to Expand/Collapse the underlying steps' class='expand_collapse_test' onclick='hideShowTestStepsAsPerId(this)'>&#8597;</button>"
						+ "	</div>"
						;

				String steps_LI = "";

				long arraySize = listOfResultObjs.size();

				// TODO ADDING STEPS OF TEST
				int stepCounter = 0;
				for (int i = 1; i < arraySize; i++) {

					// TODO In case there are NODES, Add [DIV > OL > LI] Elements
					Scenario test_step = listOfResultObjs.get(i);

					List<Scenario> subSteps = test_step.getList();
					int subStepsSize = subSteps.size();
					if (subStepsSize > 0) {

						String nodeTitle_SECTION = "<input type='checkbox' class='highlight-chbox' title='Click to highlight' onclick='checkUncheckNode(this)'>"
								+ "<div class='node'>" + "<div class=''>"
								+ "<div class='title-testStatus'>" + "<span class='"+test_step.getStatus()+"'>" + test_step.getStatus() + "</span>"
								+ "</div>"

								+ "<div class='title-startTime'>" + "<span >" + test_step.getTimeStamp() + "</span>"
								+ "</div>"

								+ "<div class='title-text'>" + "	<span >"
								+ (test_step.getDescription() != null
										? Matcher.quoteReplacement(test_step.getDescription())
										: "")
								+ "</span>" + "</div>" + "<div class='stepCount'>Steps: " + subStepsSize + "</div>"
								+ "	</div>" 
								+ "<button title='Click to Expand/Collapse the underlying steps' class='expand_collapse_node'>&#8597;</button>"
								+ "	</div>"
								;

						String nodeSteps_LI = "";
						for (int j = 0; j < subStepsSize; j++) {
							stepCounter++;
							Scenario test_SubStep = subSteps.get(j);
							nodeSteps_LI = nodeSteps_LI + "<li class='stepLI'>" + "<div class='step'>"

									+ "<div class='steps-step-Status'>" + "<span class='"+test_SubStep.getStatus()+"'>" + test_SubStep.getStatus()
									+ "</span>" + "</div>"

									+ "<div class='steps-step-startTime'>" + "<span >" + test_SubStep.getTimeStamp()
									+ "</span>" + "</div>"

									+ "<div class='steps-step-text'>"
									+ "<input type='checkbox' class='highlight-chbox' title='Click to highlight' onclick='checkUncheck(this)'>" + "<span class=''>"
									+ (test_SubStep.getDescription() != null
											? Matcher.quoteReplacement(test_SubStep.getDescription())
											: "")
									+ "</span>" + "</div>"

									+ constructScreenshotElementTag(test_SubStep.getSnapshotURL())

									+ "</div>" + "</li>";
						}
						steps_LI = steps_LI + "<li class='stepLI'>" + nodeTitle_SECTION + "<div id='steps'>" + "<ol>"
						/*
						 * +
						 * "<input type='text'  onkeyup='performStepsSearch(this)' placeholder='Search Steps'>"
						 */
								+ nodeSteps_LI + "</ol>" + "</div>" + "</li>";
					} else {
						// TODO In case there are just normal steps, Add LI Elements
						description = test_step.getDescription();
						errorScreenshotURL = test_step.getSnapshotURL();
						timeStamp = test_step.getTimeStamp();
						status = test_step.getStatus();

						if (status.equals(STATUS.NODE.value)) {
							String nodeTitle_SECTION = "<li class='stepLI'>"
									+ "<input type='checkbox' class='highlight-chbox' title='Click to highlight' onclick='checkUncheckNode(this)'>" 
									+ "<div class='node'>"
									+ "<div class=''>" + "<div class='title-testStatus'>" + "<span class='info'>"
									+ STATUS.INFO.value + "</span>" + "</div>"

									+ "<div class='title-startTime'>" + "<span >" + test_step.getTimeStamp() + "</span>"
									+ "</div>"

									+ "<div class='title-text'>" + "	<span >"
									+ (test_step.getDescription() != null
											? Matcher.quoteReplacement(test_step.getDescription())
											: "")
									+ "</span>" + "</div>" + "<div class='stepCount'>Steps: 0</div>" + "	</div>"
									+ "	</div>" + "</li>";

							steps_LI = steps_LI + nodeTitle_SECTION;
						} else {
							stepCounter++;
							steps_LI = steps_LI + "<li class='stepLI'>" + "<div class='step'>"

									+ "<div class='steps-step-Status'>" + "<span class='"+status+"'>"
									+ status + "</span>" + "</div>"

									+ "<div class='steps-step-startTime'>" + "<span >" + timeStamp + "</span>"
									+ "</div>"

									+ "<div class='steps-step-text'>"
									+ "<input type='checkbox' class='highlight-chbox' title='Click to highlight' onclick='checkUncheck(this)'>" + "<span class=''>"
									+ (description != null ? Matcher.quoteReplacement(description) : "") + "</span>"
									+ "</div>"

									+ constructScreenshotElementTag(errorScreenshotURL)

									+ "</div>" + "</li>";
						}

					}

				}

				String steps_SECTION = "<div id='steps'>" + "<ol>"
				/*
				 * +
				 * "<input type='text' onkeyup='performStepsSearch(this)' placeholder='Search Steps'>"
				 */
						+ steps_LI + "</ol>" + "</div>";

				title_SECTION = title_SECTION.replaceFirst("<!-- stepsCounter -->", stepCounter + "");
				stepsAndTitle = stepsAndTitle + "<li class='test'>" + title_SECTION + steps_SECTION + "</li>";
			}

			reportIn = reportIn.replaceFirst(resultPlaceholder,
					Matcher.quoteReplacement(stepsAndTitle) + resultPlaceholder);

			// Adding Quick Access Data as well, rather than generating it
			// on page load by java script, This data will be picked up by
			// reporting history manager and will be displayed in quick view
			// section
			String quicAccessData = constructQuickAccessDataElements();
			reportIn = reportIn.replaceFirst(quickAccess_ItemsPlaceholder,
					Matcher.quoteReplacement(quicAccessData) + quickAccess_ItemsPlaceholder);

			// Creating the new file
			Files.write(Paths.get(htmlreportFolderPath + "/" + FrameworkConfig.HTML_REPORT_FILE_NAME_CUSTOM),
					reportIn.getBytes(), StandardOpenOption.CREATE);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * This method will add the Passing, Failing and Skipping percent in Dashboard
	 * 
	 * @param reportIn
	 * @return reportIn
	 * @author shailendra Nov 1, 2019
	 */
	private static String generateDashboardPercentageBarData() {

		String mainDivContent = "";
		String divMiniBarMarginBottomPx = "30";
		String divMiniMiddleMarginBottomPx = "-25";

		if (Scenario.getPassingPercent() != 0.0) {
			String tempPerc = Scenario.getPassingPercent() + "";
			tempPerc = (tempPerc.length() > 4) ? tempPerc.substring(0, 5) : tempPerc;
			tempPerc = tempPerc + "%";

			String div = "<div class='miniBarProgress tooltips green' style='width: " + tempPerc
					+ "; background-color: green;'>" + "	<div class='mini-middle'>" + tempPerc + "	</div>"
					+ "</div>";
			mainDivContent = mainDivContent + div;
			// reportIn = reportIn.replaceAll(passingPercent,div);
		}

		if (Scenario.getFailurePercent() != 0.0) {
			String tempPerc = Scenario.getFailurePercent() + "";
			tempPerc = (tempPerc.length() > 4) ? tempPerc.substring(0, 5) : tempPerc;
			tempPerc = tempPerc + "%";

			String margin_bottom = "";
			if (Scenario.getFailurePercent() < 20.0 && Scenario.getPassingPercent() > 0.0
					&& Scenario.getSkipPercent() > 0.0) {
				margin_bottom = "margin-bottom: " + divMiniMiddleMarginBottomPx + "px;";
				divMiniBarMarginBottomPx = "55";
			}

			String div = "<div class='miniBarProgress tooltips red' style='width: " + tempPerc
					+ "; background-color: red;'>"
					+ "									<div class='mini-middle' style='" + margin_bottom + "'>"
					+ tempPerc + "									</div>" + "								</div>";

			mainDivContent = mainDivContent + div;
			// reportIn = reportIn.replaceAll(failurePercent,div);
		}

		if (Scenario.getSkipPercent() != 0.0) {
			String tempPerc = Scenario.getSkipPercent() + "";
			tempPerc = (tempPerc.length() > 4) ? tempPerc.substring(0, 5) : tempPerc;
			tempPerc = tempPerc + "%";

			String div = "<div class='miniBarProgress tooltips brown' style='width: " + tempPerc
					+ "; background-color: brown;'>" + "									<div class='mini-middle'>"
					+ tempPerc + "									</div>" + "								</div>";

			mainDivContent = mainDivContent + div;

			// reportIn = reportIn.replaceAll(skippedPercent,div);
		}

		mainDivContent = "<div class='miniBar' style='margin-bottom: " + divMiniBarMarginBottomPx + "px;'>"
				+ mainDivContent + "</div>";

		return mainDivContent;
	}

	private static String constructQuickAccessDataElements() {

		String testNgSuite = Scenario.getTestNG_SuiteName();

		TestNGSuite_POJO suite = new TestNGSuite_POJO(testNgSuite);

		for (ArrayList<Scenario> test : listOfList) {

			// TestNG Test
			String testNgTest = test.get(0).getTestNG_TestName();
			TestNGTests_POJO testNGTests = suite.containsTest(testNgTest);
			if (testNGTests == null) {
				testNGTests = suite.addTests(testNgTest);
			}

			// TestNG Class
			String testClass = test.get(0).getClassName();
			TestNGClass_POJO testNGClass = testNGTests.containsClass(testClass);
			if (testNGClass == null) {
				testNGClass = testNGTests.addClass(testClass);
			}

			testNGClass.addMethod(test.get(0));

		}

		updateQuickAccess_ParentsStatusBasedOn_Methods(suite);

		return generateHTML_QuickAccessDataElements(suite);
	}

	private static void updateQuickAccess_ParentsStatusBasedOn_Methods(TestNGSuite_POJO suite) {

		// Updating Class Level Status as per Methods
		List<TestNGTests_POJO> testList = suite.getTestList();

		if (testList == null || testList.size() == 0)
			return;

		for (TestNGTests_POJO test : testList) {

			List<TestNGClass_POJO> classList = test.getClassList();
			for (TestNGClass_POJO clas : classList) {
				updateClassStatusBasedOnMethod(clas);
			}
		}

		// Updating Test Level Status as per Class
		for (TestNGTests_POJO test : testList) {
			updateTestStatusBasedOnClass(test);
		}

		// Updating Suite Level Status as per Tests
		updateSuiteStatusBasedOnTests(suite);
	}

	/**
	 * @param suite
	 * @author Shailendra 19-Jan-2020
	 */
	private static void updateSuiteStatusBasedOnTests(TestNGSuite_POJO suite) {
		List<TestNGTests_POJO> list = suite.getTestList();
		
		int arr[] = new int[list.size()];
		for (int i = 0; i < list.size(); i++) {
			arr[i] = statusWisePriority.get(list.get(i).getStatus());
		}
		Arrays.sort(arr);
		suite.setStatus(priorityWiseStatus.get(arr[0]));
		
		/*
		 * for (TestNGTests_POJO tests : list) { if
		 * (tests.getStatus().equals(STATUS.FATAL.value)) {
		 * suite.setStatus(STATUS.FATAL.value); break; } else if
		 * (tests.getStatus().equals(STATUS.ERROR.value)) {
		 * suite.setStatus(STATUS.ERROR.value); break; } else if
		 * (tests.getStatus().equals(STATUS.FAIL.value)) {
		 * suite.setStatus(STATUS.FAIL.value); } else if
		 * (tests.getStatus().equals(STATUS.WARNING.value)) {
		 * suite.setStatus(STATUS.WARNING.value); } }
		 */

	}

	// TODO Warning status fix
	/**
	 * @author Shailendra 19-Jan-2020
	 */
	private static void updateClassStatusBasedOnMethod(TestNGClass_POJO clas) {
		List<Scenario> methList = clas.getMethodsList();

		int arr[] = new int[methList.size()];
		for (int i = 0; i < methList.size(); i++) {
			arr[i] = statusWisePriority.get(methList.get(i).getStatus());
		}
		Arrays.sort(arr);
		clas.setStatus(priorityWiseStatus.get(arr[0]));
		
		/*
		 * for (TestNGMethods_POJO meth : methList) {
		 * 
		 * if (meth.getStatus().equals(STATUS.FATAL.value)) {
		 * clas.setStatus(STATUS.FATAL.value); break; } else if
		 * (meth.getStatus().equals(STATUS.ERROR.value)) {
		 * clas.setStatus(STATUS.ERROR.value); break; } else if
		 * (meth.getStatus().equals(STATUS.FAIL.value)) {
		 * clas.setStatus(STATUS.FAIL.value); } else if
		 * (meth.getStatus().equals(STATUS.WARNING.value)) {
		 * clas.setStatus(STATUS.WARNING.value); }
		 * 
		 * }
		 */

	}

	/**
	 * @author Shailendra 19-Jan-2020
	 */
	private static void updateTestStatusBasedOnClass(TestNGTests_POJO test) {
		List<TestNGClass_POJO> list = test.getClassList();
		
		int arr[] = new int[list.size()];
		for (int i = 0; i < list.size(); i++) {
			arr[i] = statusWisePriority.get(list.get(i).getStatus());
		}
		Arrays.sort(arr);
		test.setStatus(priorityWiseStatus.get(arr[0]));
		
		
		/*
		 * for (TestNGClass_POJO cls : list) { if
		 * (cls.getStatus().equals(STATUS.FATAL.value)) {
		 * test.setStatus(STATUS.FATAL.value); break; } else if
		 * (cls.getStatus().equals(STATUS.ERROR.value)) {
		 * test.setStatus(STATUS.ERROR.value); break; } else if
		 * (cls.getStatus().equals(STATUS.FAIL.value)) {
		 * test.setStatus(STATUS.FAIL.value); } else if
		 * (cls.getStatus().equals(STATUS.WARNING.value)) {
		 * test.setStatus(STATUS.WARNING.value); } }
		 */
	}

	private static String generateHTML_QuickAccessDataElements(TestNGSuite_POJO suite) {
		// Constructing the HTML Hierarchy
		List<TestNGTests_POJO> testList = suite.getTestList();

		if (testList == null || testList.size() == 0)
			return "";

		String testHtml = "<ol style='padding-left:20px;'>";

		for (TestNGTests_POJO test : testList) {
			testHtml = testHtml + "<li class='add-custom-arrow ' onclick='hideShowQuicViewNextList(this)'> <span class='testNG-test'>" + "<span class='"+test.getStatus()+"'>" + test.getStatus() + "</span> " + test.getTestName() + "</span></li>";

			List<TestNGClass_POJO> classList = test.getClassList();
			String classHtml = "<ol style='padding-left:20px;'>";
			for (TestNGClass_POJO clas : classList) {
				classHtml = classHtml
						+ "<li class='add-custom-arrow ' onclick='hideShowQuicViewNextList(this)'><span class='testNG-class'>" + "<span class='"+clas.getStatus()+"'>" + clas.getStatus() + "</span> "+ clas.getClassName() + "</span></li>";

				List<Scenario> methList = clas.getMethodsList();

				String methHtml = "<ol class = 'quickviewMethodList'>";

				for (Scenario meth : methList) {

					methHtml = methHtml + "<li class=''>"

							+ "<span onclick=\"window.location.href='#qa_" + meth.getTestNG_MethodName() + "_"
							+ meth.getTestNG_MethodId() + "'\" class='quickViewTest' title='" + meth.getScenarioName() + "'>"
							+ "<span class='"+meth.getStatus()+"'>" + meth.getStatus() + "</span> " + meth.getScenarioName()
							+ "<div class='set_time_sheduleds'>"
							+ "    <span class='btn_0 title-start-time-scenario'>" + meth.getStartTime_Scenario_InHHMMSS() + "</span> / "
							+ "    <span class='btn_0 title-end-time-scenario'>" + meth.getEndTime_Scenario_InHHMMSS() + "</span> / "
							+ "    <span class='btn_0 title-exec-time-scenario'>" + meth.getExecutionTime_Scenario() + "</span> "
							+ ((null == meth.getPlatform() || "".equals(meth.getPlatform())) ? ""
									: ("<span class='btn_0'>" + "<span class='btn_0 group'>" +meth.getPlatform() + "</span> "
											+ "</span> "))
							+ ((null == meth.getAppType() || "".equals(meth.getAppType())) ? ""
									: ("<span class='btn_0'>" + "<span class='btn_0 group'>" +meth.getAppType() + "</span> "
											+ "</span> "))
							+ ((null == meth.getOs_Desktop() || "".equals(meth.getOs_Desktop())) ? ""
									: ("<span class='btn_0'>" + "<span class='btn_0 group'>" + meth.getOs_Desktop() + "</span> "
											+ "</span> "))
							+ ((null == meth.getBrowserName() || "".equals(meth.getBrowserName())) ? ""
									: ("<span class='btn_0'>" + "<span class='btn_0 group'>" + meth.getBrowserName() + "</span> "
											+ "</span> "))
							+ ((null == meth.getGroup() || "[]".equals(meth.getGroup())) ? ""
									: ("<span class='btn_0'>" + generateGroupTags(meth.getGroup()) + "</span> "))
							+ ((null == meth.getParameters() || "[]".equals(meth.getParameters())) ? ""
									: ("<span class='btn_0'>" + generateParametersTags(meth.getParameters())
											+ "</span> "))
							+ ((null == meth.getLocale()) ? ""
									: ("<span class='btn_0'>" + "<span class='btn_0 group'> Locale:" + meth.getLocale() + "</span> "
											+ "</span> "))
							+ "</div>" + "</span>" + "</li>";

				}
				methHtml = methHtml + "</ol>";
				classHtml = classHtml + methHtml;
			}
			classHtml = classHtml + "</ol>";
			testHtml = testHtml + classHtml;
		}
		testHtml = testHtml + "</ol>";

		String elements = "<div class='sidenavItems'>" + "<ol>" + "<li class=''>" + "<span class =''>"
				+ "<span class ='"+suite.getStatus()+"'>" + suite.getStatus() + "</span> " + suite.getSuiteName() +  "</span>" + testHtml + "</li>" + "</ol>"
				+ "</div>";
		return elements;
	}

	private static String generateParametersTags(String param) {

		String data = "";

		if (param.equals("[]")) {
			return "<span class='btn_0 group'></span> ";
		} else {

			param = param.substring(1, param.length() - 1);

			if (param.contains(",")) {
				String arr[] = param.split(",");
				for (String val : arr) {
					data = data + "<span class='btn_0 group'> Param:" + val + "</span> ";
				}
			} else {
				data = "<span class='btn_0 group'> Param:" + param + "</span> ";
			}
		}

		return data;
	}

	private static String generateGroupTags(String group) {

		String data = "";

		if (group.equals("[]")) {
			return "<span class='btn_0 group'> </span>";
		} else {

			group = group.substring(1, group.length() - 1);

			if (group.contains(",")) {
				String arr[] = group.split(",");
				for (String val : arr) {
					data = data + "<span class='btn_0 group'> Group:" + val + "</span> ";
				}
			} else {
				data = "<span class='btn_0 group'> Group:" + group + "</span> ";
			}
		}

		return data;
	}

	private static void assignSerialNumber() {
		for (int i = 1; i <= listOfList.size(); i++) {
			List<Scenario> resultList = listOfList.get(i - 1);
			Scenario r = resultList.get(0);
			r.setSrNo(i + "");
		}
	}

	private static String constructScreenshotElementTag(String errorScreenshotURL) {
		if ("".equals(errorScreenshotURL.trim()) || errorScreenshotURL == null) {
			return "";
		} else if (errorScreenshotURL.contains(FrameworkConfig.MOVIE_TEMPLATE_NAME)) {
			return "<div class='title-Movie'>" + "<a href='" + errorScreenshotURL + "' target ='_blank' title ='Click to play movie of this execution'>&#127916;</a>"
					+ "</div>";
		}
		return "<div class='steps-step-snap'>" + "<a href='" + errorScreenshotURL + "' target ='_blank'><img src='"
				+ errorScreenshotURL + "' style='max-width: 250px;max-height: 250px;'></a>" + "</div>";
	}

	private static String getDashBoardNumberColor(long number, String string) {
		String placeHolderWithColor = "";
		if ("0".equals(number + "")) {
			placeHolderWithColor = "<span style='color:#958f8f;'>" + number + "</span>";
		} else {
			switch (string) {
			case "total":
				placeHolderWithColor = "<span class='total'>" + number + "</span>";
				break;
			case "pass":
				placeHolderWithColor = "<span class='none'>" + number + "</span>";
				break;
			case "fail":
				placeHolderWithColor = "<span class='none'>" + number + "</span>";
				break;
			case "skip":
				placeHolderWithColor = "<span class='none'>" + number + "</span>";
				break;
			case "error":
				placeHolderWithColor = "<span class='none'>" + number + "</span>";
				break;
			case "warn":
				placeHolderWithColor = "<span class='none'>" + number + "</span>";
				break;
			case "fatal":
				placeHolderWithColor = "<span class='none'>" + number + "</span>";
				break;
			}
		}

		return placeHolderWithColor;
	}

	private static synchronized void prepareDashboardCountData() {
		// This method will get the counts of (pass, fail, skip, error, fatal)
		// scenarios, and Also change the scenario status as per the overall status of
		// steps

		updateNODEStatusBasedOnSteps(STATUS.INFO.value);
		updateNODEStatusBasedOnSteps(STATUS.PASS.value);
		updateNODEStatusBasedOnSteps(STATUS.WARNING.value);
		updateNODEStatusBasedOnSteps(STATUS.SKIP.value);
		updateNODEStatusBasedOnSteps(STATUS.ERROR.value);
		updateNODEStatusBasedOnSteps(STATUS.FAIL.value);
		updateNODEStatusBasedOnSteps(STATUS.FATAL.value);

		//updateScenarioStatusBasedOnSteps(STATUS.WARNING.value);
		updateScenarioStatusBasedOnSteps(STATUS.SKIP.value);
		updateScenarioStatusBasedOnSteps(STATUS.ERROR.value);
		updateScenarioStatusBasedOnSteps(STATUS.FAIL.value);
		updateScenarioStatusBasedOnSteps(STATUS.FATAL.value);

		// Getting the pass scenario count
		long skipCount = getScenarioCountBasedOnStatus(STATUS.SKIP.value);

		// Getting the pass scenario count
		long passCount = getScenarioCountBasedOnStatus(STATUS.PASS.value);

		// Getting the fatal scenario count
		long fatalCount = getScenarioCountBasedOnStatus(STATUS.FATAL.value);

		// Getting the fail scenarios count
		long failCount = getScenarioCountBasedOnStatus(STATUS.FAIL.value);

		// Getting the error scenario count
		long errorCount = getScenarioCountBasedOnStatus(STATUS.ERROR.value);

		// Getting the warning scenario count
		long warningCount = getScenarioCountBasedOnStatus(STATUS.WARNING.value);

		// Setting the total Test count from TestNG Listener Class
		// Test.setTotalScenario(passCount+failCount+skipCount+errorCount+fatalCount+warningCount);

		long totalCount = passCount + failCount + skipCount + errorCount + fatalCount + warningCount;

		Scenario.setTotalScenario(totalCount);
		Scenario.setPassedScenario(passCount);
		Scenario.setFailedScenario(failCount);
		Scenario.setSkippedScenario(skipCount);
		Scenario.setErrorScenario(errorCount);
		Scenario.setFatalScenario(fatalCount);
		Scenario.setWarningScenario(warningCount);

		Scenario.setPassingPercent(((double) passCount + (double) warningCount) * 100 / (double) totalCount);
		Scenario.setFailurePercent(
				((double) failCount + (double) errorCount + (double) fatalCount) * 100 / (double) totalCount);
		Scenario.setSkipPercent((double) skipCount * 100 / (double) totalCount);

		long infoStep = getStepCountBasedOnStatus(STATUS.INFO.value);
		long passStep = getStepCountBasedOnStatus(STATUS.PASS.value);
		long failStep = getStepCountBasedOnStatus(STATUS.FAIL.value);
		long skipStep = getStepCountBasedOnStatus(STATUS.SKIP.value);
		long errorStep = getStepCountBasedOnStatus(STATUS.ERROR.value);
		long warnStep = getStepCountBasedOnStatus(STATUS.WARNING.value);
		long fatalStep = getStepCountBasedOnStatus(STATUS.FATAL.value);

		Scenario.setTotalStep(infoStep + passStep + failStep + skipStep + errorStep + warnStep + fatalStep);
		Scenario.setPassedStep(passStep + infoStep);
		Scenario.setFailedStep(failStep);
		Scenario.setSkippedStep(skipStep);
		Scenario.setErrorStep(errorStep);
		Scenario.setWarningStep(warnStep);
		Scenario.setFatalStep(fatalStep);

	}

	private static long getStepCountBasedOnStatus(String status) {
		long count = 0;
		for (int i = 0; i < listOfList.size(); i++) {
			List<Scenario> stepsList = listOfList.get(i);
			for (int j = 1; j < stepsList.size(); j++) {
				List<Scenario> subStepsList = stepsList.get(j).getList();
				int size = subStepsList.size();
				if (size > 0) {
					for (int k = 0; k < size; k++) {
						String subStepStatus = subStepsList.get(k).getStatus();
						if (subStepStatus.equalsIgnoreCase(status)) {
							count++;
						}
					}
				} else {
					String stepStatus = stepsList.get(j).getStatus();
					if (stepStatus.equalsIgnoreCase(status)) {
						count++;
					}
				}
			}
		}
		return count;
	}

	private static long getScenarioCountBasedOnStatus(String status) {
		long count = 0;
		for (int i = 0; i < listOfList.size(); i++) {
			List<Scenario> resultList = listOfList.get(i);
			String scenarioStatus = resultList.get(0).getStatus();
			if (scenarioStatus.equalsIgnoreCase(status)) {
				count++;
			}
		}
		return count;
	}

	private static void updateNODEStatusBasedOnSteps(String status) {
		for (int i = 0; i < listOfList.size(); i++) {
			List<Scenario> stepsList = listOfList.get(i);
			for (int j = 1; j < stepsList.size(); j++) {

				List<Scenario> subStepsList = stepsList.get(j).getList();
				if (subStepsList.size() > 0) {
					for (int k = 0; k < subStepsList.size(); k++) {
						String subStepStatus = subStepsList.get(k).getStatus();
						if (subStepStatus.equalsIgnoreCase(status)) {
							stepsList.get(j).setStatus(status);
							break;
						}
					}
				}
			}
		}
	}

	private static void updateScenarioStatusBasedOnSteps(String status) {
		for (int i = 0; i < listOfList.size(); i++) {
			List<Scenario> stepsList = listOfList.get(i);
			for (int j = 1; j < stepsList.size(); j++) {
				String stepStatus = stepsList.get(j).getStatus();
				if (stepStatus.equalsIgnoreCase(status)) {
					stepsList.get(0).setStatus(status);
					break;
				}
			}
		}
	}

	private static synchronized String getStyledStepDesc_BasedOnStatus(String status) {
		String font_color_Class = "none";
		// Setting up the font color based on status value
		if (status != null) {
			if (status.equalsIgnoreCase(STATUS.INFO.value)) {
				font_color_Class = "info";
			} else if (status.equalsIgnoreCase(STATUS.FATAL.value)) {
				font_color_Class = "fail";
			} else if (status.equalsIgnoreCase(STATUS.FAIL.value)) {
				font_color_Class = "fail";
			} else if (status.equalsIgnoreCase(STATUS.WARNING.value)) {
				font_color_Class = "warning";
			} else if (status.equalsIgnoreCase(STATUS.ERROR.value)) {
				font_color_Class = "fail";
			}
		}
		return font_color_Class;
	}

}
