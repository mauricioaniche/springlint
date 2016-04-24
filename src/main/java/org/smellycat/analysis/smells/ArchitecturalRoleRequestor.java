package org.smellycat.analysis.smells;

import org.apache.log4j.Logger;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.FileASTRequestor;
import org.smellycat.architecture.ArchitecturalRoleVisitor;
import org.smellycat.architecture.Architecture;
import org.smellycat.domain.Repository;
import org.smellycat.domain.SmellyClass;
import org.smellycat.utils.jdt.ClassInfoVisitor;

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
