package org.springlint.architecture;

import java.util.Arrays;
import java.util.List;

import org.springlint.analysis.smells.Smell;
import org.springlint.analysis.smells.controller.PromiscuousController;
import org.springlint.analysis.smells.controller.SmartController;
import org.springlint.analysis.smells.repository.FatRepository;
import org.springlint.analysis.smells.repository.LaboriousRepositoryMethod;
import org.springlint.analysis.smells.repository.SmartRepository;
import org.springlint.analysis.smells.service.MeddlingService;

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
	public List<Smell> smells() {
		return Arrays.asList(
			new PromiscuousController(), new SmartController(), new SmartRepository(), new FatRepository(), new MeddlingService(), new LaboriousRepositoryMethod()
		);
	}

}
