package com.github.mauricioaniche.springlint.analysis.smells.controller;

import com.github.mauricioaniche.springlint.analysis.smells.ArchitecturalRoleDependenciesCountVisitor;
import com.github.mauricioaniche.springlint.architecture.SpringMVCArchitecture;
import com.github.mauricioaniche.springlint.domain.Repository;
import com.github.mauricioaniche.springlint.domain.SmellyClass;

public class NumberOfServicesVisitor extends ArchitecturalRoleDependenciesCountVisitor {

	public NumberOfServicesVisitor(Repository repo, SmellyClass clazz) {
		super(repo, clazz, SpringMVCArchitecture.SERVICE, "number-of-services-as-dependencies");
	}

}
