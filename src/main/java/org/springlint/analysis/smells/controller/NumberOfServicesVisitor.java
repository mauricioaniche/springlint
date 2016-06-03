package org.springlint.analysis.smells.controller;

import org.springlint.analysis.smells.ArchitecturalRoleDependenciesCountVisitor;
import org.springlint.architecture.SpringMVCArchitecture;
import org.springlint.domain.Repository;
import org.springlint.domain.SmellyClass;

public class NumberOfServicesVisitor extends ArchitecturalRoleDependenciesCountVisitor {

	public NumberOfServicesVisitor(Repository repo, SmellyClass clazz) {
		super(repo, clazz, SpringMVCArchitecture.SERVICE, "number-of-services-as-dependencies");
	}

}
