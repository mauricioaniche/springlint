package org.springlint.architecture;

import java.util.List;

import org.springlint.analysis.smells.Smell;

public interface Architecture {
	List<ArchitecturalRole> roles();
	ArchitecturalRoleVisitor roleVisitor();
	List<Smell> smells();
}
