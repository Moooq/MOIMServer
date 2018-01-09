package com.chat.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;

public class Utils {
	public String removeSuffix(String name){
		StringBuffer sb = new StringBuffer(name);
		int n = sb.indexOf("@", 0);
		if(n != -1){
			return sb.substring(0, n).toString();
		}else{
			return sb.toString();
		}
	}
}
