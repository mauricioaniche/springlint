package com.github.mauricioaniche.springlint.issues;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.github.mauricioaniche.springlint.analysis.smells.SmellAnalysis;
import com.github.mauricioaniche.springlint.analysis.smells.SmellTest;
import com.github.mauricioaniche.springlint.architecture.SpringMVCArchitecture;
import com.github.mauricioaniche.springlint.domain.Repository;
import com.github.mauricioaniche.springlint.domain.SmellyClass;

public class Issue1Test extends SmellTest {

	private SpringMVCArchitecture arch;

	@Before
	public void createArch() {
		this.arch = new SpringMVCArchitecture();
	}
	
	@Test
	public void dontThrowExceptionWithAnnotationDeclarations()  {
		SmellAnalysis tool = new SmellAnalysis(arch, basePath + "issues/1");
		Repository repo = tool.run();
		
		SmellyClass sc = repo.getByClass("foo.Issue1");
		Assert.assertEquals("foo.Issue1", sc.getName());
	}

}
