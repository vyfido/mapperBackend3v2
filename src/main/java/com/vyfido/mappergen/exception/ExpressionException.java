package com.vyfido.mappergen.exception;

/**
 * This exception is launch when the expression sql used in the method 
 * <ul>
 *  <li> use the keyword DELETE </li>
 *  <li> use if the keyword SELECT include the expression '*' in the query </li>
 * </ul>
 * @version 1.0.0
 * @since 1.0.0
 */
public class ExpressionException extends Exception{
	
	private static final long serialVersionUID = -8975007616886520320L;
	
	/***
	 * message the exception
	 */	
	private String message = null;
	
	/**
	 * Constructor
	 */
	public ExpressionException() {
		super();
	}
	
	/**
	 * Constructor
	 * @param message message the exception
	 */
	public ExpressionException(String message) {
		super(message);
		this.message = message;
	}
	
	/**
	 * get the message store in the exception
	 * @return text of message
	 */
	public String getMessage() {
		return this.message;
	}

}
