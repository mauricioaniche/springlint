package org.springlint.architecture;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.FileASTRequestor;
import org.junit.Assert;
import org.junit.Test;
import org.springlint.analysis.smells.Parser;
import org.springlint.analysis.smells.SmellTest;
import org.springlint.architecture.ArchitecturalRole;
import org.springlint.architecture.SpringMVCArchitecturalRoleVisitor;
import org.springlint.architecture.SpringMVCArchitecture;

public class SpringMVCArchitecturalRoleVisitorIntegrationTest extends SmellTest {

	@Test
	public void shouldDetectTheRole() {
		String projectPath = basePath + "/roles/springmvc";
		Parser parser = new Parser(projectPath);
		

		Map<String,ArchitecturalRole> results = new HashMap<>();
		
		parser.execute(new FileASTRequestor() {
			
			public void acceptAST(String sourceFilePath, 
					CompilationUnit cu) {
				
				final SpringMVCArchitecturalRoleVisitor visitor = new SpringMVCArchitecturalRoleVisitor();
				cu.accept(visitor);
				
				results.put(lastPart(sourceFilePath), visitor.getRole());
			}

		});
		
		Map<String,ArchitecturalRole> expected = new HashMap<>();
		expected.put("AlgoController.java", SpringMVCArchitecture.CONTROLLER);
		expected.put("AlgoControllerWithInnerClass.java", SpringMVCArchitecture.CONTROLLER);
		expected.put("AlgoComponent.java", SpringMVCArchitecture.COMPONENT);
		expected.put("AlgoService.java", SpringMVCArchitecture.SERVICE);
		expected.put("AlgoEntity.java", SpringMVCArchitecture.ENTITY);
		expected.put("AlgoRepository.java", SpringMVCArchitecture.REPOSITORY);
		expected.put("Nada.java", ArchitecturalRole.OTHER);
		Assert.assertEquals(expected, results);
	}
	
	private String lastPart(String sourceFilePath) {
		String[] path = sourceFilePath.split("/");
		return path[path.length-1];
	}
}
