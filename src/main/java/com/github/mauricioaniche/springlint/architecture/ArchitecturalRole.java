package com.github.mauricioaniche.springlint.architecture;

import com.google.gson.annotations.Expose;

public class ArchitecturalRole {

	public static ArchitecturalRole OTHER = new ArchitecturalRole(9999, "other");
	
	@Expose
	private String name;
	@Expose
	private int id;

	public ArchitecturalRole(int id, String name) {
		this.id = id;
		this.name = name;
	}

	public String name() {
		return name;
	}

	public int id() {
		return id;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		ArchitecturalRole other = (ArchitecturalRole) obj;
		if (id != other.id)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ArchitecturalRole [role=" + name + ", id=" + id + "]";
	}
	
	
	
	
}
