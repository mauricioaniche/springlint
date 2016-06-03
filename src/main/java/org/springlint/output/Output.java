package org.springlint.output;

import java.util.List;
import java.util.Map;

import org.springlint.architecture.Architecture;
import org.springlint.domain.Repository;

import br.com.aniche.ck.CKNumber;

public interface Output {
	void printOutput(Architecture arch, Map<String, List<CKNumber>> ckResults, Repository smellResults);
}
