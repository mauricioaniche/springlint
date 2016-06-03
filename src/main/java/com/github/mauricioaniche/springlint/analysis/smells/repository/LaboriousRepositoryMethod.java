package com.github.mauricioaniche.springlint.analysis.smells.repository;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;

import org.eclipse.jdt.core.dom.ASTVisitor;

import com.github.mauricioaniche.springlint.analysis.smells.Smell;
import com.github.mauricioaniche.springlint.architecture.SpringMVCArchitecture;
import com.github.mauricioaniche.springlint.domain.Repository;
import com.github.mauricioaniche.springlint.domain.SmellyClass;

public class LaboriousRepositoryMethod implements Smell {

	@Override
	public List<Callable<ASTVisitor>> analyzers(Repository repo, SmellyClass clazz) {
		return Arrays.asList(
			() -> new MultiplePersistenceInvocationsVisitor(clazz)
		);
	}

	@Override
	public boolean conciliate(SmellyClass clazz) {
		
		boolean doMultiplePersistenceInvocations = clazz.getAttribute("multiple-persistence-invocations") == 1;
		String badMethods = clazz.getNote("multiple-persistence-invocations-violations");
		
		if(clazz.is(SpringMVCArchitecture.REPOSITORY) && doMultiplePersistenceInvocations) {
			clazz.smells("Laborious Repository Method", String.format("The methods %s do more than one persistence action", badMethods));
			return true;
		}
		
		return false;
	}

}
