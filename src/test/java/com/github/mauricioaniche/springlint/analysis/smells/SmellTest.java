package com.github.mauricioaniche.springlint.analysis.smells;

import org.junit.Before;

import com.github.mauricioaniche.springlint.domain.Repository;

public class SmellTest {

	protected String basePath;

	@Before
	public void setBasePath() {
		this.basePath = Repository.class.getResource("/").getPath() + "../../test-repo/";
		
	}
	
}
