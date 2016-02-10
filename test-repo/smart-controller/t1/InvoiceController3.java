package mfa.t1;

import org.springframework.web.servlet.ModelAndView;

public class InvoiceController3 {

	public ModelAndView show(int id) {
		ModelAndView mv = new ModelAndView("seila.jsp");

		Invoice inv = new Invoice();
		inv.magic();

		mv.addObject("a", 1);
		mv.addObject("b", 2);
		return mv;
	}

	public ModelAndView delete(int id) {
		ModelAndView mv = new ModelAndView("seila.jsp");

		Invoice inv = new Invoice();
		inv.magic2();
		inv.magic3();

		mv.addObject("a", 1);
		mv.addObject("b", 2);
		mv.addObject("c", 3);
		return mv;
	}
}