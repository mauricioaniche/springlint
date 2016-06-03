package com.github.mauricioaniche.springlint.analysis.smells;

import org.apache.log4j.Logger;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.FileASTRequestor;

import com.github.mauricioaniche.springlint.architecture.ArchitecturalRoleVisitor;
import com.github.mauricioaniche.springlint.architecture.Architecture;
import com.github.mauricioaniche.springlint.domain.Repository;
import com.github.mauricioaniche.springlint.domain.SmellyClass;
import com.github.mauricioaniche.springlint.utils.jdt.ClassInfoVisitor;

public class ArchitecturalRoleRequestor extends FileASTRequestor {

	private Repository repo;
	private static Logger log = Logger.getLogger(ArchitecturalRoleRequestor.class);
	private Architecture arch;

	public ArchitecturalRoleRequestor(Architecture arch, Repository repo) {
		this.arch = arch;
		this.repo = repo;
	}
	
	@Override
	public void acceptAST(String sourceFilePath, 
			CompilationUnit cu) {
		
		try {
			ClassInfoVisitor info = new ClassInfoVisitor();
			cu.accept(info);
			if(info.getClassName()==null) return;
		
			SmellyClass clazz = repo.add(sourceFilePath, info.getClassName(), info.getType(), info.getSuperclass(), info.getInterfaces());
			ArchitecturalRoleVisitor visitor = arch.roleVisitor();
			cu.accept(visitor);
			
			clazz.setRole(visitor.getRole());
			log.info(String.format("-- %s is a %s (%s)", clazz.getName(), clazz.getRole(), clazz.getType()));
		} catch(Exception e) {
			// just ignore... sorry!
			log.error("error in " + sourceFilePath, e);
		}
	}
	
}
