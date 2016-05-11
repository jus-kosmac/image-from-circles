import java.awt.Color;
import java.awt.image.BufferedImage;

public class Drevo {
	protected int ax, ay, bx, by;
	protected Drevo zgorajLevo, zgorajDesno, spodajLevo, spodajDesno;
	protected Color barva;
	protected boolean razpadlo;
	public Drevo(BufferedImage slika, int ax, int ay, int bx, int by) {
		this.razpadlo = false;
		this.ax = ax;
		this.ay = ay;
		this.bx = bx;
		this.by = by;
		
		if (bx-ax > 2 && by-ay > 2) {
			int sx = (ax + bx) / 2;
			int sy = (ay + by) / 2;
			zgorajLevo = new Drevo(slika, ax, ay, sx, sy);
			zgorajDesno = new Drevo(slika, sx, ay, bx, sy);
			spodajLevo = new Drevo(slika, ax, sy, sx, by);
			spodajDesno = new Drevo(slika, sx, sy, bx, by);
			int rdeca = (zgorajLevo.barva.getRed() + zgorajDesno.barva.getRed() + spodajLevo.barva.getRed() + spodajDesno.barva.getRed()) / 4;
			int zelena = (zgorajLevo.barva.getGreen() + zgorajDesno.barva.getGreen() + spodajLevo.barva.getGreen() + spodajDesno.barva.getGreen()) / 4;
			int modra = (zgorajLevo.barva.getBlue() + zgorajDesno.barva.getBlue() + spodajLevo.barva.getBlue() + spodajDesno.barva.getBlue()) / 4;
			barva = new Color(rdeca, zelena, modra);
		} else {
			zgorajLevo = zgorajDesno = spodajLevo = spodajDesno = null;
			barva = povprecjeBarv(slika, ax, ay, bx, by);
		}
		
	}
		
	public Color povprecjeBarv(BufferedImage slika, int ax, int ay, int  bx, int by) {
		int rdeca = 0, zelena = 0, modra = 0;
		int steviloPikslov = (bx - ax) * (by - ay);
		for (int i = ax; i < bx; i ++) {
			for (int j = ay; j < by; j ++) {
				Color barvaPiksla = new Color(slika.getRGB(i, j), true);
				rdeca += barvaPiksla.getRed();
				zelena += barvaPiksla.getGreen();
				modra += barvaPiksla.getBlue();
			}
		}
		return new Color(rdeca / steviloPikslov, zelena / steviloPikslov, modra / steviloPikslov);
	}
	
	public static Drevo vsebujeTocko(Drevo drevo, int x, int y) {
		if (drevo.zgorajLevo == null && drevo.zgorajDesno == null && drevo.spodajLevo == null && drevo.spodajDesno == null) {
			return drevo;
		} else {
			if (!drevo.razpadlo) {
				return drevo;
			} else {
				int sx = (drevo.ax + drevo.bx) / 2;
				int sy = (drevo.ay + drevo.by) / 2;
				if (x <= sx) {
					if (y <= sy) {
						return vsebujeTocko(drevo.zgorajLevo, x, y);
					} else {
						return vsebujeTocko(drevo.spodajLevo, x, y);
					}
				} else {
					if (y <= sy) {
						return vsebujeTocko(drevo.zgorajDesno, x, y);
					} else {
						return vsebujeTocko(drevo.spodajDesno, x, y);
					}
				}
			}
		}
	}
	
}
