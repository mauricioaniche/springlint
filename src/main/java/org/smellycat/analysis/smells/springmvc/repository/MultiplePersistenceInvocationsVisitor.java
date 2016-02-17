package org.smellycat.analysis.smells.springmvc.repository;

import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.smellycat.analysis.smells.VariablesAndFieldsVisitor;
import org.smellycat.domain.SmellyClass;

public class MultiplePersistenceInvocationsVisitor extends VariablesAndFieldsVisitor {

	private SmellyClass clazz;
	private int qtyPerMethod = 0;
	
	public MultiplePersistenceInvocationsVisitor(SmellyClass clazz) {
		this.clazz = clazz;
		clazz.setAttribute("multiple-persistence-invocations", 0);
	}
	
	public boolean visit(MethodDeclaration node) {
		qtyPerMethod = 0;
		
		return super.visit(node);
	}
	
	
	public void endVisit(MethodDeclaration node) {
		if(qtyPerMethod > 1) {
			clazz.setAttribute("multiple-persistence-invocations", 1);
			saveMethodNameAsNote(node);
		}
		
		super.endVisit(node);
	}
	
	public boolean visit(MethodInvocation node) {
		boolean wasInvokedInADependency = node.getExpression()!=null;
		
		if(wasInvokedInADependency && 
			PersistenceAPIs.isOnPersistenceMechanism(findType(node)) && 
			PersistenceAPIs.isOnPersistenceMethod(node)) {
			
			qtyPerMethod++;
			
		}
		
		return super.visit(node);
	}

	private void saveMethodNameAsNote(MethodDeclaration currentMethod) {
		String methodName = currentMethod.getName().toString();
		clazz.appendNote("multiple-persistence-invocations-violations",  methodName);
	}

}
