package icsme2016.smells;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;

import org.junit.After;
import org.junit.Before;

import icsme2016.domain.Repository;

public class SmellTest {

	protected String basePath;
	protected Repository repo;
	protected PrintStream ps;
	protected ByteArrayOutputStream baos;

	@Before
	public void setUp() {
		this.repo = new Repository();
		this.basePath = "/Users/mauricioaniche/textos/icsme2016/java/tool/test-repo/";
		
		baos = new ByteArrayOutputStream();
		ps = new PrintStream(baos);
	}
	
	@After
	public void tearDown() {
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
