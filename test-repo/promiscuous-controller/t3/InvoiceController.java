package mfa.t3;

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

}
