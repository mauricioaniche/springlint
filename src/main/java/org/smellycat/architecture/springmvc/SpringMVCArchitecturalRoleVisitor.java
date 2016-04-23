package org.smellycat.architecture.springmvc;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.jdt.core.dom.Annotation;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.MarkerAnnotation;
import org.eclipse.jdt.core.dom.NormalAnnotation;
import org.eclipse.jdt.core.dom.SingleMemberAnnotation;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.smellycat.architecture.ArchitecturalRole;
import org.smellycat.architecture.ArchitecturalRoleVisitor;

import br.com.aniche.ck.CKNumber;
import br.com.aniche.ck.CKReport;

public class SpringMVCArchitecturalRoleVisitor extends ArchitecturalRoleVisitor {

	private Set<String> allAnnotations;
	private int deep=0;
	
	public SpringMVCArchitecturalRoleVisitor() {
		this.allAnnotations = new HashSet<String>();
	}
	
	public boolean visit(TypeDeclaration node) {
		deep++;
		return super.visit(node);
	}

	public void endVisit(TypeDeclaration node) {
		deep--;
	}
	
	public boolean visit(MarkerAnnotation node) {
		addAnnotation(node);
		return super.visit(node);	
	}

	public boolean visit(NormalAnnotation node) {
		addAnnotation(node);
		return super.visit(node);	
	}

	public boolean visit(SingleMemberAnnotation node) {
		addAnnotation(node);
		return super.visit(node);	
	}
	

	private void addAnnotation(Annotation o) {
		if(deep>1) return;
		
		String annotation = o.getTypeName().toString();
		allAnnotations.add(annotation);
		
	}
	
	public ArchitecturalRole getRole() {
		
		for(String ann : allAnnotations) {
			if(ann.endsWith("Controller")) return SpringMVCArchitecture.CONTROLLER;
			if(ann.endsWith("Service")) return SpringMVCArchitecture.SERVICE;
			if(ann.endsWith("Entity")) return SpringMVCArchitecture.ENTITY;
			if(ann.endsWith("Repository")) return SpringMVCArchitecture.REPOSITORY;
			if(ann.endsWith("Component")) return SpringMVCArchitecture.COMPONENT;
		}
		
		return ArchitecturalRole.OTHER;
	}

	@Override
	public void execute(CompilationUnit cu, CKReport report) {
		cu.accept(this);
	}

	@Override
	public void setResult(CKNumber result) {
		result.addSpecific("role", getRole().id());
	}
}
