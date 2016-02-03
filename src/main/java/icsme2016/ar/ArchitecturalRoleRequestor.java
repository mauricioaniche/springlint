package icsme2016.ar;

import org.apache.log4j.Logger;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.FileASTRequestor;

import br.com.aniche.ck.metric.ClassInfo;
import icsme2016.domain.Repository;
import icsme2016.domain.SmellyClass;

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
			ClassInfo info = new ClassInfo();
			cu.accept(info);
			if(info.getClassName()==null) return;
		
			SmellyClass clazz = repo.add(sourceFilePath, info.getClassName(), info.getType());
			ArchitecturalRoleVisitor visitor = new ArchitecturalRoleVisitor();
			cu.accept(visitor);
			
			clazz.setRole(visitor.getRole());
			log.info(String.format("-- %s is a %s", clazz.getName(), clazz.getRole()));
		} catch(Exception e) {
			// just ignore... sorry!
			log.error("error in " + sourceFilePath, e);
		}
	}
	
}
