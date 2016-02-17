package org.smellycat.domain;

import java.util.Collections;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.smellycat.architecture.ArchitecturalRole;
import org.smellycat.architecture.springmvc.SpringMVCArchitecture;
import org.smellycat.domain.SmellyClass;

public class SmellyClassTest {

	private SmellyClass sc;

	@Before
	public void setUp() {
		sc = new SmellyClass("/dir/File.java", "abc.File", "class", "", Collections.emptySet());
	}
	
	@Test
	public void addSmells() {
		sc.smells("Some Smell", "this class is smelly");
		sc.smells("Some Other Smell", "this class is also smelly");
		
		Assert.assertTrue(sc.hasSmell("Some Smell"));
		Assert.assertTrue(sc.hasSmell("Some Other Smell"));
		Assert.assertFalse(sc.hasSmell("Nooo"));
		Assert.assertEquals("this class is smelly", sc.getDescriptionFor("Some Smell"));
		Assert.assertEquals("this class is also smelly", sc.getDescriptionFor("Some Other Smell"));
	}

	@Test
	public void setAttributes() {
		sc.setAttribute("attr1", 1);
		sc.setAttribute("attr2", 2);
		
		Assert.assertEquals(1, sc.getAttribute("attr1"));
		Assert.assertEquals(2, sc.getAttribute("attr2"));
	}
	
	@Test
	public void returnNegative1IfAttributeIsNotFound() {
		Assert.assertEquals(-1, sc.getAttribute("attr3"));
	}

	@Test
	public void plusOneToAttribute() {
		Assert.assertEquals(-1, sc.getAttribute("attr3"));

		sc.plusOne("attr3");
		Assert.assertEquals(1, sc.getAttribute("attr3"));

		sc.plusOne("attr3");
		Assert.assertEquals(2, sc.getAttribute("attr3"));
	}
	

	@Test
	public void defineArchitecturalRole() {
		Assert.assertTrue(sc.is(ArchitecturalRole.OTHER));

		sc.setRole(SpringMVCArchitecture.CONTROLLER);
		Assert.assertTrue(sc.is(SpringMVCArchitecture.CONTROLLER));
		
	}
	
	
}
