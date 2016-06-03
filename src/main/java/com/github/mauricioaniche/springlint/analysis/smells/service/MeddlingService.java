package com.github.mauricioaniche.springlint.analysis.smells.service;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;

import org.eclipse.jdt.core.dom.ASTVisitor;

import com.github.mauricioaniche.springlint.analysis.smells.Smell;
import com.github.mauricioaniche.springlint.architecture.SpringMVCArchitecture;
import com.github.mauricioaniche.springlint.domain.Repository;
import com.github.mauricioaniche.springlint.domain.SmellyClass;

public class MeddlingService implements Smell {

	@Override
	public List<Callable<ASTVisitor>> analyzers(Repository repo, SmellyClass clazz) {
		return Arrays.asList(
			() -> new UsePersistenceMechanismVisitor(clazz)
		);
	}
	@Override
	public boolean conciliate(SmellyClass clazz) {
		
		boolean doMultiplePersistenceInvocations = clazz.getAttribute("use-persistence-mechanism") == 1;
		
		if(clazz.is(SpringMVCArchitecture.SERVICE) && doMultiplePersistenceInvocations) {
			clazz.smells("Meddling service", String.format("It uses a persistence mechanism."));
			return true;
		}
		
		return false;
	}
}
