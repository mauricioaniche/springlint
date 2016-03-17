package org.smellycat.analysis.smells.springmvc.repository;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.smellycat.analysis.smells.Smell;
import org.smellycat.architecture.springmvc.SpringMVCArchitecture;
import org.smellycat.domain.Repository;
import org.smellycat.domain.SmellyClass;

public class SmartRepository implements Smell {

	private static final int SQL_THRESHOLD = 29;
	private static final int MCCABE_THRESHOLD = 24;

	@Override
	public List<Callable<ASTVisitor>> analyzers(Repository repo, SmellyClass clazz) {
		return Arrays.asList(
			() -> new SQLComplexityVisitor(clazz),
			() -> new McCabeComplexityVisitor(clazz)
		);
	}

	@Override
	public boolean conciliate(SmellyClass clazz) {
		
		int sqlComplexity = clazz.getAttribute("sql-complexity");
		int mcCabeComplexity = clazz.getAttribute("mccabe");
		
		boolean hasHighSqlComplexity = sqlComplexity > SQL_THRESHOLD;
		boolean hasHighMcCabeComplexity = mcCabeComplexity > MCCABE_THRESHOLD;
		
		if(clazz.is(SpringMVCArchitecture.REPOSITORY) && hasHighSqlComplexity && hasHighMcCabeComplexity) {
			clazz.smells("Smart Repository", String.format("It has SQL complexity of %d and McCabe of %d", sqlComplexity, mcCabeComplexity));
			return true;
		}
		
		return false;
	}
}
