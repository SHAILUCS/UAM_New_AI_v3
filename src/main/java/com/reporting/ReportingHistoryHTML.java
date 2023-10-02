package com.reporting;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;

import org.apache.commons.io.IOUtils;

import com.config.FrameworkConfig;
import com.selenium.Custom_ExceptionHandler;

/**
 * @author shailendra.rajawat This class will generate interactable html pages
 *         which will display the old reports of Test Runs. Features: 1. It has
 *         pagination, latest report displays on first page 2. Quick view
 *         displays the Executed tests with status 3. Links lets you open the
 *         report in new tab 4. You can use keyboard to scroll up down and to
 *         navigate to next/prev page
 */
public class ReportingHistoryHTML {

	private final static SimpleDateFormat FOLDER_NAME_DATE_FORMAT = new SimpleDateFormat("yyyyMMMdd_HHmmss");
	private final static SimpleDateFormat TABLE_DISPLAY_DATE_FORMAT = new SimpleDateFormat("E MMM dd HH:mm:ss z yyyy");
	private final static SimpleDateFormat HEADER_DISPLAY_DATE_FORMAT = new SimpleDateFormat("E MMM dd yyyy");
	private final static String PROJECT_NAME = "<!-- PROJECT -->";
	private final static String PICKER_QUICK_ACCESS_START_PLACEHOLDER = "<!-- PICKER0_START -->";
	private final static String PICKER_QUICK_ACCESS_END_PLACEHOLDER = "<!-- PICKER0_END -->";
	private final static String PICKER_DASHBOARD_START_PLACEHOLDER = "<!-- PICKER1_START -->";
	private final static String PICKER_DASHBOARD_END_PLACEHOLDER = "<!-- PICKER1_END -->";
	private final static String ROW_PLACEHOLDER = "<!-- INSERT_ROW -->";
	private final static String TOTAL_ROWS_PLACEHOLDER = "<!-- TOTAL ROWS -->";
	private final static String ROWS_PER_PAGE_PLACEHOLDER = "<!-- PAGE ROWS -->";
	private final static String PREV_PAGINATION_TOP = "<!-- PREV -->";
	private final static String NEXT_PAGINATION_TOP = "<!-- NEXT -->";
	private final static String PAGINATION_PLACEHOLDER_TOP = "<!-- INSERT_TOP_PAGINATION -->";
	private final static String PAGINATION_PLACEHOLDER_BOTTOM = "<!-- INSERT_BOTTOM_PAGINATION -->";
	private static String REPORTING_HISTORY_FOLDER_PATH = FrameworkConfig.getReportingHistoryFolderPath();
	private static String REPORTING_HISTORY_FOLDER_PATH_HTML = FrameworkConfig.getReportingHistoryHTMLfolderPath();
	private static String REDISGN_TEMPLATE_PATH = FrameworkConfig.getHistoryReportRedesignTemplateFilePath();
	private static Map<String, List<String>> DATE_WISE_PAGE_NUM = new HashMap<String, List<String>>();
	private static Map<String, List<String>> PAGE_WISE_DATE = new HashMap<String, List<String>>();

	/**
	 * Code to create trend chart in the reporting history html pages
	 * 
	 * @author Shailendra
	 * @date Jul 18 203
	 */
	private final static String COUNT_PLACEHOLDER_CHART = "<!-- CHART-HOW-MANY-EXECUTIONS-To-DISPLAY -->";
	private final static String DATE_ARRAY_PLACEHOLDER_CHART = "<!-- DATE-ARRAY -->";
	private final static String PASS_ARRAY_PLACEHOLDER_CHART = "<!-- PASS-ARRAY -->";
	private final static String FAIL_ARRAY_PLACEHOLDER_CHART = "<!-- FAIL-ARRAY -->";
	private final static String SKIP_ARRAY_PLACEHOLDER_CHART = "<!-- SKIP-ARRAY -->";
	private final static String TOTAL_ARRAY_PLACEHOLDER_CHART = "<!-- TOTAL-ARRAY -->";
	private final static String TOTAL_START_PLACEHOLDER_CHART = "<!-- TOTAL-CHART-START -->";
	private final static String TOTAL_END_PLACEHOLDER_CHART = "<!-- TOTAL-CHART-END -->";
	private final static String PASS_START_PLACEHOLDER_CHART = "<!-- PASS-CHART-START -->";
	private final static String PASS_END_PLACEHOLDER_CHART = "<!-- PASS-CHART-END -->";
	private final static String FAIL_START_PLACEHOLDER_CHART = "<!-- FAIL-CHART-START -->";
	private final static String FAIL_END_PLACEHOLDER_CHART = "<!-- FAIL-CHART-END -->";
	private final static String SKIP_START_PLACEHOLDER_CHART = "<!-- SKIP-CHART-START -->";
	private final static String SKIP_END_PLACEHOLDER_CHART = "<!-- SKIP-CHART-END -->";
	private final static String CHART_DEFAULT_VISIBILITY = "<!-- CHART_VISIBILITY -->";
	private static List<Long> PASS_ARR_CHART = new ArrayList<Long>();
	private static List<Long> FAIL_ARR_CHART = new ArrayList<Long>();
	private static List<Long> SKIP_ARR_CHART = new ArrayList<Long>();
	private static List<Long> TOTAL_ARR_CHART = new ArrayList<Long>();

