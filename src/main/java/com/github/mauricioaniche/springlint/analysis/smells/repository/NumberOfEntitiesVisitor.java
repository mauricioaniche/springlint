package com.github.mauricioaniche.springlint.analysis.smells.repository;

import com.github.mauricioaniche.springlint.analysis.smells.ArchitecturalRoleDependenciesCountVisitor;
import com.github.mauricioaniche.springlint.architecture.SpringMVCArchitecture;
import com.github.mauricioaniche.springlint.domain.Repository;
import com.github.mauricioaniche.springlint.domain.SmellyClass;

public class NumberOfEntitiesVisitor extends ArchitecturalRoleDependenciesCountVisitor {

	public NumberOfEntitiesVisitor(Repository repo, SmellyClass clazz) {
		super(repo, clazz, SpringMVCArchitecture.ENTITY, "number-of-entities-as-dependencies");
	}

}
