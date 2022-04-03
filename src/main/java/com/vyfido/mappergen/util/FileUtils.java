package com.vyfido.mappergen.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

/**
 * Implement the implements for management the files based in java nio.
 * @author Fernando Garay
 * @version 1.0.0
 * @since 1.0.0
 *
 */
public final class FileUtils {
	
	/**
	 * Constructor
	 */
	private FileUtils() {
		
	}
	
	public static String readFile(String file) throws IOException, IllegalArgumentException, Exception {
		if(file != null && file.length()>0 && !file.isEmpty()) {
			try {
				return new String(Files.readAllBytes(Paths.get(file)) );
			} catch (IOException e) {
				throw e;
			} catch (Exception e) {
				throw e;
			}
		}else {
			throw new IllegalArgumentException("File input is incorrect.");
		}
	}
	
	public static boolean write(String namefile, String content) {
		
		try {
			Files.write(Paths.get(namefile), content.getBytes());
			return true;
		} catch (IOException e) {
			return false;
		}
	}
	
	//
	public static String[] searchFiles(String path, String ext){
		String[] files = new String[0];
		File directory = new File(path);
		if(directory.isDirectory()){
		    ArrayList<String> list = new ArrayList<String>();
		    try{
				for(File file : directory.listFiles()){
					if(file.isFile()){					
						if(ext != null){
							if(file.getCanonicalPath().lastIndexOf(ext) != -1){
								list.add(file.getCanonicalPath());
							}
						}else{
							list.add(file.getCanonicalPath());
						}
					}
				}		    	
		    }catch(NullPointerException e) {
		    	list.clear();
		    }catch(Exception e){
		    	list.clear();
		    }
		    
		    files = new String[list.size()];
		    for(int ind=0;ind<list.size();ind++) {
		    	files[ind] = list.get(ind);
		    }
		}
		return files;
	}
	

}
