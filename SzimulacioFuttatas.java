import szimulacio.Szimulacio;
import fauna.Hangya;
import flora.Elelem;
import geo.AntiVilagKivetel;
import geo.Pont;

public class SzimulacioFuttatas {

	Szimulacio szimulacio;
	final int szimulacioHossz;

	/*public static void main(String[] args) {
		SzimulacioFuttatas tesztelo = null;
		try {
			tesztelo = new SzimulacioFuttatas(100, new Szimulacio(9, 20,
					new Pont(4, 4), new Elelem(100, new Pont(6, 8))));
		} catch (AntiVilagKivetel e) {
		}
		if (tesztelo != null) {
			tesztelo.szimulacioFuttatas();
		}
	}*/
	
	public static void main(String[] args) {
		SzimulacioFuttatas tesztelo = null;
		try {
			tesztelo = new SzimulacioFuttatas(1, new Szimulacio(9, 5,
					new Pont(4, 4), new Elelem(100, new Pont(6, 8))));
		} catch (AntiVilagKivetel e) {
		}
		if (tesztelo != null) {
			tesztelo.szimulacioFuttatas();
		}
	}

	public SzimulacioFuttatas(int szimulacioHossz, Szimulacio szimulacio) {
		this.szimulacioHossz = szimulacioHossz;
		this.szimulacio = szimulacio;
	}

	public void szimulacioFuttatas() {
		for (int i = 0; i < szimulacioHossz; i++) {
			kiiras();
			szimulacio.szimulacioLeptetes();
		}
		System.out.println("Begyűjtött élelemmennyiség: "
				+ szimulacio.begyujtottElelemMennyiseg);
	}

	public void kiiras() {
		sorKiiras();
		for (int i = 0; i < szimulacio.meret(); i++) {
			System.out.print("|");
			for (int j = 0; j < szimulacio.meret(); j++) {
				System.out.print(cellaKiiras(i, j));
			}
			System.out.println("|");
		}
	}

	private void sorKiiras() {
		for (int i = 0; i < szimulacio.meret() + 1; i++) {
			System.out.print("-");
		}
		System.out.println();
	}

	private char cellaKiiras(int i, int j) {
		Pont helyzet = null;
		try {
			helyzet = new Pont(i, j);
			if (szimulacio.elelemForras().helyzet().equals(helyzet)) {
				return 'E';
			}
			if (szimulacio.hangyabolyHelyzet().equals(helyzet)) {
				return 'O';
			}
			for (Hangya hangya : szimulacio.hangyak()) {
				if (hangya.helyzet().equals(helyzet)) {
					if (hangya.viszeElelmet()) {
						return '+';
					}
				}
			}
			for (Hangya hangya : szimulacio.hangyak()) {
				if (hangya.helyzet().equals(helyzet)) {
					return 'H';
				}
			}
			if (szimulacio.feromonszint(helyzet) > 0.1) {
				return '.';
			}
		} catch (AntiVilagKivetel e) {
			e.printStackTrace();
		}
		return ' ';
	}
}
