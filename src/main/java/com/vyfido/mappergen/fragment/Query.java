package com.vyfido.mappergen.fragment;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Describe the sentences SQL that will be used 
 * @author Fernando Garay
 * @version 1.0.0
 * @since 1.0.0
 */
@ToString
@EqualsAndHashCode
public class Query {
	
	/**name the method*/
	@Getter @Setter
	String name;
	
	/**sentence SQL used*/
	@Getter @Setter
	String sentence;
	
	/**Output object*/
	@Getter @Setter
	String output;
	
	/**
	 * Constructor
	 */
	public Query() {
		
	}

}
