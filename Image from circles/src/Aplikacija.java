import java.io.IOException;


public class Aplikacija {

	public static void main(String[] args) throws IOException {
		if (args.length != 0) {
			Okno okno = new Okno(args[0]);
			okno.pack();
			okno.setVisible(true);
		} else {
			Okno okno = new Okno("./slike");
			okno.pack();
			okno.setVisible(true);

		}
	}
}
