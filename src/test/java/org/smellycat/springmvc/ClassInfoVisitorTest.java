package org.smellycat.springmvc;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.FileASTRequestor;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.smellycat.analysis.smells.Parser;
import org.smellycat.analysis.smells.SmellTest;
import org.smellycat.utils.jdt.ClassInfoVisitor;

public class ClassInfoVisitorTest extends SmellTest {
	
	private Map<String, ClassInfoVisitor> visitors;
	
	@Before
	public void setUp() {
		this.visitors = new HashMap<String, ClassInfoVisitor>();
	}
	
	@Test
	public void shouldDetectClasses() {
		run("class-info/t1");
		
		ClassInfoVisitor visitor = visitors.get("Controller.java");
		Assert.assertEquals("a.b.Controller", visitor.getClassName());
		Assert.assertNull(visitor.getSuperclass());
		Assert.assertTrue(visitor.getInterfaces().isEmpty());
		Assert.assertEquals("class", visitor.getType());
	}

	@Test
	public void shouldDetectSuperclasses() {
		run("class-info/t2");
		
		ClassInfoVisitor visitor = visitors.get("Controller.java");
		Assert.assertEquals("a.b.Controller", visitor.getClassName());
		Assert.assertEquals("a.b.c.BaseController", visitor.getSuperclass());
		Assert.assertTrue(visitor.getInterfaces().isEmpty());
		Assert.assertEquals("class", visitor.getType());
	}

	@Test
	public void shouldDetectItsInterfaces() {
		run("class-info/t3");
		
		ClassInfoVisitor visitor = visitors.get("ServiceImpl.java");
		Assert.assertEquals("a.b.ServiceImpl", visitor.getClassName());
		Assert.assertTrue(visitor.getInterfaces().contains("a.b.Service1"));
		Assert.assertTrue(visitor.getInterfaces().contains("a.b.Service2"));

		visitor = visitors.get("GenericServiceImpl.java");
		Assert.assertEquals("a.b.GenericServiceImpl", visitor.getClassName());
		Assert.assertTrue(visitor.getInterfaces().contains("a.b.GenericService"));
	}

	private void run(String scenario) {
		Parser parser = new Parser(basePath + scenario);
		parser.execute(new FileASTRequestor() {
			public void acceptAST(String sourceFilePath, 
					CompilationUnit cu) {
				
				ClassInfoVisitor visitor = new ClassInfoVisitor();
				cu.accept(visitor);
				
				String fileWithoutPath = sourceFilePath.replace(basePath + scenario + "/", "");
				visitors.put(fileWithoutPath, visitor);
			}
		});
	}
}
