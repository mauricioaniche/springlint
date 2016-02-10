package mfa.t1;

import mfa.t1.p.Pet;
import org.springframework.stereotype.Repository;

@Repository
public class InvoiceRepository2 {
	
	public void add(Invoice inv) {}

	public void remove(Invoice inv) {}

	public void remove(Item it) {}

	public void remove(Product it) {}

	public void remove(Pet it) {}
}