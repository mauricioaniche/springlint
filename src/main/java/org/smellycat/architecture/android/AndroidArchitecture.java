package org.smellycat.architecture.android;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.smellycat.analysis.smells.Smell;
import org.smellycat.architecture.ArchitecturalRole;
import org.smellycat.architecture.ArchitecturalRoleVisitor;
import org.smellycat.architecture.Architecture;

public class AndroidArchitecture implements Architecture {

	public static ArchitecturalRole ACTIVITY = new ArchitecturalRole(1, "activity");
	public static ArchitecturalRole TASK = new ArchitecturalRole(2, "task");
	public static ArchitecturalRole FRAGMENT = new ArchitecturalRole(3, "fragment");

	@Override
	public List<ArchitecturalRole> roles() {
		return Arrays.asList(
			ACTIVITY,
			TASK,
			FRAGMENT
		);
	}

	@Override
	public ArchitecturalRoleVisitor roleVisitor() {
		return new AndroidArchitecturalRoleVisitor();
	}

	@Override
	public String thresholdsFile() {
		return "android.js";
	}

	@Override
	public List<Smell> smells() {
		return Collections.emptyList();
	}

}
