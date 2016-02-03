package org.smellycat.springmvc.domain;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class Repository {

	private Map<String, SmellyClass> mapPerFile;
	private Map<String, SmellyClass> mapPerName;
	
	public Repository() {
		this.mapPerFile = new HashMap<String, SmellyClass>();
		this.mapPerName = new HashMap<String, SmellyClass>();
	}
	
	public SmellyClass get(String sourceFilePath) {
		return mapPerFile.get(sourceFilePath);
	}

	public SmellyClass add(String sourceFilePath, String className, String type) {
		SmellyClass smellyClass = new SmellyClass(sourceFilePath, className, type);
		
		mapPerFile.put(sourceFilePath, smellyClass);
		mapPerName.put(className, smellyClass);
		return get(sourceFilePath);
	}

	public Collection<SmellyClass> all() {
		return mapPerFile.values();
		
	}

	public SmellyClass getByClass(String qualifiedName) {
		return mapPerName.get(qualifiedName);
	}

}
