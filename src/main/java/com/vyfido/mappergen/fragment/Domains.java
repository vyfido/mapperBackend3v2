package com.vyfido.mappergen.fragment;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Describe the fields used in the generation of POJO 
 * @author Fernando Garay
 * @version 1.0.0
 * @since 1.0.0
 */
@ToString
@EqualsAndHashCode
public class Domains{
	
	/**List the fields used in the table*/
	@Getter @Setter
	Field[] fields;
	
	/**
	 * Constructor
	 */
	public Domains() {
		
	}
	
}
