package org.smellycat.springmvc;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.io.IOUtils;
import org.smellycat.springmvc.ar.ArchitecturalRoleVisitor;
import org.smellycat.springmvc.domain.ArchitecturalRole;

import com.google.gson.Gson;

import br.com.aniche.ck.CK;
import br.com.aniche.ck.CKNumber;
import br.com.aniche.ck.CKReport;

public class CKAnalysis {

	private String projectPath;
	private Map<String, List<CKNumber>> result;
	private PrintStream output;

	public CKAnalysis(String projectPath, PrintStream output) {
		this.projectPath = projectPath;
		this.output = output;
		this.result = new HashMap<String, List<CKNumber>>();
	}

	public void run() throws FileNotFoundException {
		CKReport report = new CK()
			.plug(() -> new ArchitecturalRoleVisitor())
			.calculate(projectPath);
		
		for(ArchitecturalRole role : ArchitecturalRole.values()) {
		
			List<CKNumber> filtered = report.all().stream()
				.filter(n -> n.getSpecific("role") == role.ordinal())
				.collect(Collectors.toList());
			
			result.put(role.name().toLowerCase(), filtered);
			
		}
		String json = new Gson().toJson(result);
		generateOutput(json);
	}

	private void generateOutput(String json) {
		
		try {
			InputStream is = CKAnalysis.class.getResource("/output/ck.html").openStream();
			
			StringWriter writer = new StringWriter();
			IOUtils.copy(is, writer);
			String html = writer.toString();
			html = html
				.replace("##json##", json)
				.replace("##date##", new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime()));
			
			output.print(html);
			is.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
