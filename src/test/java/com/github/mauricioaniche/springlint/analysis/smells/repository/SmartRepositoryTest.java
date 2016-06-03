package com.github.mauricioaniche.springlint.analysis.smells.repository;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.github.mauricioaniche.springlint.analysis.smells.SmellAnalysis;
import com.github.mauricioaniche.springlint.analysis.smells.SmellTest;
import com.github.mauricioaniche.springlint.architecture.SpringMVCArchitecture;
import com.github.mauricioaniche.springlint.domain.Repository;
import com.github.mauricioaniche.springlint.domain.SmellyClass;

public class SmartRepositoryTest extends SmellTest {

	private SpringMVCArchitecture arch;

	@Before
	public void createArch() {
		this.arch = new SpringMVCArchitecture();
	}
	
	@Test
	public void countKeywords() {
		SmellAnalysis tool = new SmellAnalysis(arch, basePath + "smart-repository/t1");
		Repository repo = tool.run();
		
		SmellyClass sc = repo.getByClass("mfa.t1.InvoiceRepository");
		Assert.assertEquals(3, sc.getAttribute("sql-complexity"));
	}

	@Test
	public void countKeywordsInAllMethods() {
		SmellAnalysis tool = new SmellAnalysis(arch, basePath + "smart-repository/t1");
		Repository repo = tool.run();
		
		SmellyClass sc = repo.getByClass("mfa.t1.InvoiceRepository2");
		Assert.assertEquals(12, sc.getAttribute("sql-complexity"));
	}

	@Test
	public void understandInlineSql() {
		SmellAnalysis tool = new SmellAnalysis(arch, basePath + "smart-repository/t1");
		Repository repo = tool.run();
		
		SmellyClass sc = repo.getByClass("mfa.t1.InvoiceRepository3");
		Assert.assertEquals(3, sc.getAttribute("sql-complexity"));
	}

}
