import java.awt.EventQueue;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.JToolBar;
import javax.swing.UIManager;
import java.awt.SystemColor;
import javax.swing.border.LineBorder;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;

import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.JTextArea;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class BattleGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JPanel container;
	
	private CardLayout cardLayout;
	
	private JPanel buttonPanel;
	private JPanel actionSelectionPanel;
	private JTextArea txtLog;
	
	private JPanel moveResolutionPanel;
	
	private JPanel switchPokemonPanel;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					BattleGUI frame = new BattleGUI();
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
	public BattleGUI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 520, 400);

        contentPane = new JPanel(new BorderLayout());
        setContentPane(contentPane);

        // --- Battle screen (top) ---
        JPanel battleScreen = new JPanel(new BorderLayout());
        battleScreen.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));

        JLabel enemyLabel = new JLabel("Squirtle   HP: 18/44");
        JLabel playerLabel = new JLabel("Charmander   HP: 30/39");
        enemyLabel.setHorizontalAlignment(SwingConstants.RIGHT);

        battleScreen.add(enemyLabel, BorderLayout.NORTH);
        battleScreen.add(playerLabel, BorderLayout.SOUTH);
        contentPane.add(battleScreen, BorderLayout.NORTH);

        // --- Log area (center) ---
        txtLog = new JTextArea();
        txtLog.setEditable(false);
        txtLog.setLineWrap(true);
        txtLog.setRows(3);
        contentPane.add(new JScrollPane(txtLog), BorderLayout.CENTER);

        // --- Button panels (bottom, swapped with CardLayout) ---
        cardLayout = new CardLayout();
        buttonPanel = new JPanel(cardLayout);

        buttonPanel.add(buildActionPanel(), "ACTIONS");
        buttonPanel.add(buildMovePanel(),   "MOVES");

        contentPane.add(buttonPanel, BorderLayout.SOUTH);
		
	}

	private JPanel buildActionPanel() {
	    JPanel panel = new JPanel(new GridLayout(2, 2, 8, 8));
	    panel.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));

	    JButton btnFight = new JButton("Fight");
	    btnFight.addActionListener(e -> cardLayout.show(buttonPanel, "MOVES"));
	    panel.add(btnFight);

	    JButton btnPokemon = new JButton("Pokémon");
	    btnPokemon.addActionListener(e -> log("Choose a Pokémon to switch to."));
	    panel.add(btnPokemon);

	    JButton btnBag = new JButton("Bag");
	    btnBag.addActionListener(e -> log("Choose an item."));
	    panel.add(btnBag);

	    JButton btnRun = new JButton("Run");
	    btnRun.addActionListener(e -> log("Got away safely!"));
	    panel.add(btnRun);

	    return panel;
	}

	private JPanel buildMovePanel() {
	    JPanel panel = new JPanel(new GridLayout(3, 2, 8, 8));
	    panel.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));

	    JButton btnEmber = new JButton("Ember");
	    btnEmber.addActionListener(e -> useMove("Ember"));
	    panel.add(btnEmber);

	    JButton btnScratch = new JButton("Scratch");
	    btnScratch.addActionListener(e -> useMove("Scratch"));
	    panel.add(btnScratch);

	    JButton btnGrowl = new JButton("Growl");
	    btnGrowl.addActionListener(e -> useMove("Growl"));
	    panel.add(btnGrowl);

	    JButton btnSmokescreen = new JButton("Smokescreen");
	    btnSmokescreen.addActionListener(e -> useMove("Smokescreen"));
	    panel.add(btnSmokescreen);

	    panel.add(new JLabel());

	    JButton btnBack = new JButton("← Back");
	    btnBack.addActionListener(e -> cardLayout.show(buttonPanel, "ACTIONS"));
	    panel.add(btnBack);

	    return panel;
	}

    private void useMove(String moveName) {
        log("Charmander used " + moveName + "!");
        cardLayout.show(buttonPanel, "ACTIONS");
    }

    private void log(String message) {
        txtLog.setText(message);
    }

    private JButton makeButton(String label, Runnable action) {
        JButton btn = new JButton(label);
        btn.addActionListener(e -> action.run());
        return btn;
    }
}
