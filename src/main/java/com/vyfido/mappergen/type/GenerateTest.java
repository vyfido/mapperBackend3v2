package com.vyfido.mappergen.type;

import java.io.File;

import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;

import com.vyfido.mappergen.configuration.GeneralConfiguration;
import com.vyfido.mappergen.core.Store;
import com.vyfido.mappergen.exception.ConfigurationYamlException;
import com.vyfido.mappergen.fragment.Query;
import com.vyfido.mappergen.util.FileUtils;
import com.vyfido.mappergen.util.StringUtils;

public class GenerateTest implements Store{

	/**manager the input file*/
	private final GeneralConfiguration CONFIGURATION = new GeneralConfiguration();

	/**template jtwig to used*/
	private final JtwigTemplate TEMPLATE = JtwigTemplate.classpathTemplate("templates/Test.twig");

	/**default dir output*/
	private String dirout = "/root";

	/**
	 * Constructor
	 * @param file file to process
	 * @throws ConfigurationYamlException management the YAML file
	 */
	public GenerateTest(String file) throws ConfigurationYamlException, Exception {
		CONFIGURATION.setFile(file);
	}

	/**
	 * Transformate the method used in the mapper class, 
	 * @return name the method in yaml file(Queries section)
	 */
	private String generateMethods() {
		StringBuilder sb = new StringBuilder("");
		JtwigTemplate template = JtwigTemplate.classpathTemplate("templates/TestBody.twig");
		JtwigModel model = null;
		String name = null;  
		Query[] queries = CONFIGURATION.getQuerys();

		int order = 1;
		StringBuilder ops = new StringBuilder("");
		for(Query field : queries) {
			name = "order"+order+"_"+field.getName()+"Test";
			model = JtwigModel.newModel().with("name", name);

			ops.append(template.render(model));
			order++;

		}

		sb.append(ops.toString());

		return sb.toString();
	}

	/**
	 * (non-Javadoc)
	 * @see com.vyfido.mappergen.core.Store
	 */
	public void generateFile() {
		String classname = CONFIGURATION.getConfigurationName();
		StringBuilder content = new StringBuilder("");

		JtwigModel model = null;
		model = JtwigModel.newModel()
				.with("author", System.getProperty("user.name"))
				.with("domain", CONFIGURATION.getConfigurationDomain())
				.with("classname",StringUtils.capitalice(classname))
				.with("database",  StringUtils.toNormalCase(classname));
		content.append(TEMPLATE.render(model));

		try {
			String out = content.toString().replace("__textTest__", generateMethods());
			String dir = CONFIGURATION.getConfigurationOutput()+"/"+(CONFIGURATION.getConfigurationDomain().replace(".", "/")+"/test/"+classname+"QueriesTest.java");
		    dir = dir.replace("\\", "/");
			FileUtils.write(dir , out);      
		}catch(Exception e) {
			System.err.println("The file "+classname+"Test no can be generated.");
			System.err.println(e.getMessage());     
		}   

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
				dirout+"/"+(CONFIGURATION.getConfigurationDomain().replace(".", "/"))+"/test"
		};

		for(String path : paths) {
			File file = new File(path);
			if(!file.exists()) {
				file.mkdirs();
			}
		}

		dirout = CONFIGURATION.getConfigurationOutput()+"/"+(CONFIGURATION.getConfigurationDomain().replace(".", "/"))+"/test";

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
