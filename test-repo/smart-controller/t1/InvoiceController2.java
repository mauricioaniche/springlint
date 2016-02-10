package mfa.t1;

import org.springframework.web.servlet.ModelAndView;

public class InvoiceController2 {

	public ModelAndView show(int id) {
		ModelAndView mv = new ModelAndView("seila.jsp");

		Invoice inv = new Invoice();
		inv.magic();
		inv.magic2();

		mv.addObject("a", 1);
		mv.addObject("b", 2);
		return mv;
	}

}