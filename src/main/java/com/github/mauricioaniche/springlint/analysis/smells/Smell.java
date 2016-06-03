package com.github.mauricioaniche.springlint.analysis.smells;

import java.util.List;
import java.util.concurrent.Callable;

import org.eclipse.jdt.core.dom.ASTVisitor;

import com.github.mauricioaniche.springlint.domain.Repository;
import com.github.mauricioaniche.springlint.domain.SmellyClass;

public interface Smell {
	List<Callable<ASTVisitor>> analyzers(Repository repo, SmellyClass clazz);
	boolean conciliate(SmellyClass clazz);
}
