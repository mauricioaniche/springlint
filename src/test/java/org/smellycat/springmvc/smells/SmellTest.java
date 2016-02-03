package org.smellycat.springmvc.smells;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;

import org.junit.After;
import org.junit.Before;
import org.smellycat.springmvc.domain.Repository;

public class SmellTest {

	protected String basePath;
	protected Repository repo;
	protected PrintStream ps;
	protected ByteArrayOutputStream baos;

	@Before
	public void setBasePath() {
		this.repo = new Repository();
		this.basePath = Repository.class.getResource("/").getPath() + "../../test-repo/";
		
		baos = new ByteArrayOutputStream();
		ps = new PrintStream(baos);
	}
	
	@After
	public void closePs() {
		ps.close();
	}
	
	protected String get() {
		try {
			return baos.toString("UTF-8");
		} catch (UnsupportedEncodingException e) {
			return "(encoding error)";
		}
	}
}
