package com.config;

import java.io.File;

import com.util.Util;

public class FrameworkConfig {

	/**
	 * This constant will tell the framework where to keep the execution reports copy
	 * */
	public static final String REPORT_COPY_FOLDER_PATH = "D:/Blum-reports";
	
	/**
	 * This constant holds the project name which is displayed in the HTML Reports
	 */
	public static final String PROJECT = "UAM";
	
	/**
	 * {@code captureSnapshots} : set false for speeding up the execution, It will
	 * stop capturing snapshots of each and every action(click/sendkeys etc)
	 * performed on application, but it will not stop the snapshots capturing of
	 * failed/fatal methods, this flag also controls the movie generation if this is
	 * true then only movie will be generated
	 * 
	 * Creating this method to prevent the 100% Disk usage on Jenkins. As Jenkins is
	 * installed on Linux machine, so creating a logic to stop capturing movie of
	 * Execution
	 * 
	 * @author DRIVER BINARY Oct 29, 2019
	 */
	public static boolean enableCaptureSnapshots() {
		if (Util.getOSName().toLowerCase().contains("lin")) {
			return true;
		}
		return true;
	}


	/**
	 * {@code enableAssertions} : set true to enable TestNG Assertion feature while
	 * execution, this will stop the execution of a method once a failure is
	 * reported through <br>
	 * <b><code>CustomReporter.report(Status.FAIL, "");</code></b>, or in case any
	 * exception.
	 */
	public static final boolean ENABLE_ASSERTIONS = false;

	/**
	 * {@code enableConsoleLogs} : set true to enable the report pass,fail etc
	 * statements to be printed on console while execution
	 */
	public static final boolean ENABLE_CONSOLE_LOGS = true;

	/**
	 * {@code enableMailNotification} : If set true, then framework will start
	 * sending the custom email notification after completion of execution
	 */
	public static final boolean ENABLE_EMAIL_NOTIFICATIONS = false;

	/**
	 * This constant controls how many old reports will be stored, once
	 * ReportingHistory folder count reaches this number, old folders will be
	 * deleted to make the folder count equal to this number.
	 * 
	 * Assign 0 to stop manageHistory Code
	 * 
	 * Creating this method to prevent the 100% Disk usage on Jenkins. As Jenkins is
	 * installed on Linux machine, so creating a logic to stop maintaining history
	 * of more than 50 executions
	 * 
	 * @author DRIVER BINARY Oct 29, 2019
	 */
	public static int reportingHistoryFolderLimit() {
		if (Util.getOSName().toLowerCase().contains("lin")) {
			return 10;
		}
		return 500;
	}

		
	/**
	 * This constant will control how rows one page of reporting history HTML should
	 * display, this enables the pagination.
	 */
	public static final String REPORTING_HISTORY_FOLDER_NAME = "ReportingHistory";
	public static final String REPORTING_HISTORY_FOLDER_NAME_HTML = "HTML";
	
	/** Count of last executions to be shown in chart in Reporting history report */
	private static final int REPORTING_HISTORY_ROWS_PER_PAGE = 10;
	public static final long CHART_LAST_EXECUTIONS_COUNT = 50;
	public static final boolean CHART_DEFAULT_VISIBILITY = false;
	
	/** Either set a valid url or set null/blank in this field*/
	private static final String HTML_REPORT_LOGO_URL = "https://naveenautomationlabs.com/wp-content/uploads/2020/05/LinkedIn-copy.png";//"https://clipground.com/images/selenium-logo-7.png";

	/**
	 * controls the driver(browser) object parameters like : explicit wait time implicit wait
	 * time dimensions width X height
	 */
	public static final long WAIT_EXPLICIT = 10;
	public static final long WAIT_IMPLICIT = 0;
	public static final int WIDTH = /*750;*/1920;
	public static final int HEIGHT = /*800;*/1000;
	
	/**
	 * the directory in which our project is placed
	 */
	public static final String ROOT = System.getProperty("user.dir");
	
	
	/**
	 * Report templates, You can pick any themes by changing the names of
	 * REPORT_TEMPLATE and REPORT_HISTORY_TEPLATE. Just put light, dark or retro
	 * at the ending
	 */
	public static final String REPORT_THEME = "light";
	public static final String REPORT_TEMPLATE_NAME = "Report_Redesign_Template_"+REPORT_THEME+".html";
	public static final String REPORT_HISTORY_TEMPLATE_NAME = "page_"+REPORT_THEME+".html";
	
	public static final String MOVIE_TEMPLATE_NAME = "snapshotsMovieTemplate.html";

