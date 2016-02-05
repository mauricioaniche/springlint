package org.smellycat.domain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

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

	public SmellyClass add(String sourceFilePath, String className, String type, String superclass, Set<String> interfaces) {
		SmellyClass smellyClass = new SmellyClass(sourceFilePath, className, type, superclass, interfaces);
		
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

	public List<SmellyClass> getSubtypesOf(String interfaceName) {
		List<SmellyClass> subtypes = new ArrayList<>();
		
		for(SmellyClass sc : mapPerName.values()) {
			if(sc.getInterfaces().contains(interfaceName))
				subtypes.add(sc);
		}
		return subtypes;
	}

}
