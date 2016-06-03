package com.github.mauricioaniche.springlint.architecture;

import java.util.List;

import com.github.mauricioaniche.springlint.analysis.smells.Smell;

public interface Architecture {
	List<ArchitecturalRole> roles();
	ArchitecturalRoleVisitor roleVisitor();
	List<Smell> smells();
}
