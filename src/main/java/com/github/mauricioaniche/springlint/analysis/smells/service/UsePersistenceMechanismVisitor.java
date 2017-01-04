package com.github.mauricioaniche.springlint.analysis.smells.service;

import org.eclipse.jdt.core.dom.MethodInvocation;

import com.github.mauricioaniche.springlint.analysis.smells.VariablesAndFieldsVisitor;
import com.github.mauricioaniche.springlint.analysis.smells.repository.PersistenceAPIs;
import com.github.mauricioaniche.springlint.domain.SmellyClass;

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
