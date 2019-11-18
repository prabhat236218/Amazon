package testingframeworks.amazon.utilities;

import org.apache.log4j.Logger;



public class Log {

	private Log() {

	}

	private static Logger logger = Logger.getLogger(Log.class);

	public static void inDebugAdd(String message) {
		logger.debug(message);
	}

	public static void inInfoAdd(String message) {
		logger.info(message);
	}

	public static void inErrorAdd(String message) {
		logger.error(message);
	}

	public static void inWarnAdd(String message) {
		logger.warn(message);
	}

	public static void inFatalAdd(String message) {
		logger.fatal(message);
	}
}
