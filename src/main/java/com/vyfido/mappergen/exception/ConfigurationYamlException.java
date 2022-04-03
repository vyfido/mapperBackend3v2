package com.vyfido.mappergen.exception;

/**
 * Launch exception when the file YAML have any error or configuration inconsistency
 * @author Wilson Garay
 * @version 1.0.0
 * @since 1.0.0
 */
public class ConfigurationYamlException extends Exception{

	private static final long serialVersionUID = -4720644132729730200L;

	/***
	 * message the exception
	 */
	private String message = null;
	
	/**
	 * Constructor
	 */
	public ConfigurationYamlException() {
		super();
	}
	
	/**
	 * Constructor
	 * @param message message the exception
	 */
	public ConfigurationYamlException(String message) {
		super(message);
		this.message = message;
	}
	
	/**
	 * get the message to return
	 * @return message the exception
	 */
	public String getMessage() {
		return this.message;
	}
	
}
