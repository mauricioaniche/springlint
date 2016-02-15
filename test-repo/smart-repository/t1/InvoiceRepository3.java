package mfa.t1;

import org.springframework.stereotype.Repository;

@Repository
public class InvoiceRepository3 {
	
	@Autowired
	@PersistenceContext
	private Session session;

	public void m1() {

		session.createQuery("select * from table where x = 1 and y = 1").list();
	}
}