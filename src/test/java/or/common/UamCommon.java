

package or.common;

	import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.config.Config;
import com.reporting.Reporter;
import com.reporting.STATUS;
import com.selenium.CustomExceptionHandler;
import com.selenium.ReactTable;
import com.selenium.WebPage;
import com.selenium.webdriver.DriverFactory;
import com.util.Util;

	/**
	 * this class will hold the objects common to Finance manager application
	 * 
	 * @author Shailendra 31-Aug-2020
	 */
	public class UamCommon {
		private WebPage com;
		private ReactTable rt;

		public UamCommon() {
			PageFactory.initElements(DriverFactory.getDriver(), this);
			com = new WebPage();
			rt = new ReactTable(reactTable);
		}

		public By reactTable = By.xpath("//div[contains(@class,'ReactTable')]");

		@FindBy(xpath = "//span[contains(@class,'left')]/..")
		public WebElement icon_PreviousPage;

		@FindBy(xpath = "//input[@aria-label='jump to page']")
		public WebElement txt_PageNumber;

		@FindBy(xpath = "//span[@class='-totalPages']")
		public WebElement data_TotalPages;

		@FindBy(xpath = "//select[@aria-label='rows per page']")
		public WebElement select_RowsPerPage;

		@FindBy(xpath = "//span[contains(@class,'right')]/..")
		public WebElement icon_NextPage;

		@FindBy(xpath = "//div[contains(@class,'ellipsis')]")
		public WebElement icon_Loader;

		@FindBy(xpath = "//div[.='No rows found']")
		public WebElement data_NoDataFound_Msg;
		
		@FindBy(xpath = "//div[.='Loading...']")
		public WebElement data_LoadingText;

		/**
		 * this method will verify the UI of pagination page objects
		 * 
		 * @author Shailendra 31-Aug-2020
		 */
		public void verifyPaginationUi() {
			Reporter.NODE("Pagination objects");
			com.isElementPresent(icon_PreviousPage, "icon_PreviousPage");
			com.isElementPresent(txt_PageNumber, "text_PageNumber");
			com.isElementPresent(select_RowsPerPage, "select_RowsPerPage");
			com.isElementPresent(data_TotalPages, "data_TotalPages");
			com.isElementPresent(icon_NextPage, "icon_NextPage");
		}

		/**
		 * This method will verify the passed column String data is sorted in Ascending
		 * order or not It will verify maximum 5 rows
		 * 
		 * @author Shailendra 04-Sep-2020
		 */
		public void verifyAscendingSort_Number(int colNum) {
			verifySort_Number(colNum, null);
		}

		/**
		 * This method will verify the passed column String data is sorted in Ascending
		 * order or not It will verify maximum 5 rows
		 * 
		 * @author Shailendra 04-Sep-2020
		 */
		public void verifyAscendingSort_String(int colNum) {
			verifySort_String(colNum, null);
		}

		/**
		 * This method will verify the passed column String data is sorted in Descending
		 * order or not It will verify maximum 5 rows
		 * 
		 * @author Shailendra 04-Sep-2020
		 */
		public void verifyDescendingSort_String(int colNum) {
			verifySort_String(colNum, Collections.reverseOrder());
		}

		/**
		 * This method will verify the passed column String data is sorted in Descending
		 * order or not It will verify maximum 5 rows
		 * 
		 * @author Shailendra 04-Sep-2020
		 */
		public void verifyDescendingSort_Number(int colNum) {
			verifySort_Number(colNum, Collections.reverseOrder());
		}

		/**
		 * This method will verify the passed column Date data is sorted in Ascending
		 * order or not It will verify maximum 5 rows
		 * 
		 * @author Shailendra 04-Sep-2020
		 */
		public void verifyAscendingSort_Date(int colNum) {
			verifySort_Date(colNum, null);
		}

		/**
		 * This method will verify the passed column Date data is sorted in Descending
		 * order or not It will verify maximum 5 rows
		 * 
		 * @author Shailendra 04-Sep-2020
		 */
		public void verifyDescendingSort_Date(int colNum) {
			verifySort_Date(colNum, Collections.reverseOrder());
		}

		/**
		 * Will verify the ascending and descending sorting on the passed String column
		 * 
		 * @author Shailendra 04-Sep-2020
		 */
		private void verifySort_Number(int colNum, Comparator<Object> comparator) {
			String sortType = "";

			int rows = rt.getRowCount();

			int counter = 5;
			if (rows < counter) {
				counter = rows;
			}

			List<BigDecimal> dataList = new ArrayList<BigDecimal>();

			for (int row = 2; row <= counter; row++) {
				String temp = rt.getCellText(row, colNum);
				dataList.add(Util.BD(temp));
			}

			List<BigDecimal> dataListSorted = new ArrayList<BigDecimal>();
			dataListSorted.addAll(dataList);

			if (comparator == null) {
				Collections.sort(dataListSorted);
				sortType = "Ascending";
			} else {
				Collections.sort(dataListSorted, comparator);
				sortType = "Descending";
			}

			boolean flag = false;
			for (int i = 0; i < dataList.size(); i++) {
				if (!dataListSorted.get(i).equals(dataList.get(i))) {
					flag = true;
					break;
				}
			}

			if (flag) {
				Reporter.FAIL(sortType + " Sort on Numbers failed, Expected data " + dataListSorted + " actual data " + dataList);
			} else {
				Reporter.PASS(sortType + " Sort on Numbers is working, Expected data " + dataListSorted
						+ " actual data " + dataList);
			}

		}

		/**
		 * Will verify the ascending and descending sorting on the passed String column
		 * 
		 * @author Shailendra 04-Sep-2020
		 */
		private void verifySort_String(int colNum, Comparator<Object> comparator) {
			String sortType = "";

			int rows = rt.getRowCount();

			int counter = 5;
			if (rows < counter) {
				counter = rows;
			}

			List<String> dataList = new ArrayList<String>();

			for (int row = 2; row <= counter; row++) {
				dataList.add(rt.getCellText(row, colNum));
			}

			List<String> dataListSorted = new ArrayList<String>();
			dataListSorted.addAll(dataList);

			if (comparator == null) {
				Collections.sort(dataListSorted);
				sortType = "Ascending";
			} else {
				Collections.sort(dataListSorted, comparator);
				sortType = "Descending";
			}

			boolean flag = false;
			for (int i = 0; i < dataList.size(); i++) {
				if (!dataListSorted.get(i).equals(dataList.get(i))) {
					flag = true;
					break;
				}
			}

			if (flag) {
				Reporter.FAIL(sortType + " Sort on Strings failed, Expected data " + dataListSorted + " actual data " + dataList);
			} else {
				Reporter.PASS(sortType + " Sort on Strings is working, Expected data " + dataListSorted
						+ " actual data " + dataList);
			}

		}

		/**
		 * Will verify the ascending and descending sorting on the passed Date column
		 * 
		 * @author Shailendra 04-Sep-2020
		 */
		private void verifySort_Date(int colNum, Comparator<Object> comparator) {

			com.waitForElementTobe_NotVisible(icon_Loader);

			String sortType = "";

			int rows = rt.getRowCount();

			int counter = 5;
			if (rows < counter) {
				counter = rows;
			}

			List<Date> dataList = new ArrayList<Date>();

			for (int row = 2; row <= counter; row++) {
				String data = rt.getCellText(row, colNum);
				dataList.add(Util.getDate_AsDate("dd/MM/yyyy", data));
			}

			List<Date> dataListSorted = new ArrayList<Date>();
			dataListSorted.addAll(dataList);

			if (comparator == null) {
				Collections.sort(dataListSorted);
				sortType = "Ascending";
			} else {
				Collections.sort(dataListSorted, comparator);
				sortType = "Descending";
			}

			boolean flag = false;
			for (int i = 0; i < dataList.size(); i++) {
				if (!dataListSorted.get(i).equals(dataList.get(i))) {
					flag = true;
					break;
				}
			}

			if (flag) {
				Reporter.FAIL(sortType + " Sort on Date failed, Expected data " + dataListSorted + " actual data " + dataList);
			} else {
				Reporter.PASS(sortType + " Sort on Date is working, Expected data " + dataListSorted
						+ " actual data " + dataList);
			}

		}

		/**
		 * Will verify the pagination buttons func
		 * 
		 * @author Shailendra 07-Sep-2020
		 */
		public void verifyPaginationFunc() {

			Reporter.NODE("Verifying the pagination func");

			// checking the prev icon is disabled
			com.verifyObjectIsDisabled(icon_PreviousPage, "icon_PreviousPage");

			// Getting the page count
			String pageCountData = com.getText(data_TotalPages);

			int pageCount = Integer.parseInt(pageCountData);

			if (pageCount > 1) {

				// Checking the next link, on clicking it, prev icon will be enabled and page
				// number will increase by 1
				String currentPageData = com.getAttribute(txt_PageNumber, "value");
				int currentPage_Before = Integer.parseInt(currentPageData);

				com.click(icon_NextPage, "icon_NextPage");
				com.wait(2);

				com.verifyObjectIsEnabled(icon_PreviousPage, "icon_PreviousPage");

				currentPageData = com.getAttribute(txt_PageNumber, "value");
				int currentPage_After = Integer.parseInt(currentPageData);

				if (currentPage_After == (currentPage_Before + 1)) {
					Reporter.PASS( "Current Page counter is increased by 1");
				} else {
					Reporter.FAIL( "Current Page counter is NOT increased by 1");
				}

				// Checking the Jump to page, by going to last page and checking the next button
				// is disabled.
				com.sendKeys("txt_PageNumber", txt_PageNumber, pageCountData, Keys.ENTER);
				com.wait(2);

				com.verifyObjectIsDisabled(icon_NextPage, "icon_NextPage");

				// checking the Prev page button and page number will decrease by 1
				currentPageData = com.getAttribute(txt_PageNumber, "value");
				currentPage_Before = Integer.parseInt(currentPageData);

				com.click(icon_PreviousPage, "icon_PreviousPage");
				com.wait(2);

				currentPageData = com.getAttribute(txt_PageNumber, "value");
				currentPage_After = Integer.parseInt(currentPageData);

				if (currentPage_After == (currentPage_Before - 1)) {
					Reporter.PASS( "Current Page counter is decreased by 1");
				} else {
					Reporter.FAIL( "Current Page counter is NOT decreased by 1");
				}

				// checking the rows per page func

				int rowCount_Before = rt.getRowCount();

				com.selectByVisibleText(select_RowsPerPage, "100 rows", true);
				com.wait(2);

				int rowCount_After = rt.getRowCount();

				if (rowCount_After > rowCount_Before) {
					Reporter.PASS( "Rows Per page dropdown is working fine, ROWS COUNT Before "
							+ rowCount_Before + " ROWS COUNT After " + rowCount_After);
				} else {
					Reporter.FAIL( "Rows Per page dropdown is NOT working fine, ROWS COUNT Before "
							+ rowCount_Before + " ROWS COUNT After " + rowCount_After);
				}

			} else {
				Reporter.FAIL( "Page does not have data for checking pagination functionality");
			}

		}

		/**
		 * This will click on the date field to open the calendar, then it will select
		 * the passed date value
		 * 
		 * @param text_Date WebElement/By object
		 * @param date      string should be in dd/MM/YYYY format only
		 * @author Shailendra 09-Sep-2020
		 */
		public void selectDate(Object text_Date, String date) {

			if (date == null || "".equals(date)) {
				return;
			}

			String sep = "/";
			By button_Next = By.xpath("//button[contains(.,'Next Month')]");
			By button_Prev = By.xpath("//button[contains(.,'Previous Month')]");
			By data_CurrentMonth = By.xpath("//div[contains(@class,'current-month')]");

			// Clicking on Date field, to open calendar
			com.click(text_Date);
			com.wait(.5);

			try {

				// From the passed date String, getting the numeric day, month, and year values
				int passedDay = Integer.parseInt(date.split(sep)[0]);
				int passedMonth = Integer.parseInt(date.split(sep)[1]) - 1;
				int passedYear = Integer.parseInt(date.split(sep)[2]);

				// Creating dynamic xpath to select day from visible calendar [month year]
				By day_Xpath = By.xpath(
						"//div[contains(@class,'day')][contains(@aria-label,'day')][not(contains(@class,'outside'))][.='"
								+ passedDay + "']");

				// reading the currently displayed default month and year from calendar
				String currentMonthFromCal = com.getText(data_CurrentMonth);

				// converting the String month and year values to number
				int currentMonthIndex = getMonthIndex(currentMonthFromCal.split(" ")[0]);
				int currentYear = Integer.parseInt(currentMonthFromCal.split(" ")[1]);

				// Now getting the difference in year to calculate how many clicks required on
				// Next icon
				if (passedYear > currentYear) {

					int yearDiff = passedYear - currentYear;

					if (yearDiff > 0) {

						int loopCount = (11 - currentMonthIndex) + 12 * (yearDiff - 1) + 1;

						for (int i = 0; i < loopCount; i++) {
							com.click(button_Next);
						}

					}
				} else {
					int yearDiff = currentYear - passedYear;

					if (yearDiff > 0) {

						int loopCount = (currentMonthIndex + 1) * yearDiff;

						for (int i = 0; i < loopCount; i++) {
							com.click(button_Prev);
						}

					}
				}

				// After selecting the passed year, getting the current shown month to calculate
				// clicks for going to passed month
				currentMonthFromCal = com.getText(data_CurrentMonth);
				currentMonthIndex = getMonthIndex(currentMonthFromCal.split(" ")[0]);

				if (passedMonth > currentMonthIndex) {
					int monthDiff = passedMonth - currentMonthIndex;
					if (monthDiff > 0) {
						for (int i = 0; i < monthDiff; i++) {
							com.click(button_Next);
						}
					}
				} else {
					int monthDiff = currentMonthIndex - passedMonth;
					if (monthDiff > 0) {
						for (int i = 0; i < monthDiff; i++) {
							com.click(button_Prev);
						}
					}
				}
				// Now we have reached to the desired month and year, selecting the valid day
				// from the shown calendar
				com.wait(1);
				com.click(day_Xpath, "Date [" + date + "]");

			} catch (Exception e) {
				new CustomExceptionHandler(e, date);
			}
		}

		/**
		 * This method returns the index of passed string month, used in selectDate
		 * method
		 * 
		 * @author Shailendra 09-Sep-2020
		 */
		private int getMonthIndex(String monthName) {
			switch (monthName) {
			case "January":
				return 0;

			case "February":
				return 1;

			case "March":
				return 2;

			case "April":
				return 3;

			case "May":
				return 4;

			case "June":
				return 5;

			case "July":
				return 6;

			case "August":
				return 7;

			case "September":
				return 8;

			case "October":
				return 9;

			case "November":
				return 10;

			case "December":
				return 11;

			default:
				Reporter.FAIL( "Invalid Input " + monthName + " to getMonthIndex method");
				return 0;
			}
		}

		/**
		 * This method will verify the passed success message is displayed or not with
		 * timeout in seconds
		 * 
		 * @author Shailendra 10-Sep-2020
		 */
		public String verifySuccessMessage(String msgTxt, long wait) {
			By xp = By.xpath("//div[@role='alert'][contains(.,'" + msgTxt + "')]");
			com.waitForElementTobe_Visible(xp, wait,
					"Success Message [" + msgTxt + "]");
			return com.getText(xp);
		}

		/**
		 * without timeout
		 * 
		 * @author adarsh 22 nov 2022
		 */
		public String verifySuccessMessage(String msgTxt) {
			return verifySuccessMessage(msgTxt, Config.WAIT_EXPLICIT);
		}
		
		/**
		 * This method will upload the passed file path
		 * 
		 * @author Shailendra 11-Sep-2020
		 */
		public void uploadFile(Object inputFileUploadObject, String absFilePath) {
			try {
				if (inputFileUploadObject instanceof By) {
					((JavascriptExecutor) DriverFactory.getDriver()).executeScript("arguments[0].className='';arguments[0].style='';",
							com.findElement((By) inputFileUploadObject));
				} else {
					((JavascriptExecutor) DriverFactory.getDriver()).executeScript("arguments[0].className='';arguments[0].style='';",
							(WebElement) inputFileUploadObject);
				}
				com.sendKeys(inputFileUploadObject, absFilePath);
			} catch (Exception e) {
				new CustomExceptionHandler(e, inputFileUploadObject.toString() + "|" + absFilePath);
			}
		}

	}
	

