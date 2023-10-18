package ticketmachine;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;

class TicketMachineTest {
	private static final int PRICE = 50; // Une constante

	private TicketMachine machine; // l'objet à tester

	@BeforeEach
	public void setUp() {
		machine = new TicketMachine(PRICE); // On initialise l'objet à tester
	}

	@Test
	// On vérifie que le prix affiché correspond au paramètre passé lors de l'initialisation
	// S1 : le prix affiché correspond à l’initialisation.
	void priceIsCorrectlyInitialized() {
		// Paramètres : valeur attendue, valeur effective, message si erreur
		assertEquals(PRICE, machine.getPrice(), "Initialisation incorrecte du prix");
	}

	@Test
	// S2 : la balance change quand on insère de l’argent
	void insertMoneyChangesBalance() {
		machine.insertMoney(10);
		machine.insertMoney(20);
                // Les montants ont été correctement additionnés  
		assertEquals(10 + 20, machine.getBalance(), "La balance n'est pas correctement mise à jour");              
	}

	@Test
	// S3 : on n’imprime pas leticket si le montant inséré est insuffisant
	void notPrintTicketIfMoneyIsInsufficient(){
		machine.insertMoney(PRICE-1);
		assertFalse(machine.printTicket(),
				"Le payement n'est pas effectué donc on n'imprime pas de ticket");
	}

	@Test
	// S4 : on imprime le ticket si le montant inséré est suffisant
	void printTicketIfMoneyIsSufficient(){
		machine.insertMoney(PRICE+1);
		assertTrue(machine.printTicket(), "Payement effectué donc impression du ticket");
	}

	@Test
	// S5 : Quand on imprime un ticket la balance est décrémentée du prix du ticket
	void decreasePriceOnBalance(){
		machine.insertMoney(40);
		machine.insertMoney(20); //on a 60 donc plus que le prix
		machine.printTicket();
		assertEquals(60-PRICE, machine.getBalance(),
				"La balance a été correctement effectuée");
	}

	@Test
	// S6 : le montant collecté est mis à jour quand on imprime un ticket (pas avant)
	void BalanceUpdatePrintTicket(){
		machine.insertMoney(70);
		machine.printTicket();
		assertEquals(PRICE, machine.getTotal()); // on doit obtenir 50
	}

	@Test
	// S7 : refund()rendcorrectement la monnaie
	void returnMoney(){
		machine.insertMoney(70);
		machine.printTicket();
		assertEquals(machine.getBalance(), machine.refund()); // on doit obtenir 20
	}

	@Test
	// S8 : refund()remet la balance à zéro
	void pullBalanceToNull(){
		machine.insertMoney(70);
		machine.printTicket();
		machine.refund();
		assertEquals(0, machine.getBalance());
	}

}
