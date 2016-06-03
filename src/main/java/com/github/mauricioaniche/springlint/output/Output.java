package com.github.mauricioaniche.springlint.output;

import java.util.List;
import java.util.Map;

import com.github.mauricioaniche.springlint.architecture.Architecture;
import com.github.mauricioaniche.springlint.domain.Repository;

import br.com.aniche.ck.CKNumber;

public interface Output {
	void printOutput(Architecture arch, Map<String, List<CKNumber>> ckResults, Repository smellResults);
}
