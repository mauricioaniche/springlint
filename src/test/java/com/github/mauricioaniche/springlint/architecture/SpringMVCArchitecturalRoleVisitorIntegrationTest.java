package com.github.mauricioaniche.springlint.architecture;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.FileASTRequestor;
import org.junit.Assert;
import org.junit.Test;

import com.github.mauricioaniche.springlint.analysis.smells.Parser;
import com.github.mauricioaniche.springlint.analysis.smells.SmellTest;
import com.github.mauricioaniche.springlint.architecture.ArchitecturalRole;
import com.github.mauricioaniche.springlint.architecture.SpringMVCArchitecturalRoleVisitor;
import com.github.mauricioaniche.springlint.architecture.SpringMVCArchitecture;

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
