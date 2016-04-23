package org.smellycat.analysis.ck;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.smellycat.architecture.ArchitecturalRole;
import org.smellycat.architecture.Architecture;

import br.com.aniche.ck.CK;
import br.com.aniche.ck.CKNumber;
import br.com.aniche.ck.CKReport;

public class CKAnalysis {

	private String projectPath;
	private Map<String, List<CKNumber>> result;
	private Architecture arch;

	public CKAnalysis(Architecture arch, String projectPath) {
		this.arch = arch;
		this.projectPath = projectPath;
		this.result = new HashMap<String, List<CKNumber>>();
	}

	public Map<String, List<CKNumber>> run() throws FileNotFoundException {
		CKReport report = new CK()
			.plug(() -> arch.roleVisitor())
			.calculate(projectPath);
		
		for(ArchitecturalRole role : rolesPlusUnindentified()) {
		
			List<CKNumber> filtered = report.all().stream()
				.filter(n -> n.getSpecific("role") == role.id())
				.collect(Collectors.toList());
			
			result.put(role.name().toLowerCase(), filtered);
			
		}

		return result;
	}

	private List<ArchitecturalRole> rolesPlusUnindentified() {
		List<ArchitecturalRole> roles = new ArrayList<>(arch.roles());
		roles.add(ArchitecturalRole.OTHER);
		return roles;
	}


	
}
