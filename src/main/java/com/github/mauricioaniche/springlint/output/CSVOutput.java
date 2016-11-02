package com.github.mauricioaniche.springlint.output;

import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.List;
import java.util.Map;

import com.github.mauricioaniche.springlint.analysis.smells.SmellDescription;
import com.github.mauricioaniche.springlint.architecture.Architecture;
import com.github.mauricioaniche.springlint.domain.Repository;
import com.github.mauricioaniche.springlint.domain.SmellyClass;

import br.com.aniche.ck.CKNumber;

public class CSVOutput implements Output{
	
	private PrintStream output;

	public CSVOutput(String outputPath) throws FileNotFoundException {
		this.output = new PrintStream(outputPath+(outputPath.endsWith("/")?"":"/") + "smells.csv"); 
	}

	@Override
	public void printOutput(Architecture arch, Map<String, List<CKNumber>> ckResults, Repository smellResults) {
		
		output.println("file,name,role,smell,note");
		for(SmellyClass clazz : smellResults.all()) {
			for(SmellDescription description : clazz.getSmells()) {
				output.println(
					clazz.getFile() + "," +
					clazz.getName() + "," +
					clazz.getRole().name() + "," +
					description.getName() + "," +
					description.getDescription()
				);
			}
		}		
		
		output.close();
	}

}
