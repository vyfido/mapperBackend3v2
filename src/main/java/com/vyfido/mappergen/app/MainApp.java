package com.vyfido.mappergen.app;

import java.io.File;
import java.io.IOException;

import com.vyfido.mappergen.exception.ConfigurationYamlException;
import com.vyfido.mappergen.type.GenerateDomain;
import com.vyfido.mappergen.type.GenerateMapper;
import com.vyfido.mappergen.type.GenerateQueries;
import com.vyfido.mappergen.type.GenerateTest;
import com.vyfido.mappergen.util.FileUtils;

/**
 * Define the main class for application 
 * @author Wilson Garay
 * @version 1.0.0
 * @since 1.0.0
 */
public class MainApp {
	
	/**
	 * execute the process generation the file yaml : domain, mapper, repository, service, controller
	 * @param model file name to process
	 * @throws ConfigurationYamlException error in read the file
	 */
	public static void execute(String model) throws ConfigurationYamlException,Exception {

		GenerateDomain domain = new GenerateDomain(model);
		GenerateMapper mapper = new GenerateMapper(model);
		GenerateQueries repository = new GenerateQueries(model);
		GenerateTest test = new GenerateTest(model);
		
		domain.generate();
		mapper.generate();
		repository.generate();
		test.generate();
		
	}
	
	/**
	 * initial start point, the application
	 * @param args the first argument SHOULD BE the path for work
	 * @throws ConfigurationYamlException fail to process yaml field has been occurred
	 * @throws IOException  fail in process the any file or directory
	 */
	public static void main(String... args) throws ConfigurationYamlException, IOException, Exception {
		
		/*
		String[] brave = {"Z:\\DEVELOPMENT\\workspaceGit\\mapperback\\src\\main\\resources"};
		args = brave;//*/
		
		System.out.println("Mapper JDBI 3 - Generator\n");
		
		if(args.length >= 1) {
			File file = new File(args[0]);
			if(file.exists()) {
				if(file.isFile()) {
					execute(file.getCanonicalPath());
				}
				
				if(file.isDirectory()) {
					String[] files = FileUtils.searchFiles(file.getCanonicalPath(), "yaml");
					for(String fil : files) {
						System.out.println("Procesing file: "+fil);
						execute(fil);
					}
				}
				
				System.out.println("\nGeneration process has been ending.");
				
			}else {
				System.err.println("Invalid resource.");
				System.exit(2);
			}
			
		}else {
			System.err.println("The generator required the file yaml or a directory for work.");
			System.exit(1);
		}
		
	}

}
