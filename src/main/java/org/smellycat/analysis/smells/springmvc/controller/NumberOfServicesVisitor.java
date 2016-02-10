package org.smellycat.analysis.smells.springmvc.controller;

import org.smellycat.analysis.smells.ArchitecturalRoleDependenciesCountVisitor;
import org.smellycat.architecture.springmvc.SpringMVCArchitecture;
import org.smellycat.domain.Repository;
import org.smellycat.domain.SmellyClass;

public class NumberOfServicesVisitor extends ArchitecturalRoleDependenciesCountVisitor {

	public NumberOfServicesVisitor(Repository repo, SmellyClass clazz) {
		super(repo, clazz, SpringMVCArchitecture.SERVICE, "number-of-services-as-dependencies");
	}

}
