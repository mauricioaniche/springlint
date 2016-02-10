package mfa.t2;

public class MyController {

        public MyController() {
            super("do something");
        }

        public void m1() {
        	m2();
        }

        public void m2() {
        	Invoice i = new Invoice();
        	i.magic();
        }

}