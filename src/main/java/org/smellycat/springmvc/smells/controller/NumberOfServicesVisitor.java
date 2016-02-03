package org.smellycat.springmvc.smells.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.IBinding;
import org.eclipse.jdt.core.dom.ITypeBinding;
import org.eclipse.jdt.core.dom.SimpleName;
import org.smellycat.springmvc.domain.ArchitecturalRole;
import org.smellycat.springmvc.domain.Repository;
import org.smellycat.springmvc.domain.SmellyClass;

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
			
			SmellyClass possibleService = getPossibleService(tb);
			
			boolean notMySelf = possibleService!= null && !possibleService.equals(clazz);
			if(possibleService!= null && possibleService.is(ArchitecturalRole.SERVICE) && notMySelf) {
				services.add(className);
				clazz.setAttribute("number-of-services-as-dependencies", services.size());
			}
		}
		
		return super.visit(node);
	}

	private SmellyClass getPossibleService(ITypeBinding tb) {
		String className = tb.getQualifiedName();
		SmellyClass possibleService;
		if(tb.isInterface()) {
			List<SmellyClass> subtypes = repo.getSubtypesOf(className);
			possibleService = findService(subtypes);
		} else {
			possibleService = repo.getByClass(className);
		}
		return possibleService;
	}

	private SmellyClass findService(List<SmellyClass> subtypes) {
		for(SmellyClass subtype : subtypes) {
			if(subtype.is(ArchitecturalRole.SERVICE)) return subtype;
		}
		return null;
	}

}
