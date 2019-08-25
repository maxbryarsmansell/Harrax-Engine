package com.max.harrax.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

import com.max.harrax.Main;

public class Property {

	public enum PropertyType {
		STRING, INT, BOOLEAN
	}

	/*
	 * Load a property with a given name from a given file. Return the property as a
	 * string.
	 */

	// @Refactor: This definitely needs to be refactored.
	public static String loadProperty(String propertyName, String fileName) {
		System.out.println("Loading the property...\"" + propertyName + "\", from...\"" + fileName + ".properties\"");

		Properties property = new Properties();
		InputStream input = null;
		String loadedProperty = null;

		try {
			input = Main.class.getResourceAsStream("/properties/" + fileName + ".properties.xml");
			property.loadFromXML(input);

			loadedProperty = property.getProperty(propertyName);
		} catch (IOException e) {

			System.out.println("Loading of the property failed with: " + e.getMessage());

		} finally {
			assert input == null : "The input stream for loading is null.";

			try {
				input.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return loadedProperty;
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
