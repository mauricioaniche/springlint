package org.springlint.analysis.smells.service;

import org.eclipse.jdt.core.dom.MethodInvocation;
import org.springlint.analysis.smells.VariablesAndFieldsVisitor;
import org.springlint.analysis.smells.repository.PersistenceAPIs;
import org.springlint.domain.SmellyClass;

public class UsePersistenceMechanismVisitor extends VariablesAndFieldsVisitor {

	private SmellyClass clazz;
	

	
	public UsePersistenceMechanismVisitor(SmellyClass clazz) {
		this.clazz = clazz;
		clazz.setAttribute("use-persistence-mechanism", 0);
	}
	
	public boolean visit(MethodInvocation node) {
		boolean wasInvokedInADependency = node.getExpression()!=null;
		if(wasInvokedInADependency && PersistenceAPIs.isOnPersistenceMechanism(findType(node))) {
			clazz.setAttribute("use-persistence-mechanism", 1);
		}
		
		return super.visit(node);
	}

}
