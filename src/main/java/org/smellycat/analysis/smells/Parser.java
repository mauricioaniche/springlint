package org.smellycat.analysis.smells;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.FileASTRequestor;

import com.google.common.collect.Lists;

import br.com.aniche.ck.FileUtils;

public class Parser {
	
	private static Logger log = Logger.getLogger(Parser.class);

	private static final int MAX_AT_ONCE;
	private List<List<String>> partitions;

	private String[] srcDirs;

	private String[] javaFiles;

	static {
		long maxMemory= Runtime.getRuntime().maxMemory() / (1 << 20); // in MiB
		
		if      (maxMemory >= 2000) MAX_AT_ONCE= 400;
		else if (maxMemory >= 1500) MAX_AT_ONCE= 300;
		else if (maxMemory >= 1000) MAX_AT_ONCE= 200;
		else if (maxMemory >=  500) MAX_AT_ONCE= 100;
		else                        MAX_AT_ONCE=  25;
	}
	
	
	public Parser(String projectPath) {
		srcDirs = FileUtils.getAllDirs(projectPath);
		javaFiles = FileUtils.getAllJavaFiles(projectPath);
		
		partitions = Lists.partition(Arrays.asList(javaFiles), MAX_AT_ONCE);
		log.info(String.format("Found %d java files. We divided into %d partitions", javaFiles.length, partitions.size()));
	}
	
	public void execute(FileASTRequestor storage) {
		
		for(List<String> partition : partitions) {
			ASTParser parser = ASTParser.newParser(AST.JLS8);
			
			parser.setResolveBindings(true);
			parser.setBindingsRecovery(true);
			
			Map<?, ?> options = JavaCore.getOptions();
			JavaCore.setComplianceOptions(JavaCore.VERSION_1_8, options);
			parser.setCompilerOptions(options);
			parser.setEnvironment(null, srcDirs, null, true);
			parser.createASTs(partition.toArray(new String[partition.size()]), null, new String[0], storage, null);
		}
	}
}
