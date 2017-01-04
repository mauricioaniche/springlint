package com.github.mauricioaniche.springlint.analysis.smells.service;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.github.mauricioaniche.springlint.analysis.smells.SmellAnalysis;
import com.github.mauricioaniche.springlint.analysis.smells.SmellTest;
import com.github.mauricioaniche.springlint.architecture.SpringMVCArchitecture;
import com.github.mauricioaniche.springlint.domain.Repository;
import com.github.mauricioaniche.springlint.domain.SmellyClass;

public class MeddlingServiceTest extends SmellTest {

	private SpringMVCArchitecture arch;

	@Before
	public void createArch() {
		this.arch = new SpringMVCArchitecture();
	}
	
	@Test
	public void shouldDetectTheUseOfJDBC() {
		SmellAnalysis tool = new SmellAnalysis(arch, basePath + "dbquerying-service/t1");
		Repository repo = tool.run();
		
		SmellyClass sc = repo.getByClass("mfa.t1.InvoiceService");
		Assert.assertEquals(1, sc.getAttribute("use-persistence-mechanism"));
	}
	
	@Test
	public void shouldIgnoreExceptions() {
		SmellAnalysis tool = new SmellAnalysis(arch, basePath + "dbquerying-service/t2");
		Repository repo = tool.run();
		
		SmellyClass sc = repo.getByClass("jp.co.acroquest.endosnipe.web.dashboard.service.SignalService");
		Assert.assertEquals(0, sc.getAttribute("use-persistence-mechanism"));

		SmellyClass sc2 = repo.getByClass("jp.co.acroquest.endosnipe.web.dashboard.service.SignalService2");
		Assert.assertEquals(0, sc2.getAttribute("use-persistence-mechanism"));
	}
	

	@Test
	public void shouldDetectTheUseOfJPA() {
		SmellAnalysis tool = new SmellAnalysis(arch, basePath + "dbquerying-service/t1");
		Repository repo = tool.run();
		
		SmellyClass sc = repo.getByClass("mfa.t1.JPAService");
		Assert.assertEquals(1, sc.getAttribute("use-persistence-mechanism"));
	}

	@Test
	public void shouldDetectTheUseOfHibernate() {
		SmellAnalysis tool = new SmellAnalysis(arch, basePath + "dbquerying-service/t1");
		Repository repo = tool.run();
		
		SmellyClass sc = repo.getByClass("mfa.t1.HibernateService");
		Assert.assertEquals(1, sc.getAttribute("use-persistence-mechanism"));
	}

	@Test
	public void shouldDetectTheUseOfSpringData() {
		// TODO: i need a code example
	}
	
	@Test
	public void ignoreOnlyImports() {
		SmellAnalysis tool = new SmellAnalysis(arch, basePath + "dbquerying-service/t1");
		Repository repo = tool.run();
		
		SmellyClass sc = repo.getByClass("mfa.t1.CleanService");
		Assert.assertEquals(0, sc.getAttribute("use-persistence-mechanism"));
	}
}
