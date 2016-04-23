package org.smellycat;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.smellycat.analysis.ck.CKAnalysis;
import org.smellycat.analysis.smells.SmellAnalysis;
import org.smellycat.architecture.Architecture;
import org.smellycat.domain.Repository;

import com.google.gson.Gson;

import br.com.aniche.ck.CKNumber;

public class RunAllAnalysis {
	
	private PrintStream output;
	private Architecture arch;
	private String projectPath;

	public RunAllAnalysis(Architecture arch, String projectPath, PrintStream output) {
		this.arch = arch;
		this.projectPath = projectPath;
		this.output = output;
		
	}

	public void run() throws FileNotFoundException {
		CKAnalysis ck = new CKAnalysis(arch,projectPath);
		Map<String, List<CKNumber>> ckResults = ck.run();
		
		SmellAnalysis smells = new SmellAnalysis(arch, projectPath);
		Repository smellResults = smells.run();
		
		printOutput(ckResults, smellResults);
		
	}

	private void printOutput(Map<String, List<CKNumber>> ckResults, Repository smellResults) {

		String ckResultsInJson = new Gson().toJson(ckResults);
		String smellResultsInJson = new Gson().toJson(smellResults.all());
		
		try {
			String thresholds = readResource("/output/" + arch.thresholdsFile());
			String html = readResource("/output/result.html");
			html = html
				.replace("##projectDir##", projectPath)
				.replace("##ck##", ckResultsInJson)
				.replace("##archThresholds##", thresholds)
				.replace("##smells##", smellResultsInJson)
				.replace("##date##", new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime()));
			
			output.print(html);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	private String readResource(String name) throws IOException {
		InputStream is = CKAnalysis.class.getResource(name).openStream();
		
		StringWriter writer = new StringWriter();
		IOUtils.copy(is, writer);
		String content = writer.toString();
		is.close();
		
		return content;
	}
}
