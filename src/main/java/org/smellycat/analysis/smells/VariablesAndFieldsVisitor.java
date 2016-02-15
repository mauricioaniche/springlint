package org.smellycat.analysis.smells;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.FieldDeclaration;
import org.eclipse.jdt.core.dom.ImportDeclaration;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.eclipse.jdt.core.dom.VariableDeclarationFragment;
import org.eclipse.jdt.core.dom.VariableDeclarationStatement;

public class VariablesAndFieldsVisitor extends ASTVisitor {
	protected Map<String, String> localVariables;
	protected Map<String, String> fieldVariables;
	protected Map<String, String> importList;
	
	public VariablesAndFieldsVisitor() {
		this.localVariables = new HashMap<String, String>();
		this.fieldVariables = new HashMap<String, String>();
		this.importList = new HashMap<String, String>();
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
	
	public boolean visit(MethodDeclaration node) {
		localVariables.clear();
		
		return super.visit(node);
	}

	protected String findType(MethodInvocation node) {
		String variableInvoked = node.getExpression().toString();
		String type;
		
		if(localVariables.containsKey(variableInvoked)) 
			type = localVariables.get(variableInvoked);
		else if(fieldVariables.containsKey(variableInvoked)) 
			type = fieldVariables.get(variableInvoked);
		else type = "";
		return type;
	}
	
	
}
