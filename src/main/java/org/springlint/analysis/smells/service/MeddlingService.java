package org.springlint.analysis.smells.service;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.springlint.analysis.smells.Smell;
import org.springlint.architecture.SpringMVCArchitecture;
import org.springlint.domain.Repository;
import org.springlint.domain.SmellyClass;

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
