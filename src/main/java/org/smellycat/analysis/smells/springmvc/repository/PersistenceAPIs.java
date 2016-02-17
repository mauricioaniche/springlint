package org.smellycat.analysis.smells.springmvc.repository;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.jdt.core.dom.MethodInvocation;

public class PersistenceAPIs {
	private static Set<String> persistenceApis;
	private static Set<String> persistenceMethods;

	static {
		persistenceApis = new HashSet<String>();
		persistenceMethods = new HashSet<String>();
		
		// spring data
		persistenceApis.add("org.springframework.data");
		persistenceMethods.add(".query");

		// hibernate
		persistenceApis.add("org.hibernate");
		persistenceMethods.add(".createQuery");
		persistenceMethods.add(".createSQLQuery");
		persistenceMethods.add(".createFilter");
		persistenceMethods.add(".createNamedQuery");
		persistenceMethods.add(".createCriteria");
		
		// jpa
		persistenceApis.add("javax.persistence");
		persistenceMethods.add(".createNamedQuery");
		persistenceMethods.add(".createNativeQuery");
		persistenceMethods.add(".createQuery");
		persistenceMethods.add(".createStoredProcedure");
		persistenceMethods.add(".getCriteriaBuilder");
		
		// jdbc
		persistenceApis.add("java.sql");
		persistenceMethods.add(".prepareStatement");
		persistenceMethods.add(".createStatement");
		persistenceMethods.add(".prepareCall");
	}
	
	public static Set<String> getPersistenceApis() {
		return persistenceApis;
	}
	
	public static Set<String> getPersistenceMethods() {
		return persistenceMethods;
	}
	
	public static boolean isOnPersistenceMechanism(String type) {
		return persistenceApis.stream().anyMatch(api -> type.startsWith(api));
	}

	public static boolean isOnPersistenceMethod(MethodInvocation node) {
		String expr = node.toString();
		return persistenceMethods.stream().anyMatch(api -> expr.contains(api));
	}

}
