package org.smellycat.springmvc.smells.controller;

import java.io.UnsupportedEncodingException;

import org.junit.Assert;
import org.junit.Test;
import org.smellycat.springmvc.SmellAnalysis;
import org.smellycat.springmvc.domain.SmellyClass;
import org.smellycat.springmvc.smells.SmellTest;

public class PromiscuousControllerTest extends SmellTest {

	@Test
	public void shouldCountNumberOfRoutes() throws UnsupportedEncodingException {
		SmellAnalysis tool = new SmellAnalysis(basePath + "promiscuous-controller/t1", ps, repo);
		tool.run();
		
		SmellyClass sc = repo.getByClass("mfa.t1.InvoiceController");
		Assert.assertEquals(2, sc.getAttribute("number-of-routes"));
	}

	@Test
	public void shouldCountNumberOfServices() throws UnsupportedEncodingException {
		SmellAnalysis tool = new SmellAnalysis(basePath + "promiscuous-controller/t1", ps, repo);
		tool.run();
		
		SmellyClass sc = repo.getByClass("mfa.t1.InvoiceController");
		Assert.assertEquals(2, sc.getAttribute("number-of-services-as-dependencies"));

		sc = repo.getByClass("mfa.t1.InvoiceDTO");
		Assert.assertEquals(0, sc.getAttribute("number-of-services-as-dependencies"));

		sc = repo.getByClass("mfa.t1.OtherInvoiceService");
		Assert.assertEquals(1, sc.getAttribute("number-of-services-as-dependencies"));
	}

	@Test
	public void shouldDetectTheSmells() throws UnsupportedEncodingException {
		SmellAnalysis tool = new SmellAnalysis(basePath + "promiscuous-controller/t1", ps, repo);
		tool.run();
		
		SmellyClass sc = repo.getByClass("mfa.t2.InvoiceController");
		Assert.assertTrue(sc.hasSmell("Promiscuous Controller"));

		sc = repo.getByClass("mfa.t2.NormalController");
		Assert.assertFalse(sc.hasSmell("Promiscuous Controller"));
	}

	@Test
	public void shouldUnderstandServiceInterfaces() throws UnsupportedEncodingException {
		SmellAnalysis tool = new SmellAnalysis(basePath + "promiscuous-controller/t1", ps, repo);
		tool.run();
		
		SmellyClass sc = repo.getByClass("mfa.t3.InvoiceController");
		Assert.assertEquals(1, sc.getAttribute("number-of-services-as-dependencies"));
	}
}
