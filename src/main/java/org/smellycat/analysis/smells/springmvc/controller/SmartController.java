package org.smellycat.analysis.smells.springmvc.controller;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.smellycat.analysis.smells.Smell;
import org.smellycat.architecture.springmvc.SpringMVCArchitecture;
import org.smellycat.domain.Repository;
import org.smellycat.domain.SmellyClass;

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
