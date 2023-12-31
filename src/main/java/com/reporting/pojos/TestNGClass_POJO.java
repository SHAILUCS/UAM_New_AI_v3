package com.reporting.pojos;

import java.util.ArrayList;
import java.util.List;

import com.reporting.STATUS;

public class TestNGClass_POJO {

	private String className;
	private String status;

	public TestNGClass_POJO(String className) {
		this.className = className;
		this.status = STATUS.PASS.value;
	}

	private List<Scenario> methodsList;

	public void addMethod(Scenario method) {

		if (methodsList == null) {
			initMethodsList();
		}

		methodsList.add(method);
	}

	public List<Scenario> getMethodsList() {
		return methodsList;
	}

	public void initMethodsList() {
		this.methodsList = new ArrayList<Scenario>();
	}

	/*
	 * private List<TestNGMethods_POJO> methodsList;
	 * 
	 * public void addMethod(Scenario test) {
	 * 
	 * if(methodsList == null) { initMethodsList(); }
	 * 
	 * methodsList.add(new TestNGMethods_POJO(test)); }
	 * 
	 * public List<TestNGMethods_POJO> getMethodsList() { return methodsList; }
	 * 
	 * public void initMethodsList() { this.methodsList = new
	 * ArrayList<TestNGMethods_POJO>(); }
	 */

	public String getClassName() {
		return className;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

}
