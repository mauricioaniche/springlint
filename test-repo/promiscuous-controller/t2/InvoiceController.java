package mfa.t2;

import org.springframework.stereotype.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@X
class InvoiceController {

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
	}

	@RequestMapping
	public void m2() {

		service2.action1();
	}

	@RequestMapping
	public void m3() {

		service3.action1();
	}

	@RequestMapping
	public void m4() {

		service4.action1();
	}

	@RequestMapping
	public void m5() {

		service5.action1();
	}

	@RequestMapping
	public void m6() {

		service6.action1();
	}

}
