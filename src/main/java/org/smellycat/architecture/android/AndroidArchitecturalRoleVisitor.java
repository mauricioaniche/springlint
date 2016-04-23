package org.smellycat.architecture.android;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.ITypeBinding;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.smellycat.architecture.ArchitecturalRole;
import org.smellycat.architecture.ArchitecturalRoleVisitor;

import br.com.aniche.ck.CKNumber;
import br.com.aniche.ck.CKReport;

public class AndroidArchitecturalRoleVisitor extends ArchitecturalRoleVisitor {

	private String superclass = null;

	public boolean visit(TypeDeclaration node) {
		ITypeBinding binding = node.resolveBinding();
		
		if(superclass == null)
			if(binding!=null && binding.getSuperclass() !=null && superclass==null) 
				superclass = binding.getSuperclass().getQualifiedName();
		
		return super.visit(node);
	}
	
	public ArchitecturalRole getRole() {
		
		if(superclass==null) return ArchitecturalRole.OTHER;
		if(superclass.contains("AsyncTask")) return AndroidArchitecture.TASK;
		if(superclass.endsWith("Activity")) return AndroidArchitecture.ACTIVITY;
		if(superclass.endsWith("Fragment")) return AndroidArchitecture.FRAGMENT;
		return ArchitecturalRole.OTHER;
	}

	@Override
	public void execute(CompilationUnit cu, CKReport report) {
		cu.accept(this);
	}

	@Override
	public void setResult(CKNumber result) {
		result.addSpecific("role", getRole().id());
	}

}
