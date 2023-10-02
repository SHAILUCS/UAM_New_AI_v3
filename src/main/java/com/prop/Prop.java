package com.prop;

import java.io.FileReader;
import java.util.Properties;

import org.testng.Assert;

import com.selenium.Custom_ExceptionHandler;

public class Prop {
	
	
	public static synchronized boolean containsKey(String path, String key) {
		try {
			FileReader reader = new FileReader(path);
			Properties p = new Properties();
			p.load(reader);
			return p.containsKey(key);
		} catch (Exception e) {
			new Custom_ExceptionHandler(e);
		}
		
		return false;
	}
	

	/**
	 * Call this static method to get the value from any property file by passing
	 * the file path and key
	 * 
	 * @return String value corresponding to the key
	 * @param path path of the .properties file
	 * @param key  whose value is needed
	 * @author Shailendra Jul 2023
	 */
	public static synchronized String getValue(String path, String key) {
		try {
			FileReader reader = new FileReader(path);
			Properties p = new Properties();
			p.load(reader);

			if (null == p.get(key)) {
				Assert.fail("Key '" + key + "' is not found in file " + path);
			}

			return (String) p.get(key);
		} catch (Exception e) {
			new Custom_ExceptionHandler(e);
		}
		return "PROP_ERROR";
	}
	
}
