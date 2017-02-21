package examples.pubhub.utilities;


import org.apache.log4j.Level;
import org.apache.log4j.Logger;

public class LoggingTest {

	private static Logger log = Logger.getLogger(LoggingTest.class);
	
	public void logResults(String info){
		log.info("Info: " + info);
		log.error("Error " + info);
		log.fatal("Fatal Message: " + info);
		log.debug("Debug: " + info);
		log.warn("Warning: " + info);
	}
}
