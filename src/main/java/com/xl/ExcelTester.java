package com.xl;

import org.testng.annotations.Test;

import com.reporting.Reporter;

public class ExcelTester {

	public static void main(String[] args) {
		String filePath = "D:\\eclipse-ws-2\\FW_Selenium4Upgrade\\src\\main\\java\\com\\xl\\Testing1.xlsx";
		ExcelManager tab = new ExcelManager(filePath, 0);

		System.out.println(tab.getColumnCount(0));
		System.out.println(tab.getRowCount());

		tab.setValue(0, 30, "Data1");
		tab.setValue(150, 5, "Data2");

		System.out.println(tab.getValue(0, 30));
		System.out.println(tab.getValue(150, 5));
	}

	String fileName1 = "C:/Users/shailendra.rajawat/git/iotronApex5/src/test/java/common/xlUtil/Testing1.xlsx";
	String fileName2 = "C:/Users/shailendra.rajawat/git/iotronApex5/src/test/java/common/xlUtil/Testing2.xlsx";
	String fileName3 = "C:/Users/shailendra.rajawat/git/iotronApex5/src/test/java/common/xlUtil/Testing3.xlsx";
	ExcelManager tab1 = new ExcelManager(fileName1, "Sheet1");
	ExcelManager tab2 = new ExcelManager(fileName2, "Sheet1");
	ExcelManager tab3 = new ExcelManager(fileName3, "Sheet1");

	@Test
	private void thread1() {
		Reporter.INFO( "thread1");

		tab1.setValue(tab1.getRowCount(), 0, "1.1");

		tab1.setValue(tab1.getRowCount(), 0, "1.2");
	}

	@Test
	private void thread2() {
		Reporter.INFO( "thread2");

		tab2.setValue(tab2.getRowCount(), 0, "2.1");

		tab2.setValue(tab2.getRowCount(), 0, "2.2");
	}

	@Test
	private void thread3() {
		Reporter.INFO( "thread3");

		tab3.setValue(tab3.getRowCount(), 0, "3.1");

		tab3.setValue(tab3.getRowCount(), 0, "3.2");
	}

}
