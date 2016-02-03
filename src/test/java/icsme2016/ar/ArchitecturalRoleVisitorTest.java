package icsme2016.ar;

import java.io.ByteArrayInputStream;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import icsme2016.domain.ArchitecturalRole;
import icsme2016.jdt.JDTRunner;

public class ArchitecturalRoleVisitorTest {
	
	private ArchitecturalRoleVisitor visitor;
	
	@Before
	public void setUp() {
		this.visitor = new ArchitecturalRoleVisitor();
	}
	
	@Test
	public void shouldDetectControllers() {
		String sc = "@Controller class Controller {}";
		
		new JDTRunner().visit(visitor, new ByteArrayInputStream(sc.getBytes()));
		
		Assert.assertEquals(ArchitecturalRole.CONTROLLER, visitor.getRole());
	}

	@Test
	public void shouldDetectService() {
		String sc = "@Service class Service {}";
		
		new JDTRunner().visit(visitor, new ByteArrayInputStream(sc.getBytes()));
		
		Assert.assertEquals(ArchitecturalRole.SERVICE, visitor.getRole());
	}

	@Test
	public void shouldDetectComponent() {
		String sc = "@Component class Service {}";
		
		new JDTRunner().visit(visitor, new ByteArrayInputStream(sc.getBytes()));
		
		Assert.assertEquals(ArchitecturalRole.COMPONENT, visitor.getRole());
	}

	@Test
	public void shouldDetectRepository() {
		String sc = "@Repository class Repository {}";
		
		new JDTRunner().visit(visitor, new ByteArrayInputStream(sc.getBytes()));
		
		Assert.assertEquals(ArchitecturalRole.REPOSITORY, visitor.getRole());
	}

	@Test
	public void shouldDetectEntity() {
		String sc = "@Entity class Invoice {}";
		
		new JDTRunner().visit(visitor, new ByteArrayInputStream(sc.getBytes()));
		
		Assert.assertEquals(ArchitecturalRole.ENTITY, visitor.getRole());
	}

	@Test
	public void unindentifiedOtherwise() {
		String sc = "@Bla class Invoice {}";
		
		new JDTRunner().visit(visitor, new ByteArrayInputStream(sc.getBytes()));
		
		Assert.assertEquals(ArchitecturalRole.UNINDENTIFIED, visitor.getRole());
	}
}
