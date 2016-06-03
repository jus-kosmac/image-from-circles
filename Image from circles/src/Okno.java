
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

@SuppressWarnings("serial")
public class Okno extends JFrame implements ActionListener {

	private Platno platno;
	private JButton naslednjaSlika;
	private JButton izrisiSliko;
	private JButton ponastaviSliko;
	private BufferedImage slika;
	private ArrayList<String> seznamSlik; // V seznamu slik so shranjena imena datotek s slikami, ki smo
										  // jih prebrali iz izbranega direktorija.
	private ArrayList<String> kopijaSlik; // Iz kopije slik jemljemo slike in jih prikazujemo na zaslonu.
	private JMenuItem shraniMenu;
	private JMenuItem odpriMenu;
	private JMenuItem izhodMenu; 
	private JMenuItem osveziMenu; // Osvežimo trenutni direktorij slik.
	private JMenuItem izberiMenu; // Izberemo nov direktorij slik.
	private JMenuItem krogecMenu;
	private JMenuItem trikotnikMenu;
	private JMenuItem paralelogramMenu;
	private JMenuItem osemkotnikMenu;
	private String pot; // Pot je naslov trenutnega direktorija za slike.
	
	// Seznam dovoljenih končnic za slikovne datoteke.
	final String[] dovoljeneKoncnice = new String[]{"jpg", "bmp", "png", "jpeg"};

	
	public Okno(String pot) throws IOException {
		super();
		setResizable(false);
		
		this.pot = pot;
		// Preberemo imena slik iz direktorija.
		preberiSlike(pot);
		
		// Iz kopije slik poskušamo naložiti sliko. Če datoteka v resnici ni slika ali pride do kakšne
		// druge napake, jo program ujame. "Slabo" sliko izbriše iz kopijeSlik in seznamaSlik.
		// Če sliko uspešno naloži, pa jo pobriše samo iz kopije, da jo ne moremo odpreti dvakrat zapored.
		while (kopijaSlik.size() != 0) {
			try {
				slika = novaSlika(kopijaSlik.get(0));
				platno = new Platno(slika);
				kopijaSlik.remove(0);
				break;
			} catch (IOException|NullPointerException e1) {
				e1.printStackTrace();
				seznamSlik.remove(kopijaSlik.get(0));
				kopijaSlik.remove(0);
			}
		}
		
		// Če nismo uspeli prebrati nobene slike, potem pokličemo sekundarni konstruktor platna. 
		if (seznamSlik.size() == 0) {
			platno = new Platno(true);
		}

		// V okno dodamo platno.
		this.setTitle("Slika iz krogcev");
		this.getContentPane().setLayout(new GridBagLayout());
		GridBagConstraints platnoLayout = new GridBagConstraints();
		platnoLayout.gridx = 0;
		platnoLayout.gridy = 0;
		platnoLayout.gridwidth = 3;
		this.getContentPane().add(platno, platnoLayout);
		
		// Ustvarimo menuje in podmenuje.
		JMenuBar menuBar = new JMenuBar();
		this.setJMenuBar(menuBar);
		JMenu datotekaMenu = new JMenu("Datoteka");
		JMenu oblikaMenu = new JMenu("Izberi obliko");
		menuBar.add(datotekaMenu);
		menuBar.add(oblikaMenu);
		odpriMenu = new JMenuItem("Odpri sliko");
		shraniMenu = new JMenuItem("Shrani sliko");
		izhodMenu = new JMenuItem("Izhod");
		izberiMenu = new JMenuItem("Izberi direktorij slik");
		osveziMenu = new JMenuItem("Osveži direktorij");
		krogecMenu = new JMenuItem("Krogec");
		trikotnikMenu = new JMenuItem("Trikotnik");
		paralelogramMenu = new JMenuItem("Paralelogram");
		osemkotnikMenu = new JMenuItem("Osemkotnik");
		
		datotekaMenu.add(odpriMenu);
		datotekaMenu.add(shraniMenu);
		datotekaMenu.add(izberiMenu);
		datotekaMenu.add(osveziMenu);
		datotekaMenu.add(izhodMenu);
		oblikaMenu.add(krogecMenu);
		oblikaMenu.add(trikotnikMenu);
		oblikaMenu.add(paralelogramMenu);
		oblikaMenu.add(osemkotnikMenu);
		
		odpriMenu.addActionListener(this);
		shraniMenu.addActionListener(this);
		izhodMenu.addActionListener(this);
		osveziMenu.addActionListener(this);
		izberiMenu.addActionListener(this);
		krogecMenu.addActionListener(this);
		trikotnikMenu.addActionListener(this);
		paralelogramMenu.addActionListener(this);
		osemkotnikMenu.addActionListener(this);
		
		// Ustvarimo gumbe in jih dodamo v okno.
		naslednjaSlika = new JButton("Naslednja slika");
		naslednjaSlika.addActionListener(this);
		GridBagConstraints naslednjaSlikaLayout = new GridBagConstraints();
		
		izrisiSliko = new JButton("Izriši sliko");
		izrisiSliko.addActionListener(this);
		GridBagConstraints izrisiSlikoLayout = new GridBagConstraints();

		ponastaviSliko = new JButton("Ponastavi sliko");
		ponastaviSliko.addActionListener(this);
		GridBagConstraints ponastaviSlikoLayout = new GridBagConstraints();
		
		naslednjaSlikaLayout.gridx = 0;
		naslednjaSlikaLayout.gridy = 1;
		naslednjaSlikaLayout.weightx = 1;
		
		izrisiSlikoLayout.gridx = 1;
		izrisiSlikoLayout.gridy = 1;
		izrisiSlikoLayout.weightx = 1;
		
		ponastaviSlikoLayout.gridx = 2;
		ponastaviSlikoLayout.gridy = 1;
		ponastaviSlikoLayout.weightx = 1;
		
		this.getContentPane().add(naslednjaSlika, naslednjaSlikaLayout);
		this.getContentPane().add(izrisiSliko, izrisiSlikoLayout);
		this.getContentPane().add(ponastaviSliko, ponastaviSlikoLayout);
		
	}
	
