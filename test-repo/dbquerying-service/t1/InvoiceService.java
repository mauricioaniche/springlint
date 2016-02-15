package mfa.t1;

import org.springframework.stereotype.Service;
import java.sql.Connection;

@Service
class InvoiceService {

	private Connection conn;

	public void m1() {
		conn
			.prepareStatement("select * from bla")
			.executeQuery();
	}
}
