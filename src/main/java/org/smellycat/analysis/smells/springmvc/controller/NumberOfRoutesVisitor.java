package org.smellycat.analysis.smells.springmvc.controller;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.MarkerAnnotation;
import org.eclipse.jdt.core.dom.NormalAnnotation;
import org.eclipse.jdt.core.dom.SingleMemberAnnotation;
import org.smellycat.domain.SmellyClass;

public class NumberOfRoutesVisitor extends ASTVisitor {

	private static String ROUTE_ANNOTATION = "@requestmapping";
	private SmellyClass clazz;
	
	public NumberOfRoutesVisitor(SmellyClass clazz) {
		this.clazz = clazz;
		clazz.setAttribute("number-of-routes", 0);
	}

	public boolean visit(NormalAnnotation node) {
		check(node);
		return super.visit(node);
	}

	public boolean visit(MarkerAnnotation node) {
		check(node);
		
		return super.visit(node);
	}
	
	public boolean visit(SingleMemberAnnotation node) {
		check(node);
		return super.visit(node);
	}

	private void check(ASTNode node) {
		String ann = node.toString().toLowerCase();
		if(ann.contains(ROUTE_ANNOTATION)) plusOne();
	}
	
	private void plusOne() {
		clazz.plusOne("number-of-routes");
		
	}
	
}