	public void preberiSlike(String pot) throws IOException {
		// Iz direktorija preberemo vse datoteke z ustrezno končnico in imena shranimo v seznamSlik.
		seznamSlik = new ArrayList<String>();
		
		File mapa = new File(pot);
		File[] seznamDatotek = mapa.listFiles();
		for (File datoteka : seznamDatotek) {
			if (datoteka.isFile() && 
				Arrays.asList(dovoljeneKoncnice).contains(datoteka.getName().substring(datoteka.getName().lastIndexOf(".") + 1))) {
				seznamSlik.add(pot + File.separator + datoteka.getName());
			}
		}
		
		// Iz kopije slik bomo dejansko odpirali slike. Kopijo naključno premešano, da ni vedno isti vrsti red slik.
		kopijaSlik = new ArrayList<String>(seznamSlik);
		Collections.shuffle(kopijaSlik);
	}
	
	public BufferedImage novaSlika(String ime) throws IOException {
		// Iz direktorija preberemo sliko z danim imenom in jo vrnemo.
		File datoteka = new File(ime);
		BufferedImage slika = ImageIO.read(datoteka);
		return slika;
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == naslednjaSlika) {
			// Če je kopijaSlik prazna, seznamSlik pa ne, pomeni, da smo kopijo do konca izpraznili.
			// Ustvarimo novo kopijo, da bomo iz nje spet nalagali slike.
			if (kopijaSlik.size() == 0 && seznamSlik.size() != 0) {
				kopijaSlik = new ArrayList<String>(seznamSlik);
				Collections.shuffle(kopijaSlik);
			}
			
			// Ponovno poskušamo naložiti sliko.
			while (kopijaSlik.size() != 0) {
				try {
					slika = novaSlika(kopijaSlik.get(0));
					// Platno povemo, da bo sedaj naložena nova slika (če slučajno prej ni bilo slike)
					platno.niSlike = false;
					platno.spremeniSliko(slika);
					kopijaSlik.remove(0);
					this.pack();
					break;
				} catch (IOException|NullPointerException e1) {
					e1.printStackTrace();
					seznamSlik.remove(kopijaSlik.get(0));
					kopijaSlik.remove(0);
					// V kopijiSlik so lahko preostale samo "slabe" slike (vse "dobre" smo naprimer že porabili).
					// V tem primeru moramo ponovno narediti kopijo, da bomo lahko naložili "dobro" sliko.
					// Sicer bi šli ven iz zanke in ne bi naložili nobene slike, kljub temu, da v direktoriju
					// obstajajo slike, ki niso "slabe".
					if (kopijaSlik.size() == 0 && seznamSlik.size() != 0) {
						kopijaSlik = new ArrayList<String>(seznamSlik);
						Collections.shuffle(kopijaSlik);
					}
					
				}
			}
			
			// Nismo našli nobene slike, ki bo jo lahko naložili. 
			if (seznamSlik.size() == 0) {
				platno.brezSlike();
				this.pack();
			}
					
		}
		
		if (e.getSource() == izhodMenu) {
			System.exit(0);
		}
		
		if (e.getSource() == izrisiSliko) {
			// Izriše celotno sliko.
			platno.narisiSliko = true;
			platno.repaint();
		}
		
		if (e.getSource() == ponastaviSliko) {
			platno.spremeniSliko(platno.slika);
		}
		
		if (e.getSource() == krogecMenu) {
			platno.oblika = "Krogec";
			platno.repaint();
		}
		
		if (e.getSource() == trikotnikMenu) {
			platno.oblika = "Trikotnik";
			platno.repaint();
		}
		
		if (e.getSource() == paralelogramMenu) {
			platno.oblika = "Paralelogram";
			platno.repaint();
		}
		
		if (e.getSource() == osemkotnikMenu) {
			platno.oblika = "Osemkotnik";
			platno.repaint();
		}
		
		if (e.getSource() == osveziMenu) {
			// Iz trenutnega direktorija še enkrat preberemo slike in naložimo novo sliko
			// s tem, da simuliramo pritisk gumba naslednjaSlika.
			try {
				preberiSlike(pot);
				naslednjaSlika.doClick();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		
		if (e.getSource() == izberiMenu) {
			// Ustvarimo objekt razreda JFileChooser.
			JFileChooser fileChooser = new JFileChooser();
			fileChooser.setDialogTitle("Izberi direktorij slik");
			// Izbiramo lahko samo med direktoriji (in ne datotekami).
		    fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		    int returnValue = fileChooser.showOpenDialog(this);
		    if (returnValue == JFileChooser.APPROVE_OPTION) {
				File izbranaDatoteka = fileChooser.getSelectedFile();
				try {
					// Nastavimo novo pot za direktorij slik in preberemo slike.
					this.pot = izbranaDatoteka.getPath();
					preberiSlike(pot);
					naslednjaSlika.doClick();
				} catch (IOException|NullPointerException e1) {
					e1.printStackTrace();
				}
				
			}
		    
		}
		
		if (e.getSource() == odpriMenu) {
			JFileChooser fileChooser = new JFileChooser();
			// Filter nam omogoča, da so uporabniku vidne samo datoteke z ustrezno končnico.
			FileNameExtensionFilter filter = new FileNameExtensionFilter("Image Files", "jpg", "png", "bmp", "jpeg");
			fileChooser.setFileFilter(filter);
			int returnValue = fileChooser.showOpenDialog(this);
			if (returnValue == JFileChooser.APPROVE_OPTION) {
				File izbranaDatoteka = fileChooser.getSelectedFile();
				try {
					// Poskušamo naložiti sliko.
					BufferedImage slika = ImageIO.read(izbranaDatoteka);
					platno.niSlike = false;
					platno.spremeniSliko(slika);
					this.pack();
				} catch (IOException|NullPointerException e1) {
					// Če je prišlo do napake, to uporabniku sporočimo preko okenca za napake.
					e1.printStackTrace();
					JOptionPane.showMessageDialog(null, "Slika ni veljavna.", "Slike ni mogoče odpreti" , JOptionPane.INFORMATION_MESSAGE);
				}
				
			}
		}
		
		if (e.getSource() == shraniMenu) {
			if (platno.niSlike) {
				JOptionPane.showMessageDialog(null, "Ni slike na zaslonu.", "Ni slike", JOptionPane.INFORMATION_MESSAGE);
			} else {
				// V screenshot prerišemo vsebino platna (sliko, kot je trenutno vidna na zaslonu).
				BufferedImage screenshot = new BufferedImage(platno.slika.getWidth(), platno.slika.getHeight(), BufferedImage.TYPE_INT_ARGB);
				platno.paint(screenshot.getGraphics());
				
				try {
					JFileChooser fileChooser = new JFileChooser();
					FileNameExtensionFilter filter = new FileNameExtensionFilter(".png", "png");
					fileChooser.setFileFilter(filter);
					int returnValue = fileChooser.showSaveDialog(this);
					if (returnValue == JFileChooser.APPROVE_OPTION) {
						File izbranaDatoteka = fileChooser.getSelectedFile();
						// Če se ime, ki si ga je uporabnik izbral, ne konča s .png, ročno dodamo končnico .png.
						if (!izbranaDatoteka.getPath().toLowerCase().endsWith(".png")) {
							File popravljenaDatoteka = new File(izbranaDatoteka.getPath() + ".png");
							ImageIO.write(screenshot, "PNG", popravljenaDatoteka);
						} else {
							ImageIO.write(screenshot, "PNG", izbranaDatoteka);
						}
					}
				} catch (IOException e1) {
					e1.printStackTrace();
			}
			}
		}
		
	}

}
