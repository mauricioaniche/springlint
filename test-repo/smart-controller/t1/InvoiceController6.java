package mfa.t1;

import org.springframework.web.servlet.ModelAndView;

public class InvoiceController6 {

	public ModelAndView show(int id) {
		ModelAndView mv = new ModelAndView("seila.jsp");

		Invoice inv1, inv2; 
		inv1 = new Invoice();
		inv2 = new Invoice();

		inv1.magic();
		inv2.magic();

		mv.addObject("a", 1);
		mv.addObject("b", 2);
		return mv;
	}

}