	private final static String UNIQUE_DATES = "<!-- UNIQUE DATES -->";
	private final static String DATE_WISE_PAGE = "<!-- DATE WISE PAGE -->";
	private final static String LOGO = "<!-- APPLICATION UNDER TEST LOGO URL -->";

	static void manageHTML() {
		// deleteOlderHTMLFile();
		Reporter.deleteFolderContentRecursively(new File(REPORTING_HISTORY_FOLDER_PATH_HTML),
				FrameworkConfig.REPORTING_HISTORY_FOLDER_NAME_HTML);
		createHTML();
	}

	private static void createHTML() {
		try {

			// CHART
			maintainChartTotalPassFailCounts();

			// Page wise dates
			maintainPageWiseUniqueDatesData();

			String tempFolderName, customReportPath, extentReportPath, karateReportPath;
			String reportBase = new String(Files.readAllBytes(Paths.get(REDISGN_TEMPLATE_PATH)));

			List<Date> foldersNameList = ReportingHistoryManager_Html.getFoldersNameList();
			Collections.sort(foldersNameList);

			// Getting how many folders are present then calculating the total pages to show
			// them
			int foldersCount = foldersNameList.size();
			int totalPages = 1;
			int diff = FrameworkConfig.getReportinghistoryfolderperpage();
			if (foldersCount > diff) {
				totalPages = (foldersCount + diff - 1) / diff;
			}

			// Generating Pagination html code
			String data = " ";
			for (int page = 1; page <= totalPages; page++) {
				data = data + "<span class='pagination'><a href='page" + page + ".html'>" + page + "</a></span>";
			}

			reportBase = reportBase.replaceFirst(TOTAL_ROWS_PLACEHOLDER, foldersCount + TOTAL_ROWS_PLACEHOLDER);
			reportBase = reportBase.replaceFirst(ROWS_PER_PAGE_PLACEHOLDER,
					FrameworkConfig.getReportinghistoryfolderperpage() + ROWS_PER_PAGE_PLACEHOLDER);
			reportBase = reportBase.replaceFirst(PAGINATION_PLACEHOLDER_TOP, data + PAGINATION_PLACEHOLDER_TOP);
			reportBase = reportBase.replaceFirst(PAGINATION_PLACEHOLDER_BOTTOM, data + PAGINATION_PLACEHOLDER_BOTTOM);

			String paginationData = data;

			diff = diff - 1;
			int startDiff = 0, start = foldersCount, end = 0, srNum = 1;
			for (int page = 1; page <= totalPages; page++) {

				String reportIn = reportBase;
				start = start - startDiff - 1;
				startDiff = diff;
				end = (start - diff) <= 0 ? 0 : (start - diff);
				for (int i = start; i >= end; i--) {

					tempFolderName = FOLDER_NAME_DATE_FORMAT.format(foldersNameList.get(i));
					customReportPath = REPORTING_HISTORY_FOLDER_PATH + "/" + tempFolderName + "/"
							+ FrameworkConfig.HTML_REPORT_FOLDER_NAME + "/" + FrameworkConfig.HTML_REPORT_FILE_NAME_CUSTOM;
					extentReportPath = REPORTING_HISTORY_FOLDER_PATH + "/" + tempFolderName + "/"
							+ FrameworkConfig.HTML_REPORT_FOLDER_NAME + "/" + FrameworkConfig.HTML_REPORT_FILE_NAME_EXTENT;
					karateReportPath = REPORTING_HISTORY_FOLDER_PATH + "/" + tempFolderName + "/"
							+ FrameworkConfig.HTML_REPORT_FOLDER_NAME_KARATE + "/" + FrameworkConfig.HTML_REPORT_FILE_NAME_KARATE;

					reportIn = reportIn.replaceFirst(ROW_PLACEHOLDER,
							"<tr>" + "<td>" + srNum++ + "</td>" + "<td>" + TABLE_DISPLAY_DATE_FORMAT.format(foldersNameList.get(i)) + "</td>" + "<td>"
									+ Matcher.quoteReplacement(
											getDashboardContent(customReportPath))
									+ "</td>" + "<td width='50%'> "
									+ Matcher.quoteReplacement(
											getQuickViewContent(customReportPath))
									+ "</td>" + "<td>"
									+ Matcher.quoteReplacement(
											getLinks(customReportPath, extentReportPath,karateReportPath))
									+ "</td>" + "</tr>" + ROW_PLACEHOLDER);

				}
				String prev = page == 1 ? ""
						: "<span class='pagination'><a href='page" + (page - 1) + ".html'> <span class='prev'> &#8592 </span></a></span>";
				String next = page == totalPages ? ""
						: "<span class='pagination'><a href='page" + (page + 1) + ".html'> <span class='next'> &#8594</span></a></span>";
				String currentPagePagination = data.replace("<a href='page" + page + ".html'>" + page + "</a>","<a href='page" + page + ".html'><span class='currentPage'>" + page + "</span></a>");
				reportIn = reportIn.replace(paginationData, currentPagePagination);
				reportIn = reportIn.replaceAll(PROJECT_NAME, FrameworkConfig.PROJECT);
				reportIn = reportIn.replaceAll(PREV_PAGINATION_TOP, prev);
				reportIn = reportIn.replaceAll(NEXT_PAGINATION_TOP, next);

				/**
				 * Code to create trend chart in the reporting history html pages
				 * 
				 * @author Shailendra
				 * @date Jul 18 203
				 */
				reportIn = reportIn.replaceAll(COUNT_PLACEHOLDER_CHART,
						FrameworkConfig.CHART_LAST_EXECUTIONS_COUNT + "");
				reportIn = reportIn.replaceAll(DATE_ARRAY_PLACEHOLDER_CHART, getDateArray_ForChart());
				reportIn = reportIn.replaceAll(TOTAL_ARRAY_PLACEHOLDER_CHART, getTotalArray_ForChart());
				reportIn = reportIn.replaceAll(PASS_ARRAY_PLACEHOLDER_CHART, getPassArray_ForChart());
				reportIn = reportIn.replaceAll(FAIL_ARRAY_PLACEHOLDER_CHART, getFailArray_ForChart());
				reportIn = reportIn.replaceAll(SKIP_ARRAY_PLACEHOLDER_CHART, getSkipArray_ForChart());
				reportIn = reportIn.replaceAll(CHART_DEFAULT_VISIBILITY, FrameworkConfig.CHART_DEFAULT_VISIBILITY + "");

				reportIn = reportIn.replaceAll(UNIQUE_DATES, PAGE_WISE_DATE.get(page + "").toString());
				reportIn = reportIn.replaceAll(DATE_WISE_PAGE, generateDateWisePageDropdownHTML(DATE_WISE_PAGE_NUM));

				reportIn = reportIn.replaceAll(LOGO, FrameworkConfig.getLogoUrl());
				
				Files.write(Paths.get(REPORTING_HISTORY_FOLDER_PATH_HTML + "/page" + page + ".html"), reportIn.getBytes(),
						StandardOpenOption.CREATE);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static String generateDateWisePageDropdownHTML(Map<String, List<String>> map) {
		String html = "<select name='DateWisePages' id='DateWisePages' onchange='loadPageFromDropdown()'>"
				+ "<option value='1'>Date : [Pages]</option>";

		try {
			List<Date> keysDate = new ArrayList<>();
			// Sorting the data datewise
			
			for (String date : map.keySet()) {
				keysDate.add(HEADER_DISPLAY_DATE_FORMAT.parse(date));
			}
			Collections.sort(keysDate, Collections.reverseOrder());
			
			for (Date date : keysDate) {
				String dt = HEADER_DISPLAY_DATE_FORMAT.format(date);
				List<String> list = map.get(dt);
				html = html + "<option value=" + list.get(0) + ">" + dt + " :- " + list.toString() + "</option>";
			}
		} catch (Exception e) {
		}

		return html + "</select>";
	}

	private static void maintainPageWiseUniqueDatesData() {
		try {

			List<Date> foldersNameList = ReportingHistoryManager_Html.getFoldersNameList();
			Collections.sort(foldersNameList);

			// Getting how many folders are present then calculating the total pages to show
			// them
			int foldersCount = foldersNameList.size();
			int totalPages = 1;
			int diff = FrameworkConfig.getReportinghistoryfolderperpage();
			if (foldersCount > diff) {
				totalPages = (foldersCount + diff - 1) / diff;
			}

			diff = diff - 1;
			int startDiff = 0, start = foldersCount, end = 0;
			for (int page = 1; page <= totalPages; page++) {

				List<Date> datesOfAPage = new ArrayList<>();

				start = start - startDiff - 1;
				startDiff = diff;
				end = (start - diff) <= 0 ? 0 : (start - diff);
				for (int i = start; i >= end; i--) {
					datesOfAPage.add(foldersNameList.get(i));
				}

				List<String> l = getUniqueDates(datesOfAPage);
				PAGE_WISE_DATE.put(page + "", l);
				maintainDateWisePageNumber(l, page);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private static String getLinks(String customReportPath, String extentReportPath, String karateReportPath) {
		String links = "";
		try {
			File f = new File(customReportPath);
			/*
			 * We need href = '../2019Apr22_013641/Result/Report_Redesign_Template.html#1'
			 */
			if (f.exists()) {
				String href = customReportPath
						.substring(customReportPath.indexOf(FrameworkConfig.REPORTING_HISTORY_FOLDER_NAME)
								+ FrameworkConfig.REPORTING_HISTORY_FOLDER_NAME.length());
				links += " <a href='.." + href + "' target ='_blank' style='color:blue;'>Custom-report</a>";
			}

			f = new File(extentReportPath);
			if (f.exists()) {
				String href = extentReportPath
						.substring(extentReportPath.indexOf(FrameworkConfig.REPORTING_HISTORY_FOLDER_NAME)
								+ FrameworkConfig.REPORTING_HISTORY_FOLDER_NAME.length());
				links += " <br/><a href='.." + href + "' target ='_blank' style='color:blue;'>Extent-report</a>";
			}
			
			f = new File(karateReportPath);
			if (f.exists()) {
				String href = karateReportPath
						.substring(karateReportPath.indexOf(FrameworkConfig.REPORTING_HISTORY_FOLDER_NAME)
								+ FrameworkConfig.REPORTING_HISTORY_FOLDER_NAME.length());
				links += " <br/><a href='.." + href + "' target ='_blank' style='color:blue;'>Karate-api</a>";
				href = karateReportPath.substring(karateReportPath.indexOf(FrameworkConfig.REPORTING_HISTORY_FOLDER_NAME) + FrameworkConfig.REPORTING_HISTORY_FOLDER_NAME.length(),karateReportPath.indexOf(FrameworkConfig.HTML_REPORT_FILE_NAME_KARATE))+"karate-timeline.html";
				links += " <br/><a href='.." + href + "' target ='_blank' style='color:blue;'>Karate-timeline</a>";
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return links;
	}

	public static String getQuickViewContent(String customReportPath) {
		String quickAccessContent = "";
		try {
			File f = new File(customReportPath);
			String wholeHtmlContent = IOUtils.toString(new FileInputStream(f));
			int pickerStart = wholeHtmlContent.indexOf(PICKER_QUICK_ACCESS_START_PLACEHOLDER);
			int pickerEnd = wholeHtmlContent.indexOf(PICKER_QUICK_ACCESS_END_PLACEHOLDER);
			if (pickerStart > 0) {
				quickAccessContent = "<div class='quickView'>" + wholeHtmlContent.substring(pickerStart, pickerEnd)
						+ "</div>";
			} else {
				quickAccessContent = "<iframe src='" + customReportPath + "#1' width='99%'></iframe> ";
			}

		} catch (FileNotFoundException e) {
			// e.printStackTrace();
		} catch (IOException e) {
			// e.printStackTrace();
		}
		return quickAccessContent;
	}

	private static void maintainChartTotalPassFailCounts() {
		try {
			List<Date> foldersNameList = ReportingHistoryManager_Html.getFoldersNameList();
			Collections.sort(foldersNameList, Collections.reverseOrder());
			for (Date folder : foldersNameList) {
				String tempFolderName = FOLDER_NAME_DATE_FORMAT.format(folder);
				String NEWcustomReportPath = REPORTING_HISTORY_FOLDER_PATH + "/" + tempFolderName + "/"
						+ FrameworkConfig.HTML_REPORT_FOLDER_NAME + "/" + FrameworkConfig.HTML_REPORT_FILE_NAME_CUSTOM;
				File f = new File(NEWcustomReportPath);
				String wholeHtmlContent = IOUtils.toString(new FileInputStream(f));
				/** Chart Logic @author Shailendra Jul-18-2023 */
				int pickerStart = wholeHtmlContent.indexOf(TOTAL_START_PLACEHOLDER_CHART);
				int pickerEnd = wholeHtmlContent.indexOf(TOTAL_END_PLACEHOLDER_CHART);
				long temp = pickerStart > 0 ? Long.parseLong(
						wholeHtmlContent.substring(pickerStart + TOTAL_START_PLACEHOLDER_CHART.length(), pickerEnd))
						: 0;
				TOTAL_ARR_CHART.add(temp);

				pickerStart = wholeHtmlContent.indexOf(PASS_START_PLACEHOLDER_CHART);
				pickerEnd = wholeHtmlContent.indexOf(PASS_END_PLACEHOLDER_CHART);
				temp = pickerStart > 0 ? Long.parseLong(
						wholeHtmlContent.substring(pickerStart + PASS_START_PLACEHOLDER_CHART.length(), pickerEnd)) : 0;
				PASS_ARR_CHART.add(temp);

				pickerStart = wholeHtmlContent.indexOf(FAIL_START_PLACEHOLDER_CHART);
				pickerEnd = wholeHtmlContent.indexOf(FAIL_END_PLACEHOLDER_CHART);
				temp = pickerStart > 0 ? Long.parseLong(
						wholeHtmlContent.substring(pickerStart + FAIL_START_PLACEHOLDER_CHART.length(), pickerEnd)) : 0;
				FAIL_ARR_CHART.add(temp);

				pickerStart = wholeHtmlContent.indexOf(SKIP_START_PLACEHOLDER_CHART);
				pickerEnd = wholeHtmlContent.indexOf(SKIP_END_PLACEHOLDER_CHART);
				temp = pickerStart > 0 ? Long.parseLong(
						wholeHtmlContent.substring(pickerStart + SKIP_START_PLACEHOLDER_CHART.length(), pickerEnd)) : 0;
				SKIP_ARR_CHART.add(temp);
			}

		} catch (FileNotFoundException e) {
			// e.printStackTrace();
		} catch (IOException e) {
			// e.printStackTrace();
		}
	}

	public static String getDashboardContent(String customReportPath) {
		String dashBoardContent = "";
		try {
			File f =  new File(customReportPath);
			String wholeHtmlContent = IOUtils.toString(new FileInputStream(f));
			int pickerStart = wholeHtmlContent.indexOf(PICKER_DASHBOARD_START_PLACEHOLDER);
			int pickerEnd = wholeHtmlContent.indexOf(PICKER_DASHBOARD_END_PLACEHOLDER);
			dashBoardContent = wholeHtmlContent.substring(pickerStart, pickerEnd);

			/** Chart Logic @author Shailendra Jul-18-2023 */
			pickerStart = wholeHtmlContent.indexOf(TOTAL_START_PLACEHOLDER_CHART);
			pickerEnd = wholeHtmlContent.indexOf(TOTAL_END_PLACEHOLDER_CHART);
			long temp = pickerStart > 0
					? Long.parseLong(
							wholeHtmlContent.substring(pickerStart + TOTAL_START_PLACEHOLDER_CHART.length(), pickerEnd))
					: 0;
			TOTAL_ARR_CHART.add(temp);

			pickerStart = wholeHtmlContent.indexOf(PASS_START_PLACEHOLDER_CHART);
			pickerEnd = wholeHtmlContent.indexOf(PASS_END_PLACEHOLDER_CHART);
			temp = pickerStart > 0
					? Long.parseLong(
							wholeHtmlContent.substring(pickerStart + PASS_START_PLACEHOLDER_CHART.length(), pickerEnd))
					: 0;
			PASS_ARR_CHART.add(temp);

			pickerStart = wholeHtmlContent.indexOf(FAIL_START_PLACEHOLDER_CHART);
			pickerEnd = wholeHtmlContent.indexOf(FAIL_END_PLACEHOLDER_CHART);
			temp = pickerStart > 0
					? Long.parseLong(
							wholeHtmlContent.substring(pickerStart + FAIL_START_PLACEHOLDER_CHART.length(), pickerEnd))
					: 0;
			FAIL_ARR_CHART.add(temp);

			pickerStart = wholeHtmlContent.indexOf(SKIP_START_PLACEHOLDER_CHART);
			pickerEnd = wholeHtmlContent.indexOf(SKIP_END_PLACEHOLDER_CHART);
			temp = pickerStart > 0
					? Long.parseLong(
							wholeHtmlContent.substring(pickerStart + SKIP_START_PLACEHOLDER_CHART.length(), pickerEnd))
					: 0;
			SKIP_ARR_CHART.add(temp);

		} catch (FileNotFoundException e) {
			// e.printStackTrace();
		} catch (IOException e) {
			// e.printStackTrace();
		}
		return dashBoardContent;
	}

	private static String getDateArray_ForChart() {
		List<Date> foldersNameList = ReportingHistoryManager_Html.getFoldersNameList();
		Collections.sort(foldersNameList, Collections.reverseOrder());
		String dateArr = "";
		for (int i = 0; i < foldersNameList.size(); i++) {
			dateArr = dateArr + "\"" + foldersNameList.get(i).toString() + "\"" + ",";
		}
		dateArr = "[" + dateArr.substring(0, dateArr.length() - 1) + "]";
		return dateArr;
	}

	private static String getTotalArray_ForChart() {
		String totalArr = "";
		for (int i = 0; i < TOTAL_ARR_CHART.size(); i++) {
			totalArr = totalArr + TOTAL_ARR_CHART.get(i) + ",";
		}
		totalArr = "[" + totalArr.substring(0, totalArr.length() - 1) + "]";
		return totalArr;
	}

	private static String getPassArray_ForChart() {
		String arr = "";
		for (int i = 0; i < PASS_ARR_CHART.size(); i++) {
			arr = arr + PASS_ARR_CHART.get(i) + ",";
		}
		arr = "[" + arr.substring(0, arr.length() - 1) + "]";
		return arr;
	}

	private static String getFailArray_ForChart() {
		String arr = "";
		for (int i = 0; i < FAIL_ARR_CHART.size(); i++) {
			arr = arr + FAIL_ARR_CHART.get(i) + ",";
		}
		arr = "[" + arr.substring(0, arr.length() - 1) + "]";
		return arr;
	}

	private static String getSkipArray_ForChart() {
		String arr = "";
		for (int i = 0; i < SKIP_ARR_CHART.size(); i++) {
			arr = arr + SKIP_ARR_CHART.get(i) + ",";
		}
		arr = "[" + arr.substring(0, arr.length() - 1) + "]";
		return arr;
	}

	private static List<String> getUniqueDates(List<Date> inputList) {
		List<String> outputList = new ArrayList<String>();

		try {
			// First getting only the date part, then putting in set for keeping only unique
			// values
			Set<Date> set = new HashSet<>();
			for (Date date : inputList) {
				Calendar cal = Calendar.getInstance();
				cal.setTime(date);
				cal.set(Calendar.HOUR_OF_DAY, 0);
				cal.set(Calendar.MINUTE, 0);
				cal.set(Calendar.SECOND, 0);
				cal.set(Calendar.MILLISECOND, 0);
				set.add(cal.getTime());
			}
			// System.out.println(set);

			// Now converting the set to list so that we can sort it
			List<Date> listDate = new ArrayList<Date>(set);
			Collections.sort(listDate, Collections.reverseOrder());
			// System.out.println(listDate);

			// Finally formatting the date to the format to be shown on the report
			for (Date temp : listDate) {
				outputList.add(HEADER_DISPLAY_DATE_FORMAT.format(temp));
			}
			// System.out.println(outputList);
		} catch (Exception e) {
			new Custom_ExceptionHandler(e);
		}
		return outputList;
	}

	private static void maintainDateWisePageNumber(List<String> l, int page) {
		try {
			for (String temp : l) {
				if (DATE_WISE_PAGE_NUM.containsKey(temp)) {
					List<String> list = DATE_WISE_PAGE_NUM.get(temp);
					list.add(page + "");
					DATE_WISE_PAGE_NUM.put(temp, list);
				} else {
					List<String> list = new ArrayList<String>();
					list.add(page + "");
					DATE_WISE_PAGE_NUM.put(temp, list);
				}
				// System.out.println(dateWisePageNum);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws FileNotFoundException, IOException, ParseException {
		manageHTML();

	}

}
