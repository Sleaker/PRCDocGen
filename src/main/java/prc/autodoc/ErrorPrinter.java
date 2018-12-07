package prc.autodoc;

import java.io.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * A convenience class for printing errors to both log and System.err
 */
public class ErrorPrinter {
	private static Logger LOGGER = LoggerFactory.getLogger(ErrorPrinter.class);
	
	/**
	 * Prints the given string to both stderr and errorlog. In addition, adds
	 * a line terminator to the end of the string.
	 * 
	 * @param text string to write
	 */
	public void println(String text) {
		LOGGER.error(text);
	}
	
	/**
	 * Prints the given exception's stack trace to both stderr and errorlog.
	 * 
	 * @param e exception to print
	 */
	public void printException(Exception e) {
		LOGGER.debug("Exception:", e);
	}
}