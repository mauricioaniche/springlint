package org.smellycat.architecture;

import java.util.List;

import org.smellycat.analysis.smells.Smell;

public interface Architecture {
	List<ArchitecturalRole> roles();
	ArchitecturalRoleVisitor roleVisitor();
	String thresholdsFile();
	List<Smell> smells();
}
