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
import org.smellycat.architecture.Architecture;
import org.smellycat.architecture.ArchitectureFactory;

public class SmellyCat {
	
	private static Logger log = Logger.getLogger(SmellyCat.class);

	public static void main(String[] args) throws FileNotFoundException, ParseException {
		Options opts = new Options();
		opts.addOption("arch", true, "Architecture ('springmvc', 'android')");
		opts.addOption("o", "output", true, "Path to the output. It should end with '.html'");
		opts.addOption("p", "project", true, "Path to the project");
		
		CommandLineParser parser = new DefaultParser();
		CommandLine cmd = parser.parse(opts, args);
		
		boolean missingArgument = 
				!cmd.hasOption("arch") || 
				!cmd.hasOption("output") || 
				!cmd.hasOption("project"); 

		if(missingArgument) {
			HelpFormatter formatter = new HelpFormatter();
			formatter.printHelp("smellycat", opts);
			System.exit(-1);
		}
		
		boolean invalidParams = 
				(!cmd.getOptionValue("arch").equals("springmvc") && !cmd.getOptionValue("arch").equals("android"));

		if(invalidParams) {
			System.out.println("invalid parameter");
			HelpFormatter formatter = new HelpFormatter();
			formatter.printHelp("smellycat", opts);
			System.exit(-1);
		}
		
		Architecture arch = new ArchitectureFactory().build(cmd.getOptionValue("arch"));
		String projectPath = cmd.getOptionValue("project");
		String outputPath = cmd.getOptionValue("output");
		PrintStream output = new PrintStream(outputPath);
		
		long startTime = System.currentTimeMillis();
		log.info("# ----------------------------------------- #");
		log.info("#          Smelly Cat - Spring MVC          #");
		log.info("#  www.github.com/mauricioaniche/smellycat  #");
		log.info("# ----------------------------------------- #");
		
		new RunAllAnalysis(arch, projectPath, output).run();
		
		output.close();
		long endTime = System.currentTimeMillis();
		long time = (endTime - startTime) / 1000;
		log.info(String.format("That's it! It only took %d seconds", time));


	}
	
}
