package org.smellycat.springmvc;

import java.io.PrintStream;

import org.apache.log4j.Logger;
import org.smellycat.springmvc.ar.ArchitecturalRoleRequestor;
import org.smellycat.springmvc.domain.Repository;
import org.smellycat.springmvc.domain.SmellyClass;
import org.smellycat.springmvc.smells.SmellsRequestor;
import org.smellycat.springmvc.smells.controller.PromiscuousController;

public class SmellAnalysis {

	private String projectPath;
	private PrintStream output;
	private Repository repo;
	private Parser parser;
	
	private static Logger log = Logger.getLogger(SmellAnalysis.class);

	public SmellAnalysis(String projectPath, PrintStream output, Repository repo) {
		this.projectPath = projectPath;
		this.output = output;
		this.repo = repo;
	}

	public void run() {

		log.info("Starting the parse engine");
		parser = new Parser(projectPath);
		
		identifyRoles();
		searchSmells();
		printAttributes();
		
	}

	private void printAttributes() {
		log.info("Saving the results...");
		for(SmellyClass clazz : repo.all()) {
			output.println(
				clazz.getFile() + "," +
				clazz.getName() + "," +
				clazz.getRole() + "," +
				clazz.getAttribute("number-of-routes") + "," +
				clazz.getAttribute("number-of-services-as-dependencies")
			);
		}
	}

	private void searchSmells() {
		log.info("Identifying smells...");
		parser.execute(new SmellsRequestor(repo, new PromiscuousController()));
	}

	private void identifyRoles() {
		log.info("Identifying roles...");
		parser.execute(new ArchitecturalRoleRequestor(repo));
	}	
	

}
