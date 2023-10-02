package com.listener;

import java.util.ArrayList;

public enum TestNGKeys {
	parameters("parameters"),
	priority("priority"),
	parallel("parallel"),
	browserName("browserName"), 
	browserVersion("browserVersion"), 
	appType_Web_App("appType_Web_App"), 
	platform_Desktop_Mobile("platform_Desktop_Mobile"),
	osVersion("osVersion"),
	os_Desktop("os_Desktop"),
	deviceName_Mobile("deviceName_Mobile"),
	remoteURL("remoteURL"),
	environment("environment"),
	description("description"),
	suite("suite"),
	test("test"),
	className("className"),
	methodName("methodName"),
	group("group"),
	dependsOn("dependsOn"), 
	threadCount("threadCount"), 
	locale("locale"), 
	JSON_FILE_NAME("JSON_FILE_NAME"), 
	JSON_OBJECT_NAME("JSON_OBJECT_NAME");
	
	public String value;  
	private TestNGKeys(String value){  
		this.value=value;  
	} 
	public static ArrayList<String> getValues(){
		ArrayList<String> list=new ArrayList<>();
		for (TestNGKeys item : TestNGKeys.values()) {
			list.add(item.value);
		}
		return list;
	}
}
