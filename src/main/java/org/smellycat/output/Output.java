package org.smellycat.output;

import java.util.List;
import java.util.Map;

import org.smellycat.architecture.Architecture;
import org.smellycat.domain.Repository;

import br.com.aniche.ck.CKNumber;

public interface Output {
	void printOutput(Architecture arch, Map<String, List<CKNumber>> ckResults, Repository smellResults);
}
