package mfa.t1;

import org.springframework.web.servlet.ModelAndView;

public class InvoiceController {

	public ModelAndView show(int id) {
		ModelAndView mv = new ModelAndView("seila.jsp");

		mv.addObject("a", 1);
		mv.addObject("b", 2);
		return mv;
	}

}