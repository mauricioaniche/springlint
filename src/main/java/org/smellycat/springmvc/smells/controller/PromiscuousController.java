package org.smellycat.springmvc.smells.controller;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.smellycat.springmvc.domain.ArchitecturalRole;
import org.smellycat.springmvc.domain.Repository;
import org.smellycat.springmvc.domain.SmellyClass;
import org.smellycat.springmvc.smells.Smell;

public class PromiscuousController implements Smell {

	private static final int ROUTES_THRESHOLD = 5;
	private static final int SERVICES_THRESHOLD = 2;
	
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
		
		boolean hasManyRoutes = routes > ROUTES_THRESHOLD;
		boolean hasManyServices = services > SERVICES_THRESHOLD;
		
		if(clazz.is(ArchitecturalRole.CONTROLLER) && hasManyRoutes && hasManyServices) {
			clazz.smells("Promiscuous Controller", String.format("It has %d routes and %d services", routes, services));
			return true;
		}
		
		return false;
	}
	


}
