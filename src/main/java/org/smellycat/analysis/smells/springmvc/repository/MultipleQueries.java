package org.smellycat.analysis.smells.springmvc.repository;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.smellycat.analysis.smells.Smell;
import org.smellycat.architecture.springmvc.SpringMVCArchitecture;
import org.smellycat.domain.Repository;
import org.smellycat.domain.SmellyClass;

public class MultipleQueries implements Smell {

	@Override
	public List<Callable<ASTVisitor>> analyzers(Repository repo, SmellyClass clazz) {
		return Arrays.asList(
			() -> new MultiplePersistenceInvocationsVisitor(clazz)
		);
	}

	@Override
	public boolean conciliate(SmellyClass clazz) {
		
		boolean doMultiplePersistenceInvocations = clazz.getAttribute("multiple-persistence-invocations") == 1;
		
		if(clazz.is(SpringMVCArchitecture.REPOSITORY) && doMultiplePersistenceInvocations) {
			clazz.smells("Multiple queries", String.format("It does more than one query in a method"));
			return true;
		}
		
		return false;
	}

}
