package com.vyfido.mappergen.type;

import java.io.File;
import java.util.ArrayList;

import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;

import com.vyfido.mappergen.configuration.GeneralConfiguration;
import com.vyfido.mappergen.core.Store;
import com.vyfido.mappergen.exception.ConfigurationYamlException;
import com.vyfido.mappergen.exception.ExpressionException;
import com.vyfido.mappergen.fragment.Field;
import com.vyfido.mappergen.fragment.Query;
import com.vyfido.mappergen.util.FileUtils;
import com.vyfido.mappergen.util.StringUtils;

/**
 * Implement the repository methods associate to yaml file 
 * @see com.vyfido.mappergen.core.Store
 * @author Wilson Garay
 * @version 1.0.0
 * @since 1.0.0
 *
 */
public class GenerateQueries implements Store{

	/**manager the input file*/
	private final GeneralConfiguration CONFIGURATION = new GeneralConfiguration();

	/**template jtwig to used*/
	private final JtwigTemplate TEMPLATE = JtwigTemplate.classpathTemplate("templates/Repository.twig");

	/**default dir output*/
	private String dirout = "/root";

	/**
	 * Constructor
	 * @param file file yaml to load
	 * @throws ConfigurationYamlException if the file not is correct
	 */
	public GenerateQueries(String file) throws ConfigurationYamlException, Exception {
		CONFIGURATION.setFile(file);
	}

	/**
	 * transform the sentence SQL in the equivalent list of parameters need for service
	 * @param input sentence SQL
	 * @return list the parameters of sentence
	 */
	private String generateParams(String input) {
		StringBuilder output = new StringBuilder("");
		String[] values = input.replace(")","").split("VALUES\\(");
		if(input.toUpperCase().contains("INSERT")) {
			values = input.replace(")","").split("VALUES\\(");
		}
		if(input.toUpperCase().contains("SELECT") ) {
			values = input.toLowerCase().replace(")","").split("where");
		}
		if(input.toUpperCase().contains("UPDATE")) {
			values = input.toLowerCase().split("\\s+");
		}
		
		if(values.length>1) {
			String[] params = {};
			if(input.toUpperCase().contains("INSERT")) {
				params = values[1].split(",");
			}else if(input.toUpperCase().contains("SELECT")) {
				String[] p2 = values[1].split("\\s+");
				ArrayList<String> pp = new ArrayList<String>();
				for(String p : p2) {
					if(p.contains(":")) {
						pp.add(p);
					}
				}
				params = new String[pp.size()];
				for(int ind=0; ind <pp.size(); ind++) {
					params[ind] = pp.get(ind);
				}
			}else if(input.toUpperCase().contains("UPDATE")) {
				ArrayList<String> pp = new ArrayList<String>();
				for(String p : values) {
					if(p.contains(":")) {
						if(p.contains(",")) {
							p = p.substring(0,p.indexOf(","));
						}
						pp.add(p);
					}
				}
				params = new String[pp.size()];
				for(int ind=0; ind <pp.size(); ind++) {
					params[ind] = pp.get(ind);
				}
			}
			
			int cont = 0;
			int max = params.length;
			Field[] fields = CONFIGURATION.getDomainFields();
			
			String realtype = "";
			for(String param : params) {
				
				for(Field f : fields) {
					if(f.getField().contains(param.substring(1))) {
						realtype = f.getType();
						break;
					}
				}
				
				if(realtype.isEmpty()) {
					realtype = "Object";
				}
				
				
				output.append("@Bind(\"");
				
				if(input.toUpperCase().contains("INSERT")) {
					output.append(param.replace(":", "")).append("\") "+realtype+" ");
				}
				if(input.toUpperCase().contains("SELECT") || input.toUpperCase().contains("UPDATE")){
					output.append(param.replace(":", "")).append("\") "+realtype+" ");
				}
				output.append(param.replace(":", ""));
				if(cont<max-1) {
					output.append(" , ");
				}
				cont++;
				realtype = "";
			}
		}

		return output.toString();
	}

	/**
	 * process the internal detail defined for the management of repository
	 * @return a string with all methods of repository
	 * @throws ExpressionException if found the expression SELECT * or DELETE
	 */	
	private String generateQueries() throws ExpressionException {
		StringBuilder sb = new StringBuilder();
		Query[] queries = CONFIGURATION.getQuerys();

		JtwigTemplate template = JtwigTemplate.classpathTemplate("templates/Query.twig");
		JtwigModel model = null;
		String name = null;
		String sentence = null;
		String params = null;


		StringBuilder ops = new StringBuilder("");
		boolean tx = false;
		boolean txi = false;
		for(Query field : queries) {
			
			name = field.getName();
			sentence = field.getSentence();
			if((sentence.toUpperCase().contains("SELECT ") && sentence.toUpperCase().contains("*")) ||
				sentence.toUpperCase().contains("DELETE")) {
				throw new ExpressionException("The sentence SQL is invalid for this tool");
			}
			tx = (sentence.toUpperCase().contains("INSERT")) || (sentence.toUpperCase().contains("UPDATE"));
			txi = (sentence.toUpperCase().contains("INSERT"));
			params = generateParams(sentence);
			
			model = JtwigModel.newModel()
					.with("nameservice", name)
					.with("sentence", sentence)
					.with("output", field.getOutput())
					.with("tx",tx)
					.with("txi",txi)
					.with("params", params);
					
			
			ops.append(template.render(model));
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
			String out = content.toString().replace("__textQueries__", generateQueries());
			FileUtils.write(dirout+"/"+classname+"Queries.java" , out);			
		}catch(ExpressionException e) {
			System.err.println("The file "+classname+"Repository no can be generated.");
			System.err.println(e.getMessage());			
		}catch(Exception e) {
			System.err.println("The file "+classname+"Repository no can be generated.");
			System.err.println(e.getMessage());			
		}


	}

	/**
	 * (non-Javadoc)
	 * @see com.vyfido.mappergen.core.Store
	 */
	public void generateDirectory() {
		dirout = CONFIGURATION.getConfigurationOutput();// getPropertyPath("configuration.outputdir");
		String[] paths = {
				dirout,	
				dirout+"/"+(CONFIGURATION.getConfigurationDomain().replace(".", "/")),
				dirout+"/"+(CONFIGURATION.getConfigurationDomain().replace(".", "/"))+"/queries"
		};

		for(String path : paths) {
			File file = new File(path);
			if(!file.exists()) {
				file.mkdirs();
			}
		}

		dirout = dirout+"/"+(CONFIGURATION.getConfigurationDomain().replace(".", "/"))+"/queries";

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
