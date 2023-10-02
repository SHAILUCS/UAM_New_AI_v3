package com.selenium;

import com.reporting.Reporter;

public class Custom_ExceptionHandler extends Exception{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * In case test data also needs to be displayed in report,
	 * pass the stringified test data in this method
	 * */
	public Custom_ExceptionHandler(Exception e,String data) {
		Reporter.ERROR(e.getMessage() + "<br/> Test Data:-<br/> ["+data+"] <br/> Stack Trace :- <b>"+getReadableStackTrace(e)+"</b>");
		e.printStackTrace();
	}
	
	public Custom_ExceptionHandler(Exception e) {
		Reporter.ERROR(e.getMessage() + "<br/> Stack Trace :- <b>"+getReadableStackTrace(e)+"</b>");
		e.printStackTrace();
	}

	private String getReadableStackTrace(Exception e) {
		String readableStackTrace=null;
		for (StackTraceElement elem : e.getStackTrace()) {
			readableStackTrace=readableStackTrace+"<br/>"+elem.toString();
		}
		return readableStackTrace;
	}

}
