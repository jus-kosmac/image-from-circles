
import java.awt.Container;
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
import java.util.Random;

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
	private ArrayList<String> seznamSlik;
	private ArrayList<String> kopijaSlik;
	private JMenuItem shraniMenu;
	private JMenuItem odpriMenu;
	private JMenuItem izhodMenu;
	private JMenuItem krogecMenu;
	private JMenuItem trikotnikMenu;
	private JMenuItem paralelogramMenu;
	private JMenuItem osemkotnikMenu;
	
	final String[] dovoljeneKoncnice = new String[]{"jpg", "bmp", "png", "jpeg"};

	
	public Okno() throws IOException {
		super();
		
		seznamSlik = new ArrayList<String>();
		
		File mapa = new File("./slike");
		File[] seznamDatotek = mapa.listFiles();
		for (File datoteka : seznamDatotek) {
			if (datoteka.isFile() && 
				Arrays.asList(dovoljeneKoncnice).contains(datoteka.getName().substring(datoteka.getName().lastIndexOf(".") + 1))) {
				seznamSlik.add("./slike/" + datoteka.getName());
			}
		}
		kopijaSlik = new ArrayList<String>(seznamSlik);
		Collections.shuffle(kopijaSlik);
		
		
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
		
		if (seznamSlik.size() == 0) {
			platno = new Platno(true);
			// NAPIŠI DA NI SLIKE
		}

		this.setTitle("Slika iz krogcev");
		this.getContentPane().setLayout(new GridBagLayout());
		GridBagConstraints platnoLayout = new GridBagConstraints();
		platnoLayout.gridx = 0;
		platnoLayout.gridy = 0;
		platnoLayout.gridwidth = 3;
		this.getContentPane().add(platno, platnoLayout);
		
		// Menu
		JMenuBar menuBar = new JMenuBar();
		this.setJMenuBar(menuBar);
		JMenu datotekaMenu = new JMenu("Datoteka");
		JMenu oblikaMenu = new JMenu("Izberi obliko");
		menuBar.add(datotekaMenu);
		menuBar.add(oblikaMenu);
		odpriMenu = new JMenuItem("Odpri sliko");
		shraniMenu = new JMenuItem("Shrani sliko");
		izhodMenu = new JMenuItem("Izhod");
		krogecMenu = new JMenuItem("Krogec");
		trikotnikMenu = new JMenuItem("Trikotnik");
		paralelogramMenu = new JMenuItem("Paralelogram");
		osemkotnikMenu = new JMenuItem("Osemkotnik");
		
		datotekaMenu.add(odpriMenu);
		datotekaMenu.add(shraniMenu);
		datotekaMenu.add(izhodMenu);
		oblikaMenu.add(krogecMenu);
		oblikaMenu.add(trikotnikMenu);
		oblikaMenu.add(paralelogramMenu);
		oblikaMenu.add(osemkotnikMenu);
		
		odpriMenu.addActionListener(this);
		shraniMenu.addActionListener(this);
		izhodMenu.addActionListener(this);
		krogecMenu.addActionListener(this);
		trikotnikMenu.addActionListener(this);
		paralelogramMenu.addActionListener(this);
		osemkotnikMenu.addActionListener(this);
		
		// Gumbi
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
	
	public BufferedImage novaSlika(String ime) throws IOException {
		File datoteka = new File(ime);
		BufferedImage slika = ImageIO.read(datoteka);
		return slika;
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == naslednjaSlika) {
			if (kopijaSlik.size() == 0 && seznamSlik.size() != 0) {
				kopijaSlik = new ArrayList<String>(seznamSlik);
				Collections.shuffle(kopijaSlik);
			}
			
			while (kopijaSlik.size() != 0) {
				try {
					slika = novaSlika(kopijaSlik.get(0));
					platno.spremeniSliko(slika);
					kopijaSlik.remove(0);
					this.pack();
					break;
				} catch (IOException|NullPointerException e1) {
					e1.printStackTrace();
					seznamSlik.remove(kopijaSlik.get(0));
					kopijaSlik.remove(0);
					if (kopijaSlik.size() == 0 && seznamSlik.size() != 0) {
						kopijaSlik = new ArrayList<String>(seznamSlik);
						Collections.shuffle(kopijaSlik);
					}
					
				}
			}
			
			if (seznamSlik.size() == 0) {
				platno.brezSlike();
				this.pack();
			}
			
			
			
		}
		
		if (e.getSource() == izhodMenu) {
			System.exit(0);
		}
		
		if (e.getSource() == izrisiSliko) {
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
		
		if (e.getSource() == odpriMenu) {
			JFileChooser fileChooser = new JFileChooser();
			FileNameExtensionFilter filter = new FileNameExtensionFilter("Image Files", "jpg", "png", "bmp", "jpeg");
			fileChooser.setFileFilter(filter);
			int returnValue = fileChooser.showOpenDialog(this);
			if (returnValue == JFileChooser.APPROVE_OPTION) {
				File izbranaDatoteka = fileChooser.getSelectedFile();
				try {
					BufferedImage slika = ImageIO.read(izbranaDatoteka);
					platno.niSlike = false;
					platno.spremeniSliko(slika);
					this.pack();
				} catch (IOException|NullPointerException e1) {
					e1.printStackTrace();
					JOptionPane.showMessageDialog(null, "Slika ni veljavna.", "Slike ni mogoče odpreti" , JOptionPane.INFORMATION_MESSAGE);
				}
				
			}
		}
		
		if (e.getSource() == shraniMenu) {
			if (platno.niSlike) {
				JOptionPane.showMessageDialog(null, "Ni slike na zaslonu.", "Ni slike", JOptionPane.INFORMATION_MESSAGE);
			} else {
				BufferedImage screenshot = new BufferedImage(platno.slika.getWidth(), platno.slika.getHeight(), BufferedImage.TYPE_INT_ARGB);
				platno.paint(screenshot.getGraphics());
				
				try {
					JFileChooser fileChooser = new JFileChooser();
					FileNameExtensionFilter filter = new FileNameExtensionFilter(".png", "png");
					fileChooser.setFileFilter(filter);
					int returnValue = fileChooser.showSaveDialog(this);
					if (returnValue == JFileChooser.APPROVE_OPTION) {
						File izbranaDatoteka = fileChooser.getSelectedFile();
						if (!izbranaDatoteka.getPath().toLowerCase().endsWith(".png")) {
							File popravljenaDatoteka = new File(izbranaDatoteka.getPath() + ".png");
							ImageIO.write(screenshot, "PNG", popravljenaDatoteka);
						} else {
							ImageIO.write(screenshot, "PNG", izbranaDatoteka);
						}
					}
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
			}
			}
		}
		
	}

}
