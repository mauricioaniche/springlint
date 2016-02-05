package org.smellycat.springmvc;

import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.smellycat.springmvc.ar.ArchitecturalRoleVisitor;
import org.smellycat.springmvc.domain.ArchitecturalRole;

import com.google.gson.Gson;

import br.com.aniche.ck.CK;
import br.com.aniche.ck.CKNumber;
import br.com.aniche.ck.CKReport;

public class MetricsOutput {

	private String projectPath;
	private Map<String, List<CKNumber>> result;

	public MetricsOutput(String projectPath) {
		this.projectPath = projectPath;
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
		PrintStream ps = new PrintStream("/Users/mauricioaniche/Desktop/json.json");
		ps.print(json);
		ps.close();
	}
	
	public static void main(String[] args) throws FileNotFoundException {
		new MetricsOutput("/Users/mauricioaniche/Desktop/projects/SSP").run();
	}
}
