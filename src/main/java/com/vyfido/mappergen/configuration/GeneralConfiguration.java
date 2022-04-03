package com.vyfido.mappergen.configuration;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.vyfido.mappergen.exception.ConfigurationYamlException;
import com.vyfido.mappergen.fragment.Field;
import com.vyfido.mappergen.fragment.Query;
import com.vyfido.mappergen.model.QueryModel;
import com.vyfido.mappergen.util.FileUtils;

/**
 * Implement the management model
 * @author Fernando Garay
 * @version 2.0.0
 * @since 1.0.0
 */
public class GeneralConfiguration {
	
	/**content file*/
	private String file = null;
	
	/**yaml file descriptor*/
	private QueryModel model = null;

	
	/**
	 * Constructor
	 */
	public GeneralConfiguration() {

	}
	/**
	 * Constructor
	 * @param file file to evaluate
	 * @throws ConfigurationYamlException if the configuration fail not can not load
	 */
	public GeneralConfiguration(String file) throws ConfigurationYamlException, Exception {
		this.file = file;
		loadFile();
	}
	
	/**
	 * Change the file to load
	 * @param file file to load
	 * @throws ConfigurationYamlException if the configuration fail not can load
	 */
	public void setFile(String file) throws ConfigurationYamlException, Exception {
		this.file = file;
		loadFile();
	}
	
	/**
	 * Load a file in YAML and transform in model
	 * @throws ConfigurationYamlException al procesar el archivo YAML
	 * @throws Exception fallo sin identificar
	 */
	private void loadFile() throws ConfigurationYamlException, Exception {
			ObjectMapper om = new ObjectMapper(new YAMLFactory());
			try {
				this.model = om.readValue(FileUtils.readFile(this.file),QueryModel.class);
			} catch (JsonMappingException e) {
				throw new ConfigurationYamlException("Error in parser in YAML(format) see details:\n"+e.getMessage());
			} catch (JsonProcessingException e) {
				throw new ConfigurationYamlException("Error in parser in YAML(format) see details:\n"+e.getMessage());
			} catch(IOException e) {
				throw new ConfigurationYamlException("The file defined "+this.file+"not found:\n"+e.getMessage());
			} catch(IllegalArgumentException e) {
				throw new ConfigurationYamlException("The file not is valid");
			} catch(Exception e) {
				throw e;
			}		
	}
	
	public String getConfigurationName() {
		return model.getConfiguration().get("name");
	}
	
	public String getConfigurationDomain() {
		return model.getConfiguration().get("domain");
	}
	
	public String getConfigurationOutput() {
		return model.getConfiguration().get("outputdir");
	}
	
	public Field[] getDomainFields() {
		return model.getDomain().getFields();
	}
	
	public Query[] getQuerys() {
		return model.getQueries();
	}
	

}
