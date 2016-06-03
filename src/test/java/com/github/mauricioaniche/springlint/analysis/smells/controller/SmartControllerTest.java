package com.github.mauricioaniche.springlint.analysis.smells.controller;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.github.mauricioaniche.springlint.analysis.smells.SmellAnalysis;
import com.github.mauricioaniche.springlint.analysis.smells.SmellTest;
import com.github.mauricioaniche.springlint.architecture.SpringMVCArchitecture;
import com.github.mauricioaniche.springlint.domain.Repository;
import com.github.mauricioaniche.springlint.domain.SmellyClass;

public class SmartControllerTest extends SmellTest {

	private SpringMVCArchitecture arch;

	@Before
	public void createArch() {
		this.arch = new SpringMVCArchitecture();
	}
	
	@Test
	public void ignoreSpringMethodInvocations()  {
		SmellAnalysis tool = new SmellAnalysis(arch, basePath + "smart-controller/t1");
		Repository repo = tool.run();
		
		SmellyClass sc = repo.getByClass("mfa.t1.InvoiceController");
		Assert.assertEquals(0, sc.getAttribute("rfc-but-spring"));
	}

	@Test
	public void countOtherInvocations()  {
		SmellAnalysis tool = new SmellAnalysis(arch, basePath + "smart-controller/t1");
		Repository repo = tool.run();
		
		SmellyClass sc = repo.getByClass("mfa.t1.InvoiceController2");
		Assert.assertEquals(2, sc.getAttribute("rfc-but-spring"));

		sc = repo.getByClass("mfa.t1.InvoiceController3");
		Assert.assertEquals(3, sc.getAttribute("rfc-but-spring"));
	}

	@Test
	public void countStaticInvocations()  {
		SmellAnalysis tool = new SmellAnalysis(arch, basePath + "smart-controller/t1");
		Repository repo = tool.run();
		
		SmellyClass sc = repo.getByClass("mfa.t1.InvoiceController5");
		Assert.assertEquals(2, sc.getAttribute("rfc-but-spring"));
	}

	@Test
	public void shouldDealWithInlineMultipleVariableDeclaration() {
		SmellAnalysis tool = new SmellAnalysis(arch, basePath + "smart-controller/t1");
		Repository repo = tool.run();
		
		SmellyClass sc = repo.getByClass("mfa.t1.InvoiceController6");
		Assert.assertEquals(1, sc.getAttribute("rfc-but-spring"));
	}

	@Test
	public void shouldIgnoreInternalMethods()  {
		SmellAnalysis tool = new SmellAnalysis(arch, basePath + "smart-controller/t2");
		Repository repo = tool.run();
		
		SmellyClass sc = repo.getByClass("mfa.t2.MyController");
		Assert.assertEquals(1, sc.getAttribute("rfc-but-spring"));
	}

}
