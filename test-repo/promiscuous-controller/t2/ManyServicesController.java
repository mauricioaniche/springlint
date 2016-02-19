package mfa.t2;

import org.springframework.stereotype.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import mfa.t2.p1.InvoiceService1;

@Controller
@X
class ManyServicesController {

	@Autowired
	private InvoiceService1 service1;
	@Autowired
	private InvoiceService2 service2;
	@Autowired
	private InvoiceService3 service3;
	@Autowired
	private InvoiceService4 service4;
	@Autowired
	private InvoiceService5 service5;
	@Autowired
	private InvoiceService6 service6;

	@RequestMapping
	public void m1() {

		service1.action1();
		service2.action1();
		service3.action1();
		service4.action1();
		service5.action1();
		service6.action1();
	}

}
