package mfa.t1;

import org.springframework.stereotype.Service;

@Service
class OtherInvoiceService {

	public void action1() {
		new InvoiceService().action1();
	}

	public void action2() {

	}
}
