package mfa.t1;

import org.springframework.stereotype.Service;
import org.hibernate.Session;

@Service
class HibernateService {

	private Session session;

	public void m1() {
		session
			.createQuery("select * from bla")
			.execute();
	}
}
