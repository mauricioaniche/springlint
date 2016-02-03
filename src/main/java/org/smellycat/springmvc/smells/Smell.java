package org.smellycat.springmvc.smells;

import java.util.List;
import java.util.concurrent.Callable;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.smellycat.springmvc.domain.Repository;
import org.smellycat.springmvc.domain.SmellyClass;

public interface Smell {
	List<Callable<ASTVisitor>> analyzers(Repository repo, SmellyClass clazz);
	boolean conciliate(SmellyClass clazz);
}
