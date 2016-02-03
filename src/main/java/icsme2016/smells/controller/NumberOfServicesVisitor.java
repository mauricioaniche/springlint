package icsme2016.smells.controller;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.IBinding;
import org.eclipse.jdt.core.dom.ITypeBinding;
import org.eclipse.jdt.core.dom.SimpleName;

import icsme2016.domain.ArchitecturalRole;
import icsme2016.domain.Repository;
import icsme2016.domain.SmellyClass;

public class NumberOfServicesVisitor extends ASTVisitor {

	private SmellyClass clazz;
	private Repository repo;
	private Set<String> services;

	public NumberOfServicesVisitor(Repository repo, SmellyClass clazz) {
		this.repo = repo;
		this.clazz = clazz;
		
		this.services = new HashSet<String>();
		clazz.setAttribute("number-of-services-as-dependencies", 0);
	}
	
	@Override
	public boolean visit(SimpleName node) {
		
		IBinding binding = node.resolveBinding();
		if(binding!=null && binding instanceof ITypeBinding) {
			ITypeBinding tb = (ITypeBinding) binding;
			String className = tb.getQualifiedName();
			
			SmellyClass possibleService = repo.getByClass(className);
			boolean notMySelf = possibleService!= null && !possibleService.equals(clazz);
			if(possibleService!= null && possibleService.is(ArchitecturalRole.SERVICE) && notMySelf) {
				services.add(className);
				clazz.setAttribute("number-of-services-as-dependencies", services.size());
			}
		}
		
		return super.visit(node);
	}

}
