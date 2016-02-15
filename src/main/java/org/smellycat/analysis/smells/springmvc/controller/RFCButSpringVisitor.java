package org.smellycat.analysis.smells.springmvc.controller;

import java.util.HashSet;

import org.eclipse.jdt.core.dom.MethodInvocation;
import org.smellycat.analysis.smells.VariablesAndFieldsVisitor;
import org.smellycat.domain.SmellyClass;

public class RFCButSpringVisitor extends VariablesAndFieldsVisitor {

	private HashSet<String> methodInvocations;
	
	private SmellyClass clazz;

	public RFCButSpringVisitor(SmellyClass clazz) {
		this.clazz = clazz;
		this.methodInvocations = new HashSet<String>();
		
		update(clazz);
	}

	private void update(SmellyClass clazz) {
		clazz.setAttribute("rfc-but-spring", methodInvocations.size());
	}

	public boolean visit(MethodInvocation node) {
		boolean wasInvokedInADependency = node.getExpression()!=null;
		if(wasInvokedInADependency && !belongsToSpring(findType(node))) {
			methodInvocations.add(node.getName()  + "/" + node.arguments().size());
		}
		
		update(clazz);
		
		return super.visit(node);
	}

	private boolean belongsToSpring(String type) {
		return type.contains(".springframework.");
	}
	

}
