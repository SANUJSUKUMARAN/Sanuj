package com.hs.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertyReader
{  
	Properties prop = null;
	FileInputStream in = null;
	
	/**
	 * Constructor to load the property file
	 * @throws IOException
	 */
	public PropertyReader() throws IOException
	{
		 String filepath =  System.getProperty("user.dir") + "\\"+ "src\\main\\java\\com\\hs\\config";
		
		 in = new FileInputStream(filepath+"\\"+"config.properties");
         prop = new Properties();	
		 prop.load(in);
	}	
	
	/**
	 * This method will return the property value for key
	 * @param Key
	 * @return
	 * @throws IOException 
	 */
	public String getProperty(String Key) throws IOException
	{
	  String value = prop.getProperty(Key);
	  in.close();
	  return value;
	}

}
