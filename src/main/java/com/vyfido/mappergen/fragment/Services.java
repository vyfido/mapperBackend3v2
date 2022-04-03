package com.vyfido.mappergen.fragment;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Describe the methods that will be used in the generation of controllers
 * @author Fernando Garay
 * @version 1.0.0
 * @since 1.0.0
 */
@ToString
@EqualsAndHashCode
public class Services {
	
	/**name the method*/
	@Getter @Setter
	String name;
	
	/**sentence used(remember spring-jdbc)*/
	@Getter @Setter
	String sentence;
	
	/**uri to display method*/
	@Getter @Setter
	String uri;
	
	/**consumes property*/
	@Getter @Setter
	String consumes;
	
	/**produces property*/
	@Getter @Setter
	String produces;
	
	/**http method used*/
	@Getter @Setter
	String verb;
	
	/**object the output*/
	@Getter @Setter
	String output;
	
	/**
	 * Constructor
	 */
	public Services() {
		
	}
	
}
