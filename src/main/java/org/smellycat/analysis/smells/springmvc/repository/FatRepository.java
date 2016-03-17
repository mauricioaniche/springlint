package org.smellycat.analysis.smells.springmvc.repository;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.smellycat.analysis.smells.Smell;
import org.smellycat.architecture.springmvc.SpringMVCArchitecture;
import org.smellycat.domain.Repository;
import org.smellycat.domain.SmellyClass;

public class FatRepository implements Smell {

	private static final int ENTITY_THRESHOLD = 1;

	@Override
	public List<Callable<ASTVisitor>> analyzers(Repository repo, SmellyClass clazz) {
		return Arrays.asList(
			() -> new NumberOfEntitiesVisitor(repo, clazz)
		);
	}

	@Override
	public boolean conciliate(SmellyClass clazz) {
		
		int entities = clazz.getAttribute("number-of-entities-as-dependencies");
		String listOfEntities = clazz.getNote("number-of-entities-as-dependencies-violations");
		
		boolean hasHighEntities = entities > ENTITY_THRESHOLD;
		
		if(clazz.is(SpringMVCArchitecture.REPOSITORY) && hasHighEntities) {
			clazz.smells("Fat Repository", String.format("It depends upon %d entities (%s)", entities, listOfEntities));
			return true;
		}
		
		return false;
	}
}
