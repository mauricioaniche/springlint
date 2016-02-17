package org.smellycat.analysis.smells;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.IBinding;
import org.eclipse.jdt.core.dom.ITypeBinding;
import org.eclipse.jdt.core.dom.SimpleName;
import org.smellycat.architecture.ArchitecturalRole;
import org.smellycat.domain.Repository;
import org.smellycat.domain.SmellyClass;

public class ArchitecturalRoleDependenciesCountVisitor extends ASTVisitor {

	private SmellyClass clazz;
	private Repository repo;
	private Set<String> roles;
	private ArchitecturalRole ar;
	private String attributeName;

	public ArchitecturalRoleDependenciesCountVisitor(Repository repo, SmellyClass clazz, ArchitecturalRole ar, String attributeName) {
		this.repo = repo;
		this.clazz = clazz;
		this.ar = ar;
		this.attributeName = attributeName;
		
		this.roles = new HashSet<String>();
		clazz.setAttribute(attributeName, 0);
	}
	
	@Override
	public boolean visit(SimpleName node) {
		
		IBinding binding = node.resolveBinding();
		if(binding!=null && binding instanceof ITypeBinding) {
			ITypeBinding tb = (ITypeBinding) binding;
			String className = tb.getQualifiedName();
			
			SmellyClass possibleRole = getPossibleRole(tb);
			
			boolean notMySelf = possibleRole!= null && !possibleRole.equals(clazz);
			if(possibleRole!= null && possibleRole.is(ar) && notMySelf) {
				clazz.appendNote(attributeName + "-violations", className);
				roles.add(className);
				
				clazz.setAttribute(attributeName, roles.size());
			}
		}
		
		return super.visit(node);
	}

	private SmellyClass getPossibleRole(ITypeBinding tb) {
		String className = tb.getQualifiedName();
		SmellyClass possibleService;
		if(tb.isInterface()) {
			List<SmellyClass> subtypes = repo.getSubtypesOf(className);
			possibleService = findRole(subtypes);
		} else {
			possibleService = repo.getByClass(className);
		}
		return possibleService;
	}

	private SmellyClass findRole(List<SmellyClass> subtypes) {
		Optional<SmellyClass> sc = subtypes.stream().filter(s -> s.is(ar)).findFirst();
		return sc.orElse(null);
	}

}
