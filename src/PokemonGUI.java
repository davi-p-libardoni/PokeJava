import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JToolBar;
import javax.swing.UIManager;
import java.awt.SystemColor;
import javax.swing.border.LineBorder;

import java.awt.CardLayout;
import java.awt.Color;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.JTextArea;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class PokemonGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextArea txtPrompt;
	
	private CardLayout cardLayout;
	private JPanel moveSelectionPanel;
	private JPanel moveResolutionPanel;
	private JPanel switchPokemonPanel;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PokemonGUI frame = new PokemonGUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public PokemonGUI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 523, 360);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel.setBackground(SystemColor.inactiveCaption);
		panel.setBounds(10, 228, 487, 82);
		contentPane.add(panel);
		panel.setLayout(null);
		
		txtPrompt = new JTextArea();
		txtPrompt.setBounds(10, 11, 281, 60);
		panel.add(txtPrompt);
		txtPrompt.setBackground(SystemColor.inactiveCaption);
		txtPrompt.setEditable(false);
		txtPrompt.setColumns(55);
		txtPrompt.setLineWrap(true);
		txtPrompt.setText("What will $mon do?");
		
		JButton btnFight = new JButton("FIGHT");
		btnFight.setBounds(311, 12, 78, 23);
		panel.add(btnFight);
		
		JButton btnPokemon = new JButton("PKMN");
		btnPokemon.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnPokemon.setBounds(311, 46, 78, 23);
		panel.add(btnPokemon);
		
		JButton btnBag = new JButton("BAG");
		btnBag.setBounds(399, 12, 78, 23);
		panel.add(btnBag);
		
		JButton btnRun = new JButton("RUN");
		btnRun.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnRun.setBounds(399, 46, 78, 23);
		panel.add(btnRun);
		
	}
}
