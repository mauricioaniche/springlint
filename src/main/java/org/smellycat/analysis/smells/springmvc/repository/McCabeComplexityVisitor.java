package org.smellycat.analysis.smells.springmvc.repository;

import org.smellycat.domain.SmellyClass;

import br.com.aniche.ck.metric.WMC;

public class McCabeComplexityVisitor extends WMC {

	private SmellyClass clazz;

	public McCabeComplexityVisitor(SmellyClass clazz) {
		this.clazz = clazz;
	}
	
	@Override
	protected void increaseCc(int qtd) {
		super.increaseCc(qtd);
		clazz.setAttribute("mccabe", cc);
	}
	
}
