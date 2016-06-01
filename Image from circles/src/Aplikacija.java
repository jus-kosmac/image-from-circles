import java.io.IOException;


public class Aplikacija {

	public static void main(String[] args) throws IOException {
		// Program lahko zaženemo z neobveznim parametrom, 
		// s katerim povemo, kje se nahajajo slike.
		if (args.length != 0) {
			Okno okno = new Okno(args[0]);
			okno.pack();
			okno.setVisible(true);
		// Če parametra ne podamo, program izbere trenutni direktorij.
		} else {
			Okno okno = new Okno(".");
			okno.pack();
			okno.setVisible(true);

		}
	}
}
