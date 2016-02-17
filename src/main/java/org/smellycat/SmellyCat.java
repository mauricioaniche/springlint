package org.smellycat;

import java.io.FileNotFoundException;
import java.io.PrintStream;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.log4j.Logger;
import org.smellycat.analysis.ck.CKAnalysis;
import org.smellycat.analysis.smells.SmellAnalysis;
import org.smellycat.architecture.Architecture;
import org.smellycat.architecture.ArchitectureFactory;
import org.smellycat.domain.Repository;

public class SmellyCat {
	
	private static Logger log = Logger.getLogger(SmellyCat.class);

	public static void main(String[] args) throws FileNotFoundException, ParseException {
		Options opts = new Options();
		opts.addOption("arch", true, "Architecture ('springmvc', 'android')");
		opts.addOption("o", "output", true, "Path to the output. It should end with '.html'");
		opts.addOption("p", "project", true, "Path to the project");
		opts.addOption("a", "analysis", true, "Type of the analysis ('ck', 'smell'), or 'smell-attributes' for debug purposes.");
		
		CommandLineParser parser = new DefaultParser();
		CommandLine cmd = parser.parse(opts, args);
		
		boolean missingArgument = 
				!cmd.hasOption("arch") || 
				!cmd.hasOption("output") || 
				!cmd.hasOption("project") || 
				!cmd.hasOption("analysis");

		if(missingArgument) {
			HelpFormatter formatter = new HelpFormatter();
			formatter.printHelp("smellycat", opts);
			System.exit(-1);
		}
		
		boolean invalidParams = 
				(!cmd.getOptionValue("analysis").equals("ck") && !cmd.getOptionValue("analysis").equals("smell") && !cmd.getOptionValue("analysis").equals("smell-attributes")) ||
				(!cmd.getOptionValue("arch").equals("springmvc") && !cmd.getOptionValue("arch").equals("android"));

		if(invalidParams) {
			System.out.println("invalid parameter");
			HelpFormatter formatter = new HelpFormatter();
			formatter.printHelp("smellycat", opts);
			System.exit(-1);
		}
		
		String analysis = cmd.getOptionValue("analysis");
		Architecture arch = new ArchitectureFactory().build(cmd.getOptionValue("arch"));
		String projectPath = cmd.getOptionValue("project");
		String outputPath = cmd.getOptionValue("output");
		PrintStream output = new PrintStream(outputPath);
		
		long startTime = System.currentTimeMillis();
		log.info("# ----------------------------------------- #");
		log.info("#          Smelly Cat - Spring MVC          #");
		log.info("#  www.github.com/mauricioaniche/smellycat  #");
		log.info("# ----------------------------------------- #");
		if(analysis.equals("ck")) {
			new CKAnalysis(arch,projectPath, output).run();
		}
		else if(analysis.equals("smell")) {
			Repository repo = new Repository();
			new SmellAnalysis(arch, projectPath, output, repo, true).run();
		}
		else if(analysis.equals("smell-attributes")) {
			Repository repo = new Repository();
			new SmellAnalysis(arch, projectPath, output, repo, false).run();
		}
		
		output.close();
		long endTime = System.currentTimeMillis();
		long time = (endTime - startTime) / 1000;
		log.info(String.format("That's it! It only took %d seconds", time));


	}
	
}
