package org.smellycat.domain;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.smellycat.analysis.smells.SmellDescription;
import org.smellycat.architecture.ArchitecturalRole;

public class SmellyClass {

	private final String file;
	private final String name;
	private final String type;
	private ArchitecturalRole role;
	private Set<SmellDescription> smells;
	private String superclass;
	private Set<String> interfaces;

	private Map<String, Integer> attributes;
	private Map<String, String> notes;
	
	public SmellyClass(String file, String name, String type, String superclass, Set<String> interfaces) {
		this.file = file;
		this.name = name;
		this.type = type;
		this.superclass = superclass;
		this.interfaces = interfaces;
		this.smells = new HashSet<SmellDescription>();
		this.role = ArchitecturalRole.OTHER;

		this.attributes = new HashMap<String, Integer>();
		this.notes = new HashMap<String, String>();
	}
	
	public void appendNote(String key, String note) {
		if(!notes.containsKey(key)) notes.put(key, "");
		notes.put(key, (notes.get(key) + " " + note).trim());
	}
	
	public String getNote(String key) {
		if(!notes.containsKey(key)) return "";
		return notes.get(key);
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
	
	public void smells(String smell, String description) {
		smells.add(new SmellDescription(smell, description));
	}
	
	public boolean hasSmell(String smell) {
		return smells.stream().anyMatch(s -> s.getName().equals(smell));
	}
	
	public void setAttribute(String attribute, int value) {
		attributes.put(attribute, value);
	}
	
	public int getAttribute(String attribute) {
		if(!attributes.containsKey(attribute)) return -1;
		return attributes.get(attribute);
	}

	public boolean is(ArchitecturalRole role) {
		return this.role.equals(role);
	}

	public void plusOne(String attribute) {
		if(getAttribute(attribute)==-1)
			setAttribute(attribute, 0);
		
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
	
	public boolean hasAnySmell() {
		return !smells.isEmpty();
	}

	public String getDescriptionFor(String smell) {
		return smells.stream().filter(s->s.getName().equals(smell)).findFirst().get().getDescription();
	}

	public String getSuperclass() {
		return superclass;
	}
	
	public Set<String> getInterfaces() {
		return interfaces;
	}

	public Set<SmellDescription> getSmells() {
		return smells;
	}
	
}
