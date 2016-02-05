package org.smellycat.architecture;

import org.smellycat.architecture.android.AndroidArchitecture;
import org.smellycat.architecture.springmvc.SpringMVCArchitecture;

public class ArchitectureFactory {

	public Architecture build(String arch) {
		if(arch.equals("springmvc")) return new SpringMVCArchitecture();
		if(arch.equals("android")) return new AndroidArchitecture();
		
		throw new RuntimeException("unknown architecture: " + arch);
		
	}
}
