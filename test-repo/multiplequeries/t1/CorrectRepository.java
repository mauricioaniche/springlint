package mfa.t1;

import org.springframework.stereotype.Repository;
import org.hibernate.Session;

@Repository
public class CorrectRepository {
	
	@Autowired
	@PersistenceContext
	private Session session;

	public void m1() {
		String sql = "select * from" +
					 "table where x = 1 and y = 1";

		session
			.createQuery(sql).list();

		String query = "";
		query.toString();
	}

	public void m2() {
		session.createQuery("update * from table").update();
	}
}