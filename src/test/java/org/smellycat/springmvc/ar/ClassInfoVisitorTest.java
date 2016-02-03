package org.smellycat.springmvc.ar;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.FileASTRequestor;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.smellycat.springmvc.Parser;
import org.smellycat.springmvc.smells.SmellTest;

public class ClassInfoVisitorTest extends SmellTest {
	
	private ClassInfoVisitor visitor;
	
	@Before
	public void setUp() {
		this.visitor = new ClassInfoVisitor();
	}
	
	@Test
	public void shouldDetectClasses() {
		run("class-info/t1");
		
		Assert.assertEquals("a.b.Controller", visitor.getClassName());
		Assert.assertNull(visitor.getSuperclass());
		Assert.assertTrue(visitor.getInterfaces().isEmpty());
		Assert.assertEquals("class", visitor.getType());
	}

	@Test
	public void shouldDetectSuperclasses() {
		run("class-info/t2");
		
		Assert.assertEquals("a.b.Controller", visitor.getClassName());
		Assert.assertEquals("a.b.c.BaseController", visitor.getSuperclass());
		Assert.assertTrue(visitor.getInterfaces().isEmpty());
		Assert.assertEquals("class", visitor.getType());
	}

	@Test
	public void shouldDetectItsInterfaces() {
		run("class-info/t3");
		
		Assert.assertEquals("a.b.ServiceImpl", visitor.getClassName());
		Assert.assertTrue(visitor.getInterfaces().contains("a.b.Service1"));
		Assert.assertTrue(visitor.getInterfaces().contains("a.b.Service2"));
	}

	private void run(String scenario) {
		Parser parser = new Parser(basePath + scenario);
		parser.execute(new FileASTRequestor() {
			public void acceptAST(String sourceFilePath, 
					CompilationUnit cu) {
				cu.accept(visitor);
			}
		});
	}
}
