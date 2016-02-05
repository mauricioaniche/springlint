package org.smellycat.springmvc.ar;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.Annotation;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.MarkerAnnotation;
import org.eclipse.jdt.core.dom.NormalAnnotation;
import org.eclipse.jdt.core.dom.SingleMemberAnnotation;
import org.smellycat.springmvc.domain.ArchitecturalRole;

import br.com.aniche.ck.CKNumber;
import br.com.aniche.ck.CKReport;
import br.com.aniche.ck.metric.Metric;

public class ArchitecturalRoleVisitor extends ASTVisitor implements Metric {

	private Set<String> allAnnotations;
	
	public ArchitecturalRoleVisitor() {
		this.allAnnotations = new HashSet<String>();
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
		String annotation = o.getTypeName().toString();
		allAnnotations.add(annotation);
		
	}
	
	public ArchitecturalRole getRole() {
		
		for(String ann : allAnnotations) {
			if(ann.endsWith("Controller")) return ArchitecturalRole.CONTROLLER;
			if(ann.endsWith("Service")) return ArchitecturalRole.SERVICE;
			if(ann.endsWith("Entity")) return ArchitecturalRole.ENTITY;
			if(ann.endsWith("Repository")) return ArchitecturalRole.REPOSITORY;
			if(ann.endsWith("Component")) return ArchitecturalRole.COMPONENT;
		}
		
		return ArchitecturalRole.UNINDENTIFIED;
	}

	@Override
	public void execute(CompilationUnit cu, CKReport report) {
		cu.accept(this);
	}

	@Override
	public void setResult(CKNumber result) {
		result.addSpecific("role", getRole().ordinal());
	}
}
