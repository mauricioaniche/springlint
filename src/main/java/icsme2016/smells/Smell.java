package icsme2016.smells;

import java.util.List;
import java.util.concurrent.Callable;

import org.eclipse.jdt.core.dom.ASTVisitor;

import icsme2016.domain.Repository;
import icsme2016.domain.SmellyClass;

public interface Smell {
	List<Callable<ASTVisitor>> analyzers(Repository repo, SmellyClass clazz);
	boolean conciliate(SmellyClass clazz);
}
