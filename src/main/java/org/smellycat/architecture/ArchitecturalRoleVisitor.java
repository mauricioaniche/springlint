package org.smellycat.architecture;

import org.eclipse.jdt.core.dom.ASTVisitor;

import br.com.aniche.ck.metric.Metric;

public abstract class ArchitecturalRoleVisitor extends ASTVisitor implements Metric {
	
	public abstract ArchitecturalRole getRole();

}
