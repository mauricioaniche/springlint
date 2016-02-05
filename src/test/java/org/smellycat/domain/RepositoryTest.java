package org.smellycat.domain;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.smellycat.domain.Repository;
import org.smellycat.domain.SmellyClass;

public class RepositoryTest {

	@Test
	public void findSubtypes() {
		Repository repo = new Repository();
		
		HashSet<String> interfaces = new HashSet<String>();
		interfaces.add("a.b.Interface");
		repo.add("a/b/C.java", "a.b.C", "class", "", interfaces);
		repo.add("a/b/D.java", "a.b.D", "class", "", interfaces);
		repo.add("a/b/E.java", "a.b.E", "class", "", Collections.emptySet());
		
		List<SmellyClass> classes = repo.getSubtypesOf("a.b.Interface");
		
		Assert.assertTrue(classes.contains(repo.getByClass("a.b.C")));
		Assert.assertTrue(classes.contains(repo.getByClass("a.b.D")));
		Assert.assertFalse(classes.contains(repo.getByClass("a.b.E")));
	}
}
