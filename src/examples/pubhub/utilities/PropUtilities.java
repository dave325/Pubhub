package examples.pubhub.utilities;

import java.io.IOException;
import java.util.Properties;

/*
 * This class retrieves all properties in the settings.properties file, and makes them available to the application.
 */
public class PropUtilities {

	private static ClassLoader classLoader = null;
	private static Properties props = new Properties();
	
	// A static block of code is run as soon as the application is loaded into memory.
	// Executing this code will always be the first thing the server does when it starts the first time.
	static {
		// Get a reference to the current application
		classLoader = Thread.currentThread().getContextClassLoader();
		try {
			// load the settings.properties file found inside the current application, into the Properties object
			props.load(classLoader.getResourceAsStream("/settings.properties"));
		} catch (IOException e) {
			System.out.println("Could not load properties file!");
			e.printStackTrace();
		}
	}
	
	public static double getMinPrice() {
		return Double.parseDouble(props.getProperty("minPrice"));
	}
	
	public static double getServiceRoyaltyRate() {
		return Double.parseDouble(props.getProperty("ServiceRoyaltyRate"));
	}
	
}
