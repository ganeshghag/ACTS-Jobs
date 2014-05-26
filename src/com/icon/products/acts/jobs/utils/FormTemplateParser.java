package com.icon.products.acts.jobs.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class FormTemplateParser {

	/**
	 * @param args
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 */
	public static void main(String[] args) throws FileNotFoundException, IOException {
		
		Map retMap = parseFormTemplate("form14");
		System.out.println(retMap);

	}
	
	public static Map<String, Map<String, String>> parseFormTemplate(String formTemplateName) throws FileNotFoundException, IOException{
		Map<String, Map<String, String>> retMap = new HashMap<String, Map<String, String>>();
		
		File dir = new File(".\\FormTemplates\\"+formTemplateName);
		if(!dir.exists())
				throw new RuntimeException("The dir with formTemplateName="+formTemplateName+" does not exist.");
		
		
		File[] sectionFiles = dir.listFiles();
		Arrays.sort(sectionFiles);
		for (int i = 0; i < sectionFiles.length; i++) {
			String sectionDir = sectionFiles[i].getName();
			String sectionName = sectionDir.split("-")[1];
			sectionName = sectionName.split("[.]")[0];
			
			Properties props = new Properties();
			props.load(new FileInputStream(".\\FormTemplates\\"+formTemplateName+"\\"+sectionDir));
			Map<String, String> secMap = new HashMap(props);
			
			retMap.put(sectionName,secMap);
		}
		
		return retMap;
	}

}
