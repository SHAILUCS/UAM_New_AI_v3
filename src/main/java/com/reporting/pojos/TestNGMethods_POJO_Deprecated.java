package com.reporting.pojos;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import com.selenium.Custom_ExceptionHandler;

public class TestNGMethods_POJO_Deprecated {
	private String desc;
	private String methodName;
	private String status;
	private String group;
	private String parameters;
	private String start;
	private String end;
	private String elapsed;
	private String locale;

	public TestNGMethods_POJO_Deprecated(Scenario test) {
		this.desc = test.getScenarioName();
		this.methodName = test.getTestNG_MethodName();
		this.status = test.getStatus();
		this.group = test.getGroup();
		this.parameters = test.getParameters();
		this.start = convertToHHMMSS(test.getStartTime_Scenario_InMillSec());
		this.end = convertToHHMMSS(test.getEndTime_Scenario_InMillSec());
		this.elapsed = test.getExecutionTime_Scenario();
		this.locale = test.getLocale();
	}
	
	public String getStartTime_Scenario_InHHMMSS() {
		return start;
	}

	public String getEndTime_Scenario_InHHMMSS() {
		return end;
	}

	public String getExecutionTime_Scenario() {
		return elapsed;
	}
	
	public String getScenarioName() {
		return desc;
	}

	public String getTestNG_MethodName() {
		return methodName;
	}

	public String getStatus() {
		return status;
	}

	public String getGroup() {
		return group;
	}
	
	public String getParameters() {
		return parameters;
	}

	public String getLocale() {
		return locale;
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
