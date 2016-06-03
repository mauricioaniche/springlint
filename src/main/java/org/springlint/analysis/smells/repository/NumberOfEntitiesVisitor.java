package org.springlint.analysis.smells.repository;

import org.springlint.analysis.smells.ArchitecturalRoleDependenciesCountVisitor;
import org.springlint.architecture.SpringMVCArchitecture;
import org.springlint.domain.Repository;
import org.springlint.domain.SmellyClass;

public class NumberOfEntitiesVisitor extends ArchitecturalRoleDependenciesCountVisitor {

	public NumberOfEntitiesVisitor(Repository repo, SmellyClass clazz) {
		super(repo, clazz, SpringMVCArchitecture.ENTITY, "number-of-entities-as-dependencies");
	}

}
