package org.smellycat.analysis.smells;

import org.apache.log4j.Logger;
import org.smellycat.architecture.Architecture;
import org.smellycat.domain.Repository;

public class SmellAnalysis {

	private String projectPath;
	private Repository repo;
	private Parser parser;
	
	private static Logger log = Logger.getLogger(SmellAnalysis.class);
	private Architecture arch;

	public SmellAnalysis(Architecture arch, String projectPath) {
		this.arch = arch;
		this.projectPath = projectPath;
		this.repo = new Repository();
	}

	public Repository run() {

		log.info("Starting the parse engine");
		parser = new Parser(projectPath);
		
		identifyRoles();
		searchSmells();
		
		return repo;
		
	}

	private void searchSmells() {
		log.info("Identifying smells...");
		parser.execute(new SmellsRequestor(repo, 
			arch.smells()));
	}

	private void identifyRoles() {
		log.info("Identifying roles...");
		parser.execute(new ArchitecturalRoleRequestor(arch, repo));
	}	
	

}
