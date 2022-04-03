package com.vyfido.mappergen.core;

/**
 * Define the basic operation for any class that generate a file
 * @author Wilson Garay
 * @version 1.2.0
 * @since 1.0.0
 */
public interface Store {

	/**
	 * generate file
	 */
	void generateFile();

	/**
	 * generate directory
	 */	
	void generateDirectory();
	
	/**
	 * generate directory and file
	 */
	void generate();
	
}
