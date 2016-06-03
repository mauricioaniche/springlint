package org.springlint.analysis.smells.controller;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.springlint.analysis.smells.Smell;
import org.springlint.architecture.SpringMVCArchitecture;
import org.springlint.domain.Repository;
import org.springlint.domain.SmellyClass;

public class SmartController implements Smell {

	private static final int RFC_THRESHOLD = 55;

	@Override
	public List<Callable<ASTVisitor>> analyzers(Repository repo, SmellyClass clazz) {
		return Arrays.asList(
			() -> new RFCButSpringVisitor(clazz)
		);
	}

	@Override
	public boolean conciliate(SmellyClass clazz) {
		
		int rfc = clazz.getAttribute("rfc-but-spring");
		
		boolean hasHighRfc = rfc > RFC_THRESHOLD;
		
		if(clazz.is(SpringMVCArchitecture.CONTROLLER) && hasHighRfc) {
			clazz.smells("Smart Controller", String.format("It has RFC=%d", rfc));
			return true;
		}
		
		return false;
	}
}
