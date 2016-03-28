package org.smellycat.analysis.smells.springmvc.service;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.smellycat.analysis.smells.Smell;
import org.smellycat.architecture.springmvc.SpringMVCArchitecture;
import org.smellycat.domain.Repository;
import org.smellycat.domain.SmellyClass;

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
