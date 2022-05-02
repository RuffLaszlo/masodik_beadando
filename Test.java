import szimulacio.Szimulacio;
import fauna.Hangya;
import flora.Elelem;
import geo.AntiVilagKivetel;
import geo.Pont;

public class Test {

	private static Pont origo;
	private static Szimulacio sim;
	private static Hangya ant;

	public static void main(String[] args) {
		try {
			testPosition();
			testFoodSource();
			testSimulation();
			testAnt();
			System.out.println("Minden rendben");
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
	}

	private static void testPosition() {
		Pont pos;
		try {
			pos = new Pont(3, 4);
			Pont pos_ = new Pont(3, 4);
			Pont pos2 = new Pont(4, 4);
			Pont pos3 = new Pont(3, 5);
			Pont pos4 = new Pont(0, 5);
			Pont pos4_ = new Pont(0, 5);
			origo = new Pont(0, 0);

			assertFalse(pos.equals(origo));
			assertTrue(pos.equals(pos_));

			assertTrue(pos.kelet().equals(pos2));
			assertTrue(pos.eszak().equals(pos3));
			assertTrue(pos.eszak().del().equals(pos));
			assertTrue(pos.kelet().nyugat().equals(pos));

			pos_.lepesAdottIranyba(origo);
			assertTrue(pos_.equals(pos.nyugat()));

			pos4_.lepesAdottIranyba(origo);
			assertTrue(pos4_.equals(pos4.del()));
		} catch (AntiVilagKivetel e) {
		}
	}

	private static void testFoodSource() {
		try {
			Elelem fs = new Elelem(2, new Pont(0, 0));
			assertEquals(fs.toString(), "Helyzet: x=0, y=0; Mennyiseg: 2");
			assertTrue(fs.egysegnyitCsokkent());
			assertTrue(fs.egysegnyitCsokkent());
			assertFalse(fs.egysegnyitCsokkent());
		} catch (AntiVilagKivetel e) {
		}
	}

	private static void testSimulation() {
		try {
			sim = new Szimulacio(3, 1, origo, new Elelem(1, new Pont(1, 1)));

			assertEquals(1, sim.hangyak().size());

			ant = sim.hangyak().get(0);
System.out.println(ant.toString());
			assertTrue(ant.toString().equals("Hangyaszám: 1;" +
							 " Helyzet: x=0, y=0;" + 
							 " Izgatottság: 0.0;" +
							 " Megtett lépések: 0;" +
							 " Visz-e élelmet: nem"));
			assertTrue(ant.helyzet().equals(origo));

			sim.szimulacioLeptetes();

			assertFalse(sim.hangyak().get(0).helyzet().equals(origo));
			assertSimilar(0.0, sim.feromonszint(origo));

			sim.feromonjeloles(origo, 1);
			sim.feromonjeloles(origo, 1);
			assertSimilar(2.0, sim.feromonszint(origo));
		} catch (AntiVilagKivetel e) {
		}
	}

	private static void testAnt() {
		/* the ant will reach the food in at most 200 steps */
		int i = 0;
		while (!ant.viszeElelmet() && i++ < 200) {
			sim.szimulacioLeptetes();
		}
		assertTrue(ant.viszeElelmet());
		assertFalse(ant.helyzet().equals(origo));
		int j = 0;
		/* the ant will reach the colony in 2 steps */
		while (ant.viszeElelmet() && j++ < 3) {
			sim.szimulacioLeptetes();
		}
		assertFalse(ant.viszeElelmet());
	}

	private static void assertFalse(boolean p) {
		if (p)
			throw new RuntimeException("Assertion failed");
	}

	private static void assertTrue(boolean p) {
		if (!p)
			throw new RuntimeException("Assertion failed");
	}

	private static void assertEquals(Object expected, Object actual) {
		if (!expected.equals(actual)) {
			throw new RuntimeException("Assertion failed, expected: "
					+ expected + ", actual: " + actual);
		}
	}

	private static void assertSimilar(double expected, double actual) {
		if (Math.abs(expected - actual) > 0.0001) {
			throw new RuntimeException("Assertion failed, expected: "
					+ expected + ", actual: " + actual);
		}
	}

}
