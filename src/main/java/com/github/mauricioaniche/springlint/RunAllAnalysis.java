package com.github.mauricioaniche.springlint;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Map;

import com.github.mauricioaniche.springlint.analysis.ck.CKAnalysis;
import com.github.mauricioaniche.springlint.analysis.smells.SmellAnalysis;
import com.github.mauricioaniche.springlint.architecture.Architecture;
import com.github.mauricioaniche.springlint.domain.Repository;
import com.github.mauricioaniche.springlint.output.Output;

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
