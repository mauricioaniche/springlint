package org.smellycat.output;

import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.List;
import java.util.Map;

import org.smellycat.analysis.smells.SmellDescription;
import org.smellycat.architecture.Architecture;
import org.smellycat.domain.Repository;
import org.smellycat.domain.SmellyClass;

import br.com.aniche.ck.CKNumber;

public class CSVOutput implements Output{
	
	private PrintStream output;

	public CSVOutput(String outputPath) throws FileNotFoundException {
		this.output = new PrintStream(outputPath + "smells.csv"); 
	}

	@Override
	public void printOutput(Architecture arch, Map<String, List<CKNumber>> ckResults, Repository smellResults) {
		
		output.println("project,file,name,role,smell,note");
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
