package mfa.t1;

import org.springframework.stereotype.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@X
class InvoiceController {

	@Autowired
	private InvoiceService service;

	@RequestMapping
	public void add() {

		service.action1();
	}

	@Bla
	@RequestMapping("/bla")
	public void update(String p1, String p2) {

		OtherInvoiceService other = new OtherInvoiceService();
		other.action2();
	}

	public void delete(String p1, String p2) {

		service.action2();
	}
}
