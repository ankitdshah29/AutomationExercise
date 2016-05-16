package com.automation.store.utils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @author Ankit Shah
 *
 *         This class is used to get the data from the properties file
 */
public class DataPropertyHelper {
	private Properties properties = null;

	public DataPropertyHelper() {
		InputStream is = null;
		try {
			properties = new Properties();
			is = getClass().getClassLoader().getResourceAsStream("data.properties");
			properties.load(is);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * Get the properties file from the key
	 * 
	 * @param key
	 * @return
	 */
	public String getProperty(String key) {
		return properties.getProperty(key);
	}

}
