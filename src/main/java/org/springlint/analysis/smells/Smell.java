package org.springlint.analysis.smells;

import java.util.List;
import java.util.concurrent.Callable;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.springlint.domain.Repository;
import org.springlint.domain.SmellyClass;

public interface Smell {
	List<Callable<ASTVisitor>> analyzers(Repository repo, SmellyClass clazz);
	boolean conciliate(SmellyClass clazz);
}
