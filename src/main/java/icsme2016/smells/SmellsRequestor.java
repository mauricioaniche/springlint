package icsme2016.smells;

import java.util.concurrent.Callable;

import org.apache.log4j.Logger;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.FileASTRequestor;

import icsme2016.domain.Repository;
import icsme2016.domain.SmellyClass;

public class SmellsRequestor extends FileASTRequestor {

	private static Logger log = Logger.getLogger(SmellsRequestor.class);
	
	private Repository repo;
	private Smell[] smells;

	public SmellsRequestor(Repository repo, Smell... smells) {
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
				log.info("-- Smell detector: " + clazz.getClass().getSimpleName());
				for(Callable<ASTVisitor> visitorInstantiator : smell.analyzers(repo, clazz)) {
					ASTVisitor visitor = visitorInstantiator.call();
					log.info("--- Calculating " + visitor.getClass().getSimpleName());
					cu.accept(visitor);
				}
				
				boolean foundASmell = smell.conciliate(clazz);
				if(foundASmell) log.info("-- SMELL DETECTED!");
			}
			
		} catch(Exception e) {
			// just ignore... sorry!
			log.error("error in " + sourceFilePath, e);
		}
	}

}
