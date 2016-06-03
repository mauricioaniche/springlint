package org.springlint;

import java.io.FileNotFoundException;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.log4j.Logger;
import org.springlint.architecture.Architecture;
import org.springlint.architecture.SpringMVCArchitecture;
import org.springlint.output.CSVOutput;
import org.springlint.output.HTMLOutput;
import org.springlint.output.Output;

public class SpringLint {
	
	private static Logger log = Logger.getLogger(SpringLint.class);

	public static void main(String[] args) throws FileNotFoundException, ParseException {
		Options opts = new Options();
		opts.addOption("o", "output", true, "Path to the output. Should be a dir ending with /");
		opts.addOption("otype", true, "Type of the output: 'csv', 'html'");
		opts.addOption("p", "project", true, "Path to the project");
		
		CommandLineParser parser = new DefaultParser();
		CommandLine cmd = parser.parse(opts, args);
		
		boolean missingArgument = 
				!cmd.hasOption("output") || 
				!cmd.hasOption("otype") || 
				!cmd.hasOption("project"); 

		if(missingArgument) {
			HelpFormatter formatter = new HelpFormatter();
			formatter.printHelp("smellycat", opts);
			System.exit(-1);
		}
		
		Architecture arch = new SpringMVCArchitecture();
		String projectPath = cmd.getOptionValue("project");
		String outputPath = cmd.getOptionValue("output");
		
		Output output;
		if(cmd.getOptionValue("otype").equals("html")) {
			output = new HTMLOutput(outputPath);
		} else {
			output = new CSVOutput(outputPath);
		}
		
		long startTime = System.currentTimeMillis();
		log.info("# ----------------------------------------- #");
		log.info("#          Smelly Cat - Spring MVC          #");
		log.info("#  www.github.com/mauricioaniche/smellycat  #");
		log.info("# ----------------------------------------- #");
		
		new RunAllAnalysis(arch, projectPath, output).run();
		
		long endTime = System.currentTimeMillis();
		long time = (endTime - startTime) / 1000;
		log.info(String.format("That's it! It only took %d seconds", time));


	}
	
}
