package org.smellycat.springmvc.ar;

import org.apache.log4j.Logger;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.FileASTRequestor;
import org.smellycat.springmvc.domain.Repository;
import org.smellycat.springmvc.domain.SmellyClass;

public class ArchitecturalRoleRequestor extends FileASTRequestor {

	private Repository repo;
	private static Logger log = Logger.getLogger(ArchitecturalRoleRequestor.class);

	public ArchitecturalRoleRequestor(Repository repo) {
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
			ArchitecturalRoleVisitor visitor = new ArchitecturalRoleVisitor();
			cu.accept(visitor);
			
			clazz.setRole(visitor.getRole());
			log.info(String.format("-- %s is a %s (%s)", clazz.getName(), clazz.getRole(), clazz.getType()));
		} catch(Exception e) {
			// just ignore... sorry!
			log.error("error in " + sourceFilePath, e);
		}
	}
	
}
