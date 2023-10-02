package com.util;

import com.config.Config;

public class PropTester {
	public static void main(String[] args) {
		String filePath = Config.ROOT + "/src/main/java/com/util/demo.properties";

		System.out.println(Prop.getValue(filePath, "key1"));
		System.out.println(Prop.getValue(filePath, "key2"));
		
	}
}
