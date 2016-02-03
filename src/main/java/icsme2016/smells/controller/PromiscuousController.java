package icsme2016.smells.controller;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;

import org.eclipse.jdt.core.dom.ASTVisitor;

import icsme2016.domain.ArchitecturalRole;
import icsme2016.domain.Repository;
import icsme2016.domain.SmellyClass;
import icsme2016.smells.Smell;

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
