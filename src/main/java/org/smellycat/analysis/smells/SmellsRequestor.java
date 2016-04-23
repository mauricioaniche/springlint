package org.smellycat.analysis.smells;

import java.util.List;
import java.util.concurrent.Callable;

import org.apache.log4j.Logger;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.FileASTRequestor;
import org.smellycat.domain.Repository;
import org.smellycat.domain.SmellyClass;

public class SmellsRequestor extends FileASTRequestor {

	private static Logger log = Logger.getLogger(SmellsRequestor.class);
	
	private Repository repo;
	private List<Smell> smells;

	public SmellsRequestor(Repository repo, List<Smell> smells) {
		this.repo = repo;
		this.smells = smells;
	}
	
	@Override
	public void acceptAST(String sourceFilePath, 
			CompilationUnit cu) {
		
		try {
		
			SmellyClass clazz = repo.get(sourceFilePath);
			log.info("Analysing class " + clazz.getName());
			
			for(Smell smell : smells) {
				log.debug("-- Smell detector: " + smell.getClass().getSimpleName());
				for(Callable<ASTVisitor> visitorInstantiator : smell.analyzers(repo, clazz)) {
					ASTVisitor visitor = visitorInstantiator.call();
					log.debug("--- Calculating " + visitor.getClass().getSimpleName());
					cu.accept(visitor);
				}
				
				boolean foundASmell = smell.conciliate(clazz);
				if(foundASmell) log.info("-- SMELL DETECTED!");
			}
			
		} catch(Throwable e) {
			// just ignore... sorry!
			log.error("error in " + sourceFilePath, e);
		}
	}

}
