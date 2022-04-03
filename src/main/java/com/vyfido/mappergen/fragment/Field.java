package com.vyfido.mappergen.fragment;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Describe the field in database and type java to use
 * @author Fernando Garay
 * @version 1.0.0
 * @since 1.0.0
 */
@ToString
@EqualsAndHashCode
public class Field{
	
	/**name the field*/
	@Getter @Setter
	String field;
	
	/**type the field*/
	@Getter @Setter
	String type;
	
	/**
	 * Constructor
	 */
	public Field() {
		
	}
}


