package com.vyfido.mappergen.model;


import java.util.HashMap;

import com.vyfido.mappergen.fragment.Domains;
import com.vyfido.mappergen.fragment.Query;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * This class model the Mapper used in jdbi2 y jdbi3
 * @author Fernando Garay
 * @version 1.0.0
 * @since 1.0.0
 *
 */
@ToString
@EqualsAndHashCode
public class QueryModel {

	/**configuration initial the process*/
	@Getter @Setter
	HashMap<String, String> configuration = new HashMap<String, String>();

	/**field needed for generate POJO class*/
	@Getter @Setter
	Domains domain = new Domains();
	
	/**sentences SQL needed for generate*/
	@Getter @Setter
	Query queries[] = {};
	
	/**
	 * Constructor
	 */
	public QueryModel() {
		
	}
	
}
