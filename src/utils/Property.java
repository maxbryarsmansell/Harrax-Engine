package utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

import game.Main;

public class Property {
	
	/*
	 * Load a property with a given name from a given file.
	 * Return the property as a string.
	 */
	
	public static String loadProperty(String propertyName, String fileName)
	{
		Properties property = new Properties();
		InputStream input = null;
		String value = null;
		try {
			
			System.out.println("Loading the property...\"" + propertyName + "\", from...\"" + fileName + ".properties\"");
			input = new FileInputStream("res/properties/" + fileName + ".properties");
			property.load(input);
			value = property.getProperty(propertyName);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return value;
	}
	
	/*
	 * Set a given file with a new property of a given name.
	 */
	
	public static void setProperty(String propertyName, String fileName, String value) {
		Properties property = new Properties();
		OutputStream output = null;
		try {
			output = new FileOutputStream("src/properties/" + fileName + ".properties");
			property.setProperty(propertyName, value);
			property.store(output, null);
			
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (output != null) {
				try {
					output.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
