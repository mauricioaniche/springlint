package org.smellycat.analysis.smells.springmvc.repository;

import org.smellycat.analysis.smells.ArchitecturalRoleDependenciesCountVisitor;
import org.smellycat.architecture.springmvc.SpringMVCArchitecture;
import org.smellycat.domain.Repository;
import org.smellycat.domain.SmellyClass;

public class NumberOfEntitiesVisitor extends ArchitecturalRoleDependenciesCountVisitor {

	public NumberOfEntitiesVisitor(Repository repo, SmellyClass clazz) {
		super(repo, clazz, SpringMVCArchitecture.ENTITY, "number-of-entities-as-dependencies");
	}

}
