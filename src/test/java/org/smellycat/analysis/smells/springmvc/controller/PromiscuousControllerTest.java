package org.smellycat.analysis.smells.springmvc.controller;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.smellycat.analysis.smells.SmellAnalysis;
import org.smellycat.analysis.smells.SmellTest;
import org.smellycat.architecture.springmvc.SpringMVCArchitecture;
import org.smellycat.domain.Repository;
import org.smellycat.domain.SmellyClass;

public class PromiscuousControllerTest extends SmellTest {

	private SpringMVCArchitecture arch;

	@Before
	public void createArch() {
		this.arch = new SpringMVCArchitecture();
	}
	
	@Test
	public void shouldCountNumberOfRoutes() {
		SmellAnalysis tool = new SmellAnalysis(arch, basePath + "promiscuous-controller/t1");
		Repository repo = tool.run();
		
		SmellyClass sc = repo.getByClass("mfa.t1.InvoiceController");
		Assert.assertEquals(2, sc.getAttribute("number-of-routes"));
	}

	@Test
	public void shouldCountNumberOfServices() {
		SmellAnalysis tool = new SmellAnalysis(arch, basePath + "promiscuous-controller/t1");
		Repository repo = tool.run();
		
		SmellyClass sc = repo.getByClass("mfa.t1.InvoiceController");
		Assert.assertEquals(2, sc.getAttribute("number-of-services-as-dependencies"));

		sc = repo.getByClass("mfa.t1.InvoiceDTO");
		Assert.assertEquals(0, sc.getAttribute("number-of-services-as-dependencies"));

		sc = repo.getByClass("mfa.t1.OtherInvoiceService");
		Assert.assertEquals(1, sc.getAttribute("number-of-services-as-dependencies"));
	}

	@Test
	public void shouldDetectTheSmells() {
		SmellAnalysis tool = new SmellAnalysis(arch, basePath + "promiscuous-controller/t2");
		Repository repo = tool.run();
		
		System.out.println(repo.all());
		SmellyClass sc = repo.getByClass("mfa.t2.ManyRoutesController");
		Assert.assertTrue(sc.hasSmell("Promiscuous Controller"));
		sc = repo.getByClass("mfa.t2.ManyServicesController");
		Assert.assertTrue(sc.hasSmell("Promiscuous Controller"));
		sc = repo.getByClass("mfa.t2.NormalController");
		Assert.assertFalse(sc.hasSmell("Promiscuous Controller"));
	}

	@Test
	public void shouldUnderstandServiceInterfaces() {
		SmellAnalysis tool = new SmellAnalysis(arch, basePath + "promiscuous-controller/t3");
		Repository repo = tool.run();
		
		SmellyClass sc = repo.getByClass("mfa.t3.InvoiceController");
		Assert.assertEquals(1, sc.getAttribute("number-of-services-as-dependencies"));
	}
}
