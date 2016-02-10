package mfa.t1;

import org.springframework.web.servlet.ModelAndView;

public class InvoiceController5 {

	@Autowired
	private Repo repo = Utils.helper1();

	public ModelAndView show(int id) {
		Utils.helper2();
	}

}