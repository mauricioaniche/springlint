package org.smellycat.springmvc.domain;

import java.util.HashMap;
import java.util.Map;

public class SmellyClass {

	private final String file;
	private final String name;
	private final String type;
	private ArchitecturalRole role;
	private Map<String, String> smells;
	private Map<String, Integer> attributes;
	
	public SmellyClass(String file, String name, String type) {
		this.file = file;
		this.name = name;
		this.type = type;
		this.smells = new HashMap<String, String>();
		this.attributes = new HashMap<String, Integer>();
		this.role = ArchitecturalRole.UNINDENTIFIED;
	}
	
	public String getFile() {
		return file;
	}
	public String getName() {
		return name;
	}
	public String getType() {
		return type;
	}
	public void setRole(ArchitecturalRole role) {
		this.role = role;
	}
	public ArchitecturalRole getRole() {
		return role;
	}
	
	public void smells(String smell, String explanation) {
		smells.put(smell, explanation);
	}
	
	public boolean hasSmell(String smell) {
		return smells.containsKey(smell);
	}
	
	public void setAttribute(String attribute, int value) {
		attributes.put(attribute, value);
	}
	
	public int getAttribute(String attribute) {
		if(!attributes.containsKey(attribute)) return 0;
		return attributes.get(attribute);
	}

	public boolean is(ArchitecturalRole role) {
		return this.role.equals(role);
	}

	public void plusOne(String attribute) {
		setAttribute(attribute, getAttribute(attribute)+1);
	}

	@Override
	public String toString() {
		return "SmellyClass [file=" + file + ", name=" + name + ", type=" + type + ", role=" + role + ", smells="
				+ smells + ", attributes=" + attributes + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((file == null) ? 0 : file.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((role == null) ? 0 : role.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SmellyClass other = (SmellyClass) obj;
		if (file == null) {
			if (other.file != null)
				return false;
		} else if (!file.equals(other.file))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (role != other.role)
			return false;
		return true;
	}

	public String getExplanationFor(String smell) {
		return smells.get(smell);
	}

	
	
}