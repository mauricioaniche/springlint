package org.smellycat.analysis.smells;

import java.io.PrintStream;

import org.apache.log4j.Logger;
import org.smellycat.analysis.smells.springmvc.controller.PromiscuousController;
import org.smellycat.analysis.smells.springmvc.controller.SmartController;
import org.smellycat.analysis.smells.springmvc.repository.FatRepository;
import org.smellycat.analysis.smells.springmvc.repository.MultipleQueries;
import org.smellycat.analysis.smells.springmvc.repository.SmartRepository;
import org.smellycat.analysis.smells.springmvc.service.DBQueryingService;
import org.smellycat.architecture.Architecture;
import org.smellycat.domain.Repository;
import org.smellycat.domain.SmellyClass;

public class SmellAnalysis {

	private String projectPath;
	private PrintStream output;
	private Repository repo;
	private Parser parser;
	
	private static Logger log = Logger.getLogger(SmellAnalysis.class);
	private Architecture arch;

	public SmellAnalysis(Architecture arch, String projectPath, PrintStream output, Repository repo) {
		this.arch = arch;
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
		
		output.println("project,file,name,role,routes,services,entities,sqlcomplexity,mccabe,springrfc,persistence,persistinvocations");
		for(SmellyClass clazz : repo.all()) {
			output.println(
				projectPath + "," +
				clazz.getFile() + "," +
				clazz.getName() + "," +
				clazz.getRole().name() + "," +
				clazz.getAttribute("number-of-routes") + "," +
				clazz.getAttribute("number-of-services-as-dependencies") + "," +
				clazz.getAttribute("number-of-entities-as-dependencies") + "," +
				clazz.getAttribute("sql-complexity") + "," +
				clazz.getAttribute("mccabe") + "," +
				clazz.getAttribute("rfc-but-spring") + "," +
				clazz.getAttribute("use-persistence-mechanism") + "," +
				clazz.getAttribute("multiple-persistence-invocations")
			);
		}
	}

	private void searchSmells() {
		// TODO: inject all smells automatically
		log.info("Identifying smells...");
		parser.execute(new SmellsRequestor(repo, 
			new PromiscuousController(), new SmartController(), new SmartRepository(), new FatRepository(), new DBQueryingService(), new MultipleQueries()));
	}

	private void identifyRoles() {
		log.info("Identifying roles...");
		parser.execute(new ArchitecturalRoleRequestor(arch, repo));
	}	
	

}
