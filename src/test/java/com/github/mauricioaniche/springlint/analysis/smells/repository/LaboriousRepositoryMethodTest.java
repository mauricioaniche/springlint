package com.github.mauricioaniche.springlint.analysis.smells.repository;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.github.mauricioaniche.springlint.analysis.smells.SmellAnalysis;
import com.github.mauricioaniche.springlint.analysis.smells.SmellTest;
import com.github.mauricioaniche.springlint.architecture.SpringMVCArchitecture;
import com.github.mauricioaniche.springlint.domain.Repository;
import com.github.mauricioaniche.springlint.domain.SmellyClass;

public class LaboriousRepositoryMethodTest extends SmellTest {

	private SpringMVCArchitecture arch;

	@Before
	public void createArch() {
		this.arch = new SpringMVCArchitecture();
	}
	
	@Test
	public void identifyMultipleQueriesInASingleMethod() {
		SmellAnalysis tool = new SmellAnalysis(arch, basePath + "multiplequeries/t1");
		Repository repo = tool.run();
		
		SmellyClass sc = repo.getByClass("mfa.t1.WrongRepository");
		Assert.assertEquals(1, sc.getAttribute("multiple-persistence-invocations"));
		sc = repo.getByClass("mfa.t1.CorrectRepository");
		Assert.assertEquals(0, sc.getAttribute("multiple-persistence-invocations"));
	}

	// TODO: tests for the other persistence apis...
}
