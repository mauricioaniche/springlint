package org.smellycat.analysis.smells.springmvc.controller;

import java.io.ByteArrayInputStream;
import java.util.Collections;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.smellycat.analysis.smells.springmvc.controller.NumberOfRoutesVisitor;
import org.smellycat.domain.SmellyClass;
import org.smellycat.jdt.SingleClassJDTRunner;

public class NumberOfRoutesVisitorTest {
	
	private SmellyClass clazz;

	@Before
	public void setUp() {
		clazz = new SmellyClass("a", "a", "class", "", Collections.emptySet());
	}
	
	@Test
	public void shouldCountAnnotations() {
		String sc = 
				  "class Controller {"
				+ "@RequestMapping"
				+ "  public void m1() {"
				+ "    a = a + 1;"
				+ "  }"
				+ "@RequestMapping"
				+ "  public void m2() {"
				+ "    a = a + 1;"
				+ "  }"
				+ "}"
				;
		
		NumberOfRoutesVisitor visitor = new NumberOfRoutesVisitor(clazz);
		new SingleClassJDTRunner().visit(visitor, new ByteArrayInputStream(sc.getBytes()));
		
		Assert.assertEquals(2, clazz.getAttribute("number-of-routes"));
				
	}
	
	@Test
	public void shouldCountAnnotationsWithSingleMember() {
		String sc = 
				"class Controller {"
				+ "@RequestMapping(\"bla\")"
				+ "  public void m1() {"
				+ "    a = a + 1;"
				+ "  }"
				+ "}"
				;
		
		NumberOfRoutesVisitor visitor = new NumberOfRoutesVisitor(clazz);
		new SingleClassJDTRunner().visit(visitor, new ByteArrayInputStream(sc.getBytes()));
		
		Assert.assertEquals(1, clazz.getAttribute("number-of-routes"));
		
	}

	@Test
	public void shouldCountAnnotationsWithManyParams() {
		String sc = 
				"class Entity {"
				+ "@RequestMapping(rota=\"bla\", rota2=2)"
				+ "  public void m1() {"
				+ "    a = a + 1;"
				+ "  }"
				+ "}"
				;
		
		NumberOfRoutesVisitor visitor = new NumberOfRoutesVisitor(clazz);
		new SingleClassJDTRunner().visit(visitor, new ByteArrayInputStream(sc.getBytes()));
		
		Assert.assertEquals(1, clazz.getAttribute("number-of-routes"));		
	}
	
	@Test
	public void shouldIgnoreOtherAnnotations() {
		String sc = 
				"class Entity {"
				+ "@Bla @Ble(1) @Bli(rota=1,rota2=2)"
				+ "  public void m1() {"
				+ "    a = a + 1;"
				+ "  }"
				+ "}"
				;
		
		NumberOfRoutesVisitor visitor = new NumberOfRoutesVisitor(clazz);
		new SingleClassJDTRunner().visit(visitor, new ByteArrayInputStream(sc.getBytes()));
		
		Assert.assertEquals(0, clazz.getAttribute("number-of-routes"));
		
	}
	
}
