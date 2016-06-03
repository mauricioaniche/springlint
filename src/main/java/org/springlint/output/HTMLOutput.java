package org.springlint.output;

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
import org.springlint.analysis.ck.CKAnalysis;
import org.springlint.architecture.Architecture;
import org.springlint.domain.Repository;

import com.google.gson.Gson;

import br.com.aniche.ck.CKNumber;

public class HTMLOutput implements Output {
	
	private PrintStream output;

	public HTMLOutput(String output) throws FileNotFoundException {
		this.output = new PrintStream(output+"result.html");
	}

	public void printOutput(Architecture arch, Map<String, List<CKNumber>> ckResults, Repository smellResults) {

		String ckResultsInJson = new Gson().toJson(ckResults);
		String smellResultsInJson = new Gson().toJson(smellResults.all());
		
		try {
			String html = readResource("/output/result.html");
			html = html
				.replace("##ck##", ckResultsInJson)
				.replace("##smells##", smellResultsInJson)
				.replace("##date##", new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime()));
			
			output.print(html);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			output.close();
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
