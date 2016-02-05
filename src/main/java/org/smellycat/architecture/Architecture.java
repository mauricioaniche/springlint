package org.smellycat.architecture;

import java.util.List;

public interface Architecture {
	List<ArchitecturalRole> roles();
	ArchitecturalRoleVisitor roleVisitor();
	String thresholdsFile();
}
