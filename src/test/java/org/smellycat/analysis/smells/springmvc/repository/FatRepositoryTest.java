package org.smellycat.analysis.smells.springmvc.repository;

import java.io.UnsupportedEncodingException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.smellycat.analysis.smells.SmellAnalysis;
import org.smellycat.analysis.smells.SmellTest;
import org.smellycat.architecture.springmvc.SpringMVCArchitecture;
import org.smellycat.domain.SmellyClass;

public class FatRepositoryTest extends SmellTest {

	private SpringMVCArchitecture arch;

	@Before
	public void createArch() {
		this.arch = new SpringMVCArchitecture();
	}
	
	@Test
	public void countNumberOfEntities() throws UnsupportedEncodingException {
		SmellAnalysis tool = new SmellAnalysis(arch, basePath + "fat-repository/t1", ps, repo);
		tool.run();
		
		SmellyClass sc = repo.getByClass("mfa.t1.InvoiceRepository");
		Assert.assertEquals(3, sc.getAttribute("number-of-entities-as-dependencies"));
	}

	@Test
	public void countNumberOfEntitiesNoMatterThePackage() throws UnsupportedEncodingException {
		SmellAnalysis tool = new SmellAnalysis(arch, basePath + "fat-repository/t1", ps, repo);
		tool.run();
		
		SmellyClass sc = repo.getByClass("mfa.t1.InvoiceRepository2");
		Assert.assertEquals(4, sc.getAttribute("number-of-entities-as-dependencies"));
	}

	@Test
	public void countNumberOfEntitiesInReturnType() throws UnsupportedEncodingException {
		SmellAnalysis tool = new SmellAnalysis(arch, basePath + "fat-repository/t1", ps, repo);
		tool.run();
		
		SmellyClass sc = repo.getByClass("mfa.t1.InvoiceRepository3");
		Assert.assertEquals(2, sc.getAttribute("number-of-entities-as-dependencies"));
	}
}
