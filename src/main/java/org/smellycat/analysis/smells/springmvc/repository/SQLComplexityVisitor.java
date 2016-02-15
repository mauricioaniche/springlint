package org.smellycat.analysis.smells.springmvc.repository;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.StringLiteral;
import org.smellycat.domain.SmellyClass;

public class SQLComplexityVisitor extends ASTVisitor {

	private SmellyClass clazz;
	private int complexity;
	
	private static Set<String> complexityKeywords;
	static {
		complexityKeywords = new HashSet<String>();
		complexityKeywords.add("where");
		complexityKeywords.add("and");
		complexityKeywords.add("or");
		complexityKeywords.add("join");
		complexityKeywords.add("exists");
		complexityKeywords.add("not");
		complexityKeywords.add("from");
		complexityKeywords.add("xor");
		complexityKeywords.add("if");
		complexityKeywords.add("else");
		complexityKeywords.add("case");

		complexityKeywords.add(" in"); // there's a space so that it doesnt conflict with 'join'. better approach needed.
	}

	public SQLComplexityVisitor(SmellyClass clazz) {
		this.clazz = clazz;
		this.complexity = 0;
		update();
	}
	
	public boolean visit(StringLiteral node) {
		String sql = node.getLiteralValue();
		if(isSql(sql)) {
			calculateComplexity(sql);
			update();
		}
		
		return super.visit(node);
	}

	private void update() {
		clazz.setAttribute("sql-complexity", complexity);
	}

	private void calculateComplexity(String sql) {
		sql = sql.toLowerCase();
		sql = sql.replace("(", " ");
		sql = sql.replace(")", " ");
		
		for(String keyword : complexityKeywords) {
			complexity += StringUtils.countMatches(sql, keyword);
		}
	}

	private boolean isSql(String sql) {
		// TODO can we actually know this is a sql?
		return true;
	}
}
