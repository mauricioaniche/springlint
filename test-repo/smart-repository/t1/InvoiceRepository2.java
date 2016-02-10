package mfa.t1;

import org.springframework.stereotype.Repository;

@Repository
public class InvoiceRepository2 {
	
	@Autowired
	@PersistenceContext
	private Session session;

	public void m1() {
		String sql = "select * from" +
					 "table where x = 1 and y = 1";

		session.createQuery(sql).list();
	}

	public void m2() {
		String sql = "select * FROM" +
					 "table a join b on a.a = b.b where x = 1 and y = 1 and not exists ()";

		session.createQuery(sql).list();
	}
}