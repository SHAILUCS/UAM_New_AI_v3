package com.json;

import java.util.Map;

import org.testng.annotations.Test;

import com.config.Config;
import com.reporting.Reporter;

public class JsonTester {

	static String jsonFilePath1=Config.ROOT+"/src/main/java/com/json/demo1.json";
	static String jsonFilePath2=Config.ROOT+"/src/main/java/com/json/demo2.json";
	
	public static void main(String[] args) {
		JSONManager json=new JSONManager("C:\\Users\\GWL\\git\\BlumTelehealthAutomation_v4\\app-config-devices.json","device1");
		/*
		 * json.put("k1.1", "v1.1"); json.put("k1.2", "v1.2");
		 */
		System.out.println(json.toHashMap() instanceof Map);
		System.out.println(json.toHashMap());
	}
	
	@Test
	private void thread1() {
		Reporter.INFO( "thread1");
		JSONManager json=new JSONManager(jsonFilePath1);
		json.put("k1.1", "v1.1");
		json.put("k1.2", "v1.2");
		System.out.println(json.getStr("k1.1"));
	}
	
	@Test
	private void thread2() {
		Reporter.INFO( "thread2");
		JSONManager json=new JSONManager(jsonFilePath1);
		json.put("k2.1", "v2.1");
		json.put("k2.2", "v2.2");
		System.out.println(json.getStr("k2.1"));
	}
	
	@Test
	private void thread3() {
		Reporter.INFO( "thread3");
		JSONManager json=new JSONManager(jsonFilePath1);
		json.put("k3.1", "v3.1");
		json.put("k3.2", "v3.2");
		System.out.println(json.getStr("k3.1"));
		
	}
	
	@Test
	private void thread4() {
		Reporter.INFO( "thread4");
		JSONManager json=new JSONManager(jsonFilePath2);
		json.put("k4.1", "v4.1");
		json.put("k4.2", "v4.2");
		System.out.println(json.getStr("k4.1"));
	}
	
	@Test
	private void thread5() {
		Reporter.INFO( "thread5");
		JSONManager json=new JSONManager(jsonFilePath2);
		json.put("k5.1", "v5.1");
		json.put("k5.2", "v5.2");
		System.out.println(json.getStr("k5.1"));
	}
}