	/** Results will be stored in this folder, with following names */
	public static final String HTML_REPORT_FOLDER_NAME = "Report";
	public static final String HTML_REPORT_FOLDER_NAME_KARATE = "karate-reports";
	public static final String HTML_REPORT_FILE_NAME_CUSTOM = "Report_Redesign_Template.html";
	public static final String HTML_REPORT_FILE_NAME_EXTENT = "Report_Extent.html";
	public static final String HTML_REPORT_FILE_NAME_KARATE = "karate-summary.html";

	/** All downloaded files will be stored in this folder */
	public static final String DOWNLOADS_FOLDER_NAME = "Downloads";

	/** All snapshots will be stored in this folder */
	public static final String SNAPSHOTS_FOLDER_NAME = "Snapshots";

	/**
	 * This method will tell you how many items will get display on single html page
	 * of Reporting History
	 */
	public static int getReportinghistoryfolderperpage() {
		int val = REPORTING_HISTORY_ROWS_PER_PAGE;
		if (val <= 0) {
			return 1;
		} else {
			return val;
		}
	}
	

	private static final String RESOURCE_FOLDER_NAME_MAIN = "src/main/resources";
	public static String snapshotsMovieTemplateFilePath() {
		String path = ROOT + "/" + RESOURCE_FOLDER_NAME_MAIN + "/" + MOVIE_TEMPLATE_NAME;
		return path;
	}

	public static String getReportRedesignTemplateFilePath() {
		String path = ROOT + "/" + RESOURCE_FOLDER_NAME_MAIN + "/" + REPORT_TEMPLATE_NAME;
		return path;
	}

	public static String getHistoryReportRedesignTemplateFilePath() {
		String path = ROOT + "/" + RESOURCE_FOLDER_NAME_MAIN + "/" + REPORT_HISTORY_TEMPLATE_NAME;
		return path;
	}
	
	public static String getSnapShotsFolderPath() {
		String path = ROOT + "/" + SNAPSHOTS_FOLDER_NAME;
		return path;
	}
	

	public static String getKarateReportFolderPath() {
		String path = ROOT + "/target/karate-reports" ;
		return path;
	}


	public static String getResultextenthtmlfilePath() {
		String path = ROOT + "/" + HTML_REPORT_FOLDER_NAME + "/" + HTML_REPORT_FILE_NAME_EXTENT;
		return path;
	}

	public static String getResultHtmlFilePath() {
		String path = ROOT + "/" + HTML_REPORT_FOLDER_NAME + "/" + HTML_REPORT_FILE_NAME_CUSTOM;
		return path;
	}

	public static String getResultFolderPath() {
		String val = ROOT + "/" + HTML_REPORT_FOLDER_NAME;
		// Creating the Result Folder in case it is not already present
		File fileObj = new File(val);
		if (!fileObj.exists()) {
			fileObj.mkdir();
		}
		return val;
	}

	public static String getDownloadsPath() {
		// System.out.println("=============================================");
		String val = ROOT ;
		
		if (Util.getOSName().toLowerCase().contains("win")) {
			val = val + "\\" + DOWNLOADS_FOLDER_NAME;
		}else {
			val = val + "/" + DOWNLOADS_FOLDER_NAME;
		}
		
		// Creating the Result Folder in case it is not already present
		File fileObj = new File(val);
		if (!fileObj.exists()) {
			System.out.println("CREATING DIRECTORY");
			fileObj.mkdir();
		}
		// System.out.println("DOWNLOADS PATH : " + val);
		// System.out.println("=============================================");
		return val;
	}

	public static String getReportingHistoryFolderPath() {
		String val = ROOT + "/" + REPORTING_HISTORY_FOLDER_NAME;
		// Creating the Result Folder in case it is not already present
		File fileObj = new File(val);
		if (!fileObj.exists()) {
			fileObj.mkdir();
		}
		return val;
	}

	public static String getReportingHistoryHTMLfolderPath() {
		String val = ROOT + "/" + REPORTING_HISTORY_FOLDER_NAME + "/" + REPORTING_HISTORY_FOLDER_NAME_HTML;
		// Creating the Result Folder in case it is not already present
		File fileObj = new File(val);
		if (!fileObj.exists()) {
			fileObj.mkdir();
		}
		return val;
	}

	public static String getResourcesFolderPath() {
		String val = ROOT + "/" + RESOURCE_FOLDER_NAME_MAIN;
		return val;
	}

	public static String getJavaPackagePath() {
		String val = ROOT + "/" + "src/main/java";
		return val;
	}

	public static String getLogoUrl() {
		if(HTML_REPORT_LOGO_URL==null || "".equals(HTML_REPORT_LOGO_URL)) {
			return "https://www.infinitcare.com/wp-content/uploads/2016/08/Dummy-logo.png";
		}
		return HTML_REPORT_LOGO_URL;
	}

}
