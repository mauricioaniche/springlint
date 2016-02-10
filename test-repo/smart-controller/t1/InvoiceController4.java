package mfa.t1;

import org.springframework.web.servlet.ModelAndView;

public class InvoiceController4 {

	@Autowired
	private Repo repo;

	public ModelAndView show(int id) {
		ModelAndView mv = new ModelAndView("seila.jsp");

		mv.addObject("a", 1);
		mv.addObject("b", 2);
		return mv;
	}

	public ModelAndView delete(int id) {
		ModelAndView mv = new ModelAndView("seila.jsp");

		mv.addObject("a", 1);
		mv.addObject("b", 2);
		mv.addObject("c", 3);

		repo.get();

		return mv;
	}
}