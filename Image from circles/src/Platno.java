import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class Platno extends JPanel implements MouseMotionListener {

	protected Drevo drevo;
	protected String oblika;
	protected boolean niSlike;
	protected boolean narisiSliko;
	protected BufferedImage slika;
	/**
	 * Create the panel.
	 * @throws IOException 
	 */
	public Platno(BufferedImage slika) throws IOException {
		super();
		this.setBackground(Color.BLACK);
		addMouseMotionListener(this);
		
		narisiSliko = false;
		niSlike = false;
		oblika = "Krogec";
		
		this.slika = slika;
		drevo = new Drevo(slika, 0, 0, slika.getWidth(), slika.getHeight());
		drevo.razpadlo = true;
	}
	
	public Platno(boolean vrednost) {
		super();
		this.setBackground(Color.BLACK);
		addMouseMotionListener(this);
		
		narisiSliko = false;
		niSlike = true;
		oblika = "Krogec";
	}
	
	public void spremeniSliko(BufferedImage slika) {
		if (! niSlike) {
			this.slika = slika;
			drevo = new Drevo(slika, 0, 0, slika.getWidth(), slika.getHeight());
			drevo.razpadlo = true;
			narisiSliko = false;
			setSize(getPreferredSize());
			repaint();
		}
	}
	
	public void brezSlike() {
		niSlike = true;
		setSize(getPreferredSize());
		repaint();
	}
	
	@Override
	public Dimension getPreferredSize() {
		if (niSlike) {
			Dimension dimenzija = new Dimension(500, 500);
			return dimenzija;
		} else {
			Dimension dimenzija = new Dimension(drevo.bx - drevo.ax, drevo.by - drevo.ay);
			return dimenzija;
		}
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (niSlike) {
			String napis = "(ni slike)";
			// nastavimo font za izpis
			g.setFont(new Font("Helvetica", Font.PLAIN, 30));
			g.setColor(Color.WHITE);
			// dobimo objekt fm, ki zna računati vse v zvezi s fontom
			FontMetrics fm = g.getFontMetrics();
			// objekt fm vprašamo, kako velik bo naš napis, da ga znamo
			// centrirati
			Rectangle2D r = fm.getStringBounds(napis, g);
			// naredimo nais, centrirano
			g.drawString(napis, 
					(int)((getWidth() - r.getWidth())/2),
					(int)((getHeight() + r.getHeight())/2));
		} else if (narisiSliko) {
			g.drawImage(slika, 0, 0, this);
		} else {
			narisiDrevo(g, drevo);
		}
	}
	
	public void narisiDrevo(Graphics g, Drevo d) {
		if (d.razpadlo) {
			narisiDrevo(g, d.spodajDesno);
			narisiDrevo(g, d.spodajLevo);
			narisiDrevo(g, d.zgorajDesno);
			narisiDrevo(g, d.zgorajLevo);
		} else if (d.zgorajDesno != null) {
			g.setColor(d.barva);
			if (oblika == "Krogec") {
				g.fillOval(d.ax, d.ay, d.bx - d.ax, d.by - d.ay);
			} else if (oblika == "Trikotnik") {
				int sx = (d.ax + d.bx) / 2;
				g.fillPolygon(new int[]{d.ax, sx, d.bx}, new int[]{d.by, d.ay, d.by}, 3);
			} else if (oblika == "Paralelogram") {
				int sx = (d.ax + d.bx) / 2;
				int sy = (d.ay + d.by) / 2;
				g.fillPolygon(new int[]{d.ax, sx, d.bx, sx}, new int[]{sy, d.ay, sy, d.by}, 4);
			} else if (oblika == "Osemkotnik") {
				int razlikaX = (d.bx - d.ax);
				int razlikaY = (d.by - d.ay);
				int s1x = d.ax + razlikaX / 5;
				int s2x = d.ax + (4 * razlikaX) / 5;
				int s1y = d.ay + razlikaY / 5;
				int s2y = d.ay + (4 * razlikaY) / 5;
				g.fillPolygon(new int[]{d.ax, s1x, s2x, d.bx, d.bx, s2x, s1x, d.ax}, new int[]{s1y, d.ay, d.ay, s1y, s2y, d.by, d.by, s2y}, 8);
			}
		} else {
			g.setColor(d.barva);
			g.fillRect(d.ax, d.ay, d.bx - d.ax, d.by - d.ay);
		}	
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		if (! niSlike && drevo.ax <= e.getX() && drevo.bx >= e.getX() && drevo.ay <= e.getY() && drevo.by >= e.getY()) {
			Drevo d = Drevo.vsebujeTocko(drevo, e.getX(), e.getY());
			if (d.zgorajDesno != null) {
				d.razpadlo = true;
			}
			repaint();
		}
	}

}
