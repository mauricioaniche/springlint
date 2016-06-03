package org.springlint.analysis.smells.repository;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springlint.analysis.smells.SmellAnalysis;
import org.springlint.analysis.smells.SmellTest;
import org.springlint.architecture.SpringMVCArchitecture;
import org.springlint.domain.Repository;
import org.springlint.domain.SmellyClass;

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
