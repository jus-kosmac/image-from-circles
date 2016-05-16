
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;

@SuppressWarnings("serial")
public class Okno extends JFrame implements ActionListener {

	private Platno platno;
	private JButton naslednjaSlika;
	private JButton izrisiSliko;
	private BufferedImage slika;

	
	public Okno() throws IOException {
		super();
		
		platno = new Platno("gol.jpg");
		this.setTitle("Slika iz krogcev");
		this.getContentPane().setLayout(new GridBagLayout());
		GridBagConstraints platnoLayout = new GridBagConstraints();
		platnoLayout.gridx = 0;
		platnoLayout.gridy = 0;
		this.getContentPane().add(platno, platnoLayout);
		
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}

}
