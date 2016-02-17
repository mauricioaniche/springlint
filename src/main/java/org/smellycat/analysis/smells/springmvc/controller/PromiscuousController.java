package org.smellycat.analysis.smells.springmvc.controller;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.smellycat.analysis.smells.Smell;
import org.smellycat.architecture.springmvc.SpringMVCArchitecture;
import org.smellycat.domain.Repository;
import org.smellycat.domain.SmellyClass;

public class PromiscuousController implements Smell {

	private static final int ROUTES_THRESHOLD = 12;
	private static final int SERVICES_THRESHOLD = 6;
	
	@Override
	public List<Callable<ASTVisitor>> analyzers(Repository repo, SmellyClass clazz) {
		return Arrays.asList(
			() -> new NumberOfRoutesVisitor(clazz),
			() -> new NumberOfServicesVisitor(repo, clazz)
		);
	}
	
	@Override
	public boolean conciliate(SmellyClass clazz) {
		
		int routes = clazz.getAttribute("number-of-routes");
		int services = clazz.getAttribute("number-of-services-as-dependencies");
		
		boolean hasManyRoutes = routes >= ROUTES_THRESHOLD;
		boolean hasManyServices = services >= SERVICES_THRESHOLD;
		
		if(clazz.is(SpringMVCArchitecture.CONTROLLER) && hasManyRoutes && hasManyServices) {
			clazz.smells("Promiscuous Controller", String.format("It has %d routes and %d services", routes, services));
			return true;
		}
		
		return false;
	}
	


}
