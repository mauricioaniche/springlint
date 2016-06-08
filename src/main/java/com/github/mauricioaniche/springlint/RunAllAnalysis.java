package com.github.mauricioaniche.springlint;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.github.mauricioaniche.springlint.analysis.ck.CKAnalysis;
import com.github.mauricioaniche.springlint.analysis.smells.SmellAnalysis;
import com.github.mauricioaniche.springlint.architecture.Architecture;
import com.github.mauricioaniche.springlint.domain.Repository;
import com.github.mauricioaniche.springlint.output.Output;

import br.com.aniche.ck.CKNumber;

public class RunAllAnalysis {
	
	private static Logger log = Logger.getLogger(RunAllAnalysis.class);
	
	private Architecture arch;
	private String projectPath;
	private Output output;

	public RunAllAnalysis(Architecture arch, String projectPath, Output output) {
		this.arch = arch;
		this.projectPath = projectPath;
		this.output = output;
		
	}

	public void run() throws FileNotFoundException {
		CKAnalysis ck = new CKAnalysis(arch,projectPath);
		
		log.info("Running CK metrics");
		Map<String, List<CKNumber>> ckResults = ck.run();
		
		log.info("Looking for smells");
		SmellAnalysis smells = new SmellAnalysis(arch, projectPath);
		Repository smellResults = smells.run();
		
		log.info("Generating output");
		output.printOutput(arch, ckResults, smellResults);
	}


}
