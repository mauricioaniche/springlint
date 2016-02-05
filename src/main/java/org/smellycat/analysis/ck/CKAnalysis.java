package org.smellycat.analysis.ck;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.io.IOUtils;
import org.smellycat.architecture.ArchitecturalRole;
import org.smellycat.architecture.Architecture;

import com.google.gson.Gson;

import br.com.aniche.ck.CK;
import br.com.aniche.ck.CKNumber;
import br.com.aniche.ck.CKReport;

public class CKAnalysis {

	private String projectPath;
	private Map<String, List<CKNumber>> result;
	private PrintStream output;
	private Architecture arch;

	public CKAnalysis(Architecture arch, String projectPath, PrintStream output) {
		this.arch = arch;
		this.projectPath = projectPath;
		this.output = output;
		this.result = new HashMap<String, List<CKNumber>>();
	}

	public void run() throws FileNotFoundException {
		CKReport report = new CK()
			.plug(() -> arch.roleVisitor())
			.calculate(projectPath);
		
		for(ArchitecturalRole role : rolesPlusUnindentified()) {
		
			List<CKNumber> filtered = report.all().stream()
				.filter(n -> n.getSpecific("role") == role.id())
				.collect(Collectors.toList());
			
			result.put(role.name().toLowerCase(), filtered);
			
		}
		String json = new Gson().toJson(result);
		generateOutput(json);
	}

	private List<ArchitecturalRole> rolesPlusUnindentified() {
		List<ArchitecturalRole> roles = new ArrayList<>(arch.roles());
		roles.add(ArchitecturalRole.OTHER);
		return roles;
	}

	private void generateOutput(String json) {
		
		try {
			String thresholds = readResource("/output/" + arch.thresholdsFile());
			String html = readResource("/output/ck.html");
			html = html
				.replace("##projectDir##", projectPath)
				.replace("##json##", json)
				.replace("##arch##", thresholds)
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
