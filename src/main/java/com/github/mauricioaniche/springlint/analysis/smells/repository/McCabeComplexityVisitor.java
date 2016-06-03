package com.github.mauricioaniche.springlint.analysis.smells.repository;

import com.github.mauricioaniche.springlint.domain.SmellyClass;

import br.com.aniche.ck.metric.WMC;

public class McCabeComplexityVisitor extends WMC {

	private SmellyClass clazz;

	public McCabeComplexityVisitor(SmellyClass clazz) {
		this.clazz = clazz;
		update();
	}
	
	@Override
	protected void increaseCc(int qtd) {
		super.increaseCc(qtd);
		update();
	}

	private void update() {
		clazz.setAttribute("mccabe", cc);
	}
	
}
