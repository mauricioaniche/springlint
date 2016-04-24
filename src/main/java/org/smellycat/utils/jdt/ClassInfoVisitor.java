package org.smellycat.utils.jdt;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.EnumDeclaration;
import org.eclipse.jdt.core.dom.ITypeBinding;
import org.eclipse.jdt.core.dom.Type;
import org.eclipse.jdt.core.dom.TypeDeclaration;

public class ClassInfoVisitor extends ASTVisitor {

	private String className;
	private String type;
	private String superclass;
	private Set<String> interfaces;
	
	public ClassInfoVisitor() {
		this.interfaces = new HashSet<String>();
	}

	@Override
	public boolean visit(TypeDeclaration node) {
		
		getFullClassName(node.resolveBinding());
		getType(node);
		getSuperclass(node);
		getInterfaces(node);
		
		
		return false;
	}

	private void getInterfaces(TypeDeclaration node) {
		if(node.superInterfaceTypes()!=null) {
			for(Object o : node.superInterfaceTypes()) {
				String interfaceName = ((Type)o).resolveBinding().getBinaryName();
				interfaces.add(interfaceName);
			}
		}
	}

	private void getSuperclass(TypeDeclaration node) {
		if(node.getSuperclassType()!=null) {
			superclass = node.getSuperclassType().resolveBinding().getBinaryName();
		}
	}

	private void getType(TypeDeclaration node) {
		if(node.isInterface()) type = "interface";
		else type = "class";
	}

	@Override
	public boolean visit(EnumDeclaration node) {
		type = "enum";
		getFullClassName(node.resolveBinding());
		return false;
	}
	
	public String getClassName() {
		return className;
	}
	
	public String getType() {
		return type;
	}
	
	private void getFullClassName(ITypeBinding binding) {
		if(binding!=null)
			this.className = binding.getBinaryName();
	}

	public String getSuperclass() {
		return superclass;
	}

	public Set<String> getInterfaces() {
		return Collections.unmodifiableSet(interfaces);
	}
	
	
	
}