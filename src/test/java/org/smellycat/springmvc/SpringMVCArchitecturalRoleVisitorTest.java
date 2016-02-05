package org.smellycat.springmvc;

import java.io.ByteArrayInputStream;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.smellycat.architecture.ArchitecturalRole;
import org.smellycat.architecture.springmvc.SpringMVCArchitecturalRoleVisitor;
import org.smellycat.architecture.springmvc.SpringMVCArchitecture;
import org.smellycat.jdt.SingleClassJDTRunner;

public class SpringMVCArchitecturalRoleVisitorTest {
	
	private SpringMVCArchitecturalRoleVisitor visitor;
	
	@Before
	public void setUp() {
		this.visitor = new SpringMVCArchitecturalRoleVisitor();
	}
	
	@Test
	public void shouldDetectControllers() {
		String sc = "@Controller class Controller {}";
		
		new SingleClassJDTRunner().visit(visitor, new ByteArrayInputStream(sc.getBytes()));
		
		Assert.assertEquals(SpringMVCArchitecture.CONTROLLER, visitor.getRole());
	}

	@Test
	public void shouldDetectService() {
		String sc = "@Service class Service {}";
		
		new SingleClassJDTRunner().visit(visitor, new ByteArrayInputStream(sc.getBytes()));
		
		Assert.assertEquals(SpringMVCArchitecture.SERVICE, visitor.getRole());
	}

	@Test
	public void shouldDetectComponent() {
		String sc = "@Component class Service {}";
		
		new SingleClassJDTRunner().visit(visitor, new ByteArrayInputStream(sc.getBytes()));
		
		Assert.assertEquals(SpringMVCArchitecture.COMPONENT, visitor.getRole());
	}

	@Test
	public void shouldDetectRepository() {
		String sc = "@Repository class Repository {}";
		
		new SingleClassJDTRunner().visit(visitor, new ByteArrayInputStream(sc.getBytes()));
		
		Assert.assertEquals(SpringMVCArchitecture.REPOSITORY, visitor.getRole());
	}

	@Test
	public void shouldDetectEntity() {
		String sc = "@Entity class Invoice {}";
		
		new SingleClassJDTRunner().visit(visitor, new ByteArrayInputStream(sc.getBytes()));
		
		Assert.assertEquals(SpringMVCArchitecture.ENTITY, visitor.getRole());
	}

	@Test
	public void unindentifiedOtherwise() {
		String sc = "@Bla class Invoice {}";
		
		new SingleClassJDTRunner().visit(visitor, new ByteArrayInputStream(sc.getBytes()));
		
		Assert.assertEquals(ArchitecturalRole.OTHER, visitor.getRole());
	}
}
