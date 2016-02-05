package org.smellycat.architecture.springmvc;

import java.util.Arrays;
import java.util.List;

import org.smellycat.architecture.ArchitecturalRole;
import org.smellycat.architecture.ArchitecturalRoleVisitor;
import org.smellycat.architecture.Architecture;

public class SpringMVCArchitecture implements Architecture {

	public static ArchitecturalRole CONTROLLER = new ArchitecturalRole(1, "controller");
	public static ArchitecturalRole SERVICE = new ArchitecturalRole(2, "service");
	public static ArchitecturalRole ENTITY = new ArchitecturalRole(3, "entity");
	public static ArchitecturalRole REPOSITORY = new ArchitecturalRole(4, "repository");
	public static ArchitecturalRole COMPONENT = new ArchitecturalRole(5, "component");

	@Override
	public List<ArchitecturalRole> roles() {
		return Arrays.asList(
			CONTROLLER,
			SERVICE,
			ENTITY,
			REPOSITORY,
			COMPONENT
		);
	}

	@Override
	public ArchitecturalRoleVisitor roleVisitor() {
		return new SpringMVCArchitecturalRoleVisitor();
	}

	@Override
	public String thresholdsFile() {
		return "springmvc.js";
	}

}
