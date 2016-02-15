package org.smellycat.analysis.smells.springmvc.service;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.jdt.core.dom.MethodInvocation;
import org.smellycat.analysis.smells.VariablesAndFieldsVisitor;
import org.smellycat.domain.SmellyClass;

public class UsePersistenceMechanismVisitor extends VariablesAndFieldsVisitor {

	private SmellyClass clazz;
	
	private static Set<String> persistenceApis;

	static {
		persistenceApis = new HashSet<String>();
		persistenceApis.add("java.sql");
		persistenceApis.add("javax.persistence");
		persistenceApis.add("org.hibernate");
		persistenceApis.add("org.springframework.data");
		
	}
	
	public UsePersistenceMechanismVisitor(SmellyClass clazz) {
		this.clazz = clazz;
		clazz.setAttribute("use-persistence-mechanism", 0);
	}
	
	public boolean visit(MethodInvocation node) {
		boolean wasInvokedInADependency = node.getExpression()!=null;
		if(wasInvokedInADependency && isPersistenceMethod(findType(node))) {
			clazz.setAttribute("use-persistence-mechanism", 1);
		}
		
		return super.visit(node);
	}

	private boolean isPersistenceMethod(String type) {
		
		return persistenceApis.stream().anyMatch(api -> type.startsWith(api));
	}

}
