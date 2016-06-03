package org.springlint;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Map;

import org.springlint.analysis.ck.CKAnalysis;
import org.springlint.analysis.smells.SmellAnalysis;
import org.springlint.architecture.Architecture;
import org.springlint.domain.Repository;
import org.springlint.output.Output;

import br.com.aniche.ck.CKNumber;

public class RunAllAnalysis {
	
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
		Map<String, List<CKNumber>> ckResults = ck.run();
		
		SmellAnalysis smells = new SmellAnalysis(arch, projectPath);
		Repository smellResults = smells.run();
		
		output.printOutput(arch, ckResults, smellResults);
	}


}
