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
 * Generate the domain class, based in the definition in yaml format, join the template in jtwig
 * @see com.vyfido.mappergen.core.Store
 * @author Wilson Garay
 * @version 1.3.0
 * @since 1.0.0
 *
 */
public class GenerateDomain implements Store{ 
	
	/**manager the input file*/
	private final GeneralConfiguration CONFIGURATION = new GeneralConfiguration();
	
	/**template jtwig to used*/
	private final JtwigTemplate TEMPLATE = JtwigTemplate.classpathTemplate("templates/Class.twig");
	
	/**default dir output*/
	private String dirout = "/root";

	/**
	 * Constructor
	 * @param file file yaml to load
	 * @throws ConfigurationYamlException if the file not is correct
	 */
	public GenerateDomain(String file) throws ConfigurationYamlException, Exception {
		this.CONFIGURATION.setFile(file);
	}
	
	/**
	 * Transform the array Fields at HashMap, for template JTwig
	 * @param fields array the Fields
	 * @return HashMap equivalent
	 */
	private LinkedHashMap<String, String> generateMap(Field[] fields) {
		LinkedHashMap<String, String> flds = new LinkedHashMap<String, String>();
		for(Field fld : fields) {
			flds.put(fld.getField(),fld.getType());
		}
		return flds;
	}
	
	/**
	 * (non-Javadoc)
	 * @see com.vyfido.mappergen.core.Store
	 */
	public void generateFile() {
		String classname = CONFIGURATION.getConfigurationName();
		StringBuilder content = new StringBuilder("");
		LinkedHashMap<String, String> fields = generateMap(CONFIGURATION.getDomainFields());
			
		JtwigModel model = null;
		model = JtwigModel.newModel()
				.with("author", System.getProperty("user.name"))
				.with("domain", CONFIGURATION.getConfigurationDomain())
				.with("classname",StringUtils.capitalice(classname))
				.with("fields",fields);

		content.append(TEMPLATE.render(model));
;
		FileUtils.write(dirout+"/"+classname+".java" , content.toString());
		
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
			dirout+"/"+(CONFIGURATION.getConfigurationDomain().replace(".", "/"))+"/domain"
		};
		
		for(String path : paths) {
			File file = new File(path);
			if(!file.exists()) {
				file.mkdirs();
			}
		}
		
		dirout = dirout+"/"+CONFIGURATION.getConfigurationDomain().replace(".", "/")+"/domain";
		
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
