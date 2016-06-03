package org.springlint.analysis.smells;

import org.junit.Before;
import org.springlint.domain.Repository;

public class SmellTest {

	protected String basePath;

	@Before
	public void setBasePath() {
		this.basePath = Repository.class.getResource("/").getPath() + "../../test-repo/";
		
	}
	
}
