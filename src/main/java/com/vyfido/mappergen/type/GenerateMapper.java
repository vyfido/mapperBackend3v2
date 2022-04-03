package com.vyfido.mappergen.type;

import java.io.File;
import java.util.LinkedHashMap;

import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;

import com.vyfido.mappergen.configuration.GeneralConfiguration;
import com.vyfido.mappergen.core.Store;
import com.vyfido.mappergen.exception.ConfigurationYamlException;
import com.vyfido.mappergen.fragment.Field;
import com.vyfido.mappergen.util.FileUtils;
import com.vyfido.mappergen.util.StringUtils;

/**
 * Implement the mapper for associate between domain class and database queries
 * @see com.vyfido.mappergen.core.Store
 * @author Wilson Garay
 * @version 1.0.0
 * @since 1.0.0
 *
 */
public class GenerateMapper implements Store{

	/**manager the input file*/
	private final GeneralConfiguration CONFIGURATION = new GeneralConfiguration();
	
	/**template jtwig to used*/
	private final JtwigTemplate TEMPLATE = JtwigTemplate.classpathTemplate("templates/Mapper.twig");
	
	/**default dir output*/
	private String dirout = "/root";
	
	/**
	 * Constructor
	 * @param file file yaml to load
	 * @throws ConfigurationYamlException if the file not is correct
	 */
	public GenerateMapper(String file) throws ConfigurationYamlException, Exception {
		CONFIGURATION.setFile(file);
	}
	
	/**
	 * break the fields data and generate the combination name_field, type_field for mapper
	 * @param fields fields in the input file(domain.fields)
	 * @return a linkedhashmap based in the input with combination Field.name and Filed.type
	 */	
	private LinkedHashMap<String, String> generateMap(Field[] fields) {
		LinkedHashMap<String, String> flds = new LinkedHashMap<String, String>();
		String typeUse = "";
		for(Field fld : fields) {
			if(fld.getType().equalsIgnoreCase("Integer")) {
				typeUse = "rs.getInt(\""+fld.getField()+"\")";
			}else {
				typeUse = "rs.get"+fld.getType()+"(\""+fld.getField()+"\")";
			}
			flds.put(StringUtils.toCamelCase("set_"+fld.getField()),typeUse);
		}
		return flds;
	}

	/**
	 * (non-Javadoc)
	 * @see com.vyfido.mappergen.core.Store
	 */
	public void generateFile() {
		String classname = CONFIGURATION.getConfigurationName();// getPropertyPath("configuration.name");
		StringBuilder content = new StringBuilder("");
		LinkedHashMap<String, String> fields = new LinkedHashMap<String, String>();
		

		fields = generateMap(CONFIGURATION.getDomainFields());
		
		JtwigModel model = null;
		model = JtwigModel.newModel()
				.with("author", System.getProperty("user.name"))
				.with("domain", CONFIGURATION.getConfigurationDomain())
				.with("classname",StringUtils.capitalice(classname))
				.with("nameinstance", classname.toLowerCase())
				.with("fields",fields);
		
		content.append(TEMPLATE.render(model));
		String dir = CONFIGURATION.getConfigurationOutput()+"/"+(CONFIGURATION.getConfigurationDomain().replace(".", "/"))+"/mapper";
		FileUtils.write(dir+"/"+classname+"Mapper.java" , content.toString());
		
	}

	/**
	 * (non-Javadoc)
	 * @see com.vyfido.mappergen.core.Store
	 */
	public void generateDirectory() {
		dirout = CONFIGURATION.getConfigurationOutput();
		String[] paths = {
			dirout,	
			dirout+"/"+(CONFIGURATION.getConfigurationDomain().replace(".", "/")),
			dirout+"/"+(CONFIGURATION.getConfigurationDomain().replace(".", "/"))+"/mapper"
		};
		
		for(String path : paths) {
			File file = new File(path);
			if(!file.exists()) {
				file.mkdirs();
			}
		}
		
	}

	/**
	 * (non-Javadoc)
	 * @see com.vyfido.mappergen.core.Store
	 */
	public void generate() {
		generateDirectory();
		generateFile();
	}

}
