package org.smellycat.architecture.springmvc;

import java.util.Arrays;
import java.util.List;

import org.smellycat.analysis.smells.Smell;
import org.smellycat.analysis.smells.springmvc.controller.PromiscuousController;
import org.smellycat.analysis.smells.springmvc.controller.SmartController;
import org.smellycat.analysis.smells.springmvc.repository.FatRepository;
import org.smellycat.analysis.smells.springmvc.repository.LaboriousRepositoryMethod;
import org.smellycat.analysis.smells.springmvc.repository.SmartRepository;
import org.smellycat.analysis.smells.springmvc.service.MeddlingService;
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

	@Override
	public List<Smell> smells() {
		return Arrays.asList(
			new PromiscuousController(), new SmartController(), new SmartRepository(), new FatRepository(), new MeddlingService(), new LaboriousRepositoryMethod()
		);
	}

}
