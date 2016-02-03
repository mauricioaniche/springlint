package org.smellycat.springmvc;

import java.io.FileNotFoundException;
import java.io.PrintStream;

import org.apache.log4j.Logger;
import org.smellycat.springmvc.ar.ArchitecturalRoleRequestor;
import org.smellycat.springmvc.domain.Repository;
import org.smellycat.springmvc.domain.SmellyClass;
import org.smellycat.springmvc.smells.SmellsRequestor;
import org.smellycat.springmvc.smells.controller.PromiscuousController;

public class SmellyCat {
	
	private static Logger log = Logger.getLogger(SmellyCat.class);
	private PrintStream output;

	private Repository repo;

	private Parser parser;
	private String projectPath;

	public static void main(String[] args) throws FileNotFoundException {
//		String projectPath = "/Users/mauricioaniche/Desktop/projects/SSP";
//		String outputPath = "/Users/mauricioaniche/Desktop/projects/tool.csv";
		String projectPath = args[0];
		String outputPath = args[1];

		PrintStream output = new PrintStream(outputPath);
		Repository repo = new Repository();
		new SmellyCat(projectPath, output, repo).execute();
		output.close();
	}
	
	public SmellyCat(String projectPath, PrintStream output, Repository repo) {
		this.projectPath = projectPath;
		this.output = output;
		this.repo = repo;
	}
	
	public void execute() {
		long startTime = System.currentTimeMillis();
		log.info("# ----------------------------- #");
		log.info("#    Smelly Cat - Spring MVC    #");
		log.info("# ----------------------------- #");

		log.info("Starting the parse engine");
		parser = new Parser(projectPath);
		
		identifyRoles();
		searchSmells();
		printAttributes();
		
		long endTime = System.currentTimeMillis();
		long time = (endTime - startTime) / 1000;
		log.info(String.format("That's it! It only took %d seconds", time));
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
