package mfa.t1;

import org.springframework.stereotype.Service;
import javax.persistence.EntityManager;

@Service
class JPAService {

	private EntityManager em;

	public void m1() {
		em
			.createQuery("select * from bla")
			.execute();
	}
}
