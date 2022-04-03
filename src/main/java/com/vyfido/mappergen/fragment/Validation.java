package com.vyfido.mappergen.fragment;

/**
 * Define an action for validate the fields in applications model
 * @author Fernando Garay
 * @version 1.0.0
 * @since 1.0.0
 */
public interface Validation {

	/**
	 * check if the properties obligatory in the configuration class
	 * @return state of evaluation true correct or false in error case
	 */
	boolean validate();

}
