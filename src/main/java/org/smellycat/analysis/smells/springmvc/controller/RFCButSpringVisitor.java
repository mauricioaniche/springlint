package org.smellycat.analysis.smells.springmvc.controller;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.FieldDeclaration;
import org.eclipse.jdt.core.dom.ImportDeclaration;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.eclipse.jdt.core.dom.VariableDeclarationFragment;
import org.eclipse.jdt.core.dom.VariableDeclarationStatement;
import org.smellycat.domain.SmellyClass;

public class RFCButSpringVisitor extends ASTVisitor {

	private HashSet<String> methodInvocations;
	
	private Map<String, String> localVariables;
	private Map<String, String> fieldVariables;
	private Map<String, String> importList;
	
	private SmellyClass clazz;

	public RFCButSpringVisitor(SmellyClass clazz) {
		this.clazz = clazz;
		this.localVariables = new HashMap<String, String>();
		this.fieldVariables = new HashMap<String, String>();
		this.importList = new HashMap<String, String>();
		this.methodInvocations = new HashSet<String>();
		
		update(clazz);
	}

	private void update(SmellyClass clazz) {
		clazz.setAttribute("rfc-but-spring", methodInvocations.size());
	}

	public boolean visit(MethodInvocation node) {
		boolean wasInvokedInADependency = node.getExpression()!=null;
		if(wasInvokedInADependency && !belongsToSpring(node)) {
			methodInvocations.add(node.getName()  + "/" + node.arguments().size());
		}
		
		update(clazz);
		
		return super.visit(node);
	}

	private boolean belongsToSpring(MethodInvocation node) {
		String variableInvoked = node.getExpression().toString();
		String type;
		
		if(localVariables.containsKey(variableInvoked)) 
			type = localVariables.get(variableInvoked);
		else if(fieldVariables.containsKey(variableInvoked)) 
			type = fieldVariables.get(variableInvoked);
		else type = "";
		
		return type.contains(".springframework.");
	}
	
	public boolean visit(MethodDeclaration node) {
		localVariables.clear();
		
		return super.visit(node);
	}
	
	public boolean visit(ImportDeclaration node) {
		
		String importName = node.getName().toString();
		String[] splittedImportName = importName.split("\\.");
		String clazzName = splittedImportName[splittedImportName.length-1];
		
		importList.put(clazzName, importName);
		
		return super.visit(node);
	}
	
	public boolean visit(FieldDeclaration node) {
		
		String simpleType = node.getType().toString();
		for(Object o : node.fragments()) {
			VariableDeclarationFragment fragment = (VariableDeclarationFragment) o;
			
			String variableName = fragment.getName().toString();
			String fullType = importList.containsKey(simpleType) ? importList.get(simpleType) : simpleType;
			fieldVariables.put(variableName, fullType);
		}
		
		return super.visit(node);
	}
	
	public boolean visit(VariableDeclarationStatement node) {
		
		String simpleType = node.getType().toString();
		for(Object o : node.fragments()) {
			VariableDeclarationFragment fragment = (VariableDeclarationFragment) o;

			String variableName = fragment.getName().toString();
			String fullType = importList.containsKey(simpleType) ? importList.get(simpleType) : simpleType;
			localVariables.put(variableName, fullType);
		}

		return true;
	}

	
}
