import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class BattleUI extends JFrame {

    // Componentes que serão preenchidos por variáveis
    private JLabel lblPlayerName, lblEnemyName;
    private JLabel lblPlayerLevel, lblEnemyLevel;
    private JProgressBar barPlayerHP, barEnemyHP;
    private JTextArea txtLog;
    private String tMessage;
    private JPanel bottomBar;
    private JTextArea actionTxtArea;
    private String actionText;
    private JPanel actionButtons;
    private JTextArea moveDescTxtArea;
    private Font pixelFont = new Font("Monospaced",Font.BOLD,16);
    JButton[] moveBtns = new JButton[] {new JButton(),new JButton(),new JButton(),new JButton()};

    public BattleUI(Battle b) {
        Pokemon p1 = b.getBattler(0);
        Pokemon p2 = b.getBattler(1);
    	
        setTitle("Pokémon Battle Interface");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setLayout(new BorderLayout());

        // Painel Principal de Batalha
        JPanel battlePanel = new JPanel(null);
        battlePanel.setBackground(Color.WHITE);

        // --- STATUS DO INIMIGO (Topo Esquerdo ou Direito) ---
        JPanel enemyStatus = createStatusPanel(p2.getName(), p2.getLevel(), p2.getStat(Stat.HP), p2.getCurrentHp());
        enemyStatus.setBounds(20, 20, 200, 60);
        battlePanel.add(enemyStatus);

        // --- STATUS DO JOGADOR (Baixo Direito) ---
        JPanel playerStatus = createStatusPanel(p1.getName(), p1.getLevel(), p1.getStat(Stat.HP), p1.getCurrentHp());
        playerStatus.setBounds(260, 180, 200, 80);
        battlePanel.add(playerStatus);


        CardLayout cl = new CardLayout();
        bottomBar = new JPanel(cl);
        bottomBar.setVisible(true);
        
        // --- LOG DE MENSAGENS ---
        txtLog = new JTextArea(tMessage);
        txtLog.setRows(4);
        txtLog.setEditable(false);
        txtLog.setLineWrap(true);
        txtLog.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        bottomBar.add(txtLog,"TEXT_LOG");
        
        // --- menu de ações ---
        JPanel actionArea = new JPanel();
        actionArea.setLayout(new BorderLayout());
        
        actionTxtArea = new JTextArea("teste");
        actionTxtArea.setRows(4);
        actionTxtArea.setEditable(false);
        actionTxtArea.setLineWrap(true);
        actionTxtArea.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        actionArea.add(actionTxtArea,BorderLayout.CENTER);
        
        actionButtons = new JPanel();
        actionButtons.setLayout(new GridLayout(2,2,5,5));
        JButton btnFight = new JButton("FIGHT");
        JButton btnPkmn  = new JButton("PKMN");
        JButton btnBag   = new JButton("BAG");
        JButton btnRun   = new JButton("RUN");
        JButton[] actionBtns = new JButton[] {btnFight,btnPkmn,btnBag,btnRun};
        for(int i = 0;i<4;i++) {
        	actionButtons.add(actionBtns[i]);
        	actionBtns[i].setFont(pixelFont);
        }
        ActionListener actionMenuListener = e -> {
        	String command = e.getActionCommand();
        	switch(command) {
        		case "FIGHT":
        			showMoves(p1,cl);
        			break;
        		default:
        			break;
        	}
        };
        btnFight.addActionListener(actionMenuListener);
        btnPkmn.addActionListener(actionMenuListener);
        btnBag.addActionListener(actionMenuListener);
        btnRun.addActionListener(actionMenuListener);
        actionArea.add(actionButtons,BorderLayout.EAST);
        bottomBar.add(actionArea,"ACTION_BAR");
        
        // --- menu de moves ---
        JPanel moveSelectArea = new JPanel();
        moveSelectArea.setLayout(new BorderLayout());
        JPanel moveNamesArea = new JPanel();
        moveNamesArea.setLayout(new GridLayout(2,2,10,10));
        moveSelectArea.add(moveNamesArea,BorderLayout.CENTER);
        for(int j = 0; j < 4 ;j++) {
        	int index = j;
        	moveBtns[j] = new JButton();
        	moveBtns[j].setFont(pixelFont);
        	moveNamesArea.add(moveBtns[j]);
        	moveBtns[j].addActionListener(e->{
        		moveChosen(index);
        	});
        }
        bottomBar.add(moveSelectArea,"MOVE_SELECTION");
        JPanel moveDetailsArea = new JPanel();
        moveDescTxtArea = new JTextArea();
        moveDetailsArea.add(moveDescTxtArea);
        moveSelectArea.add(moveDetailsArea,BorderLayout.EAST);
        
        getContentPane().add(battlePanel, BorderLayout.CENTER);
        getContentPane().add(bottomBar,BorderLayout.SOUTH);
        
        cl.show(bottomBar,"ACTION_BAR");
    }

    private JPanel createStatusPanel(String name, int lvl, int maxHp, int curHp) {
        JPanel panel = new JPanel(new GridLayout(3, 1));
        panel.setBorder(BorderFactory.createTitledBorder(""));
        
        JLabel nameLabel = new JLabel(name + "  Lv." + lvl);
        nameLabel.setFont(new Font("Monospaced", Font.BOLD, 14));
        
        JProgressBar hpBar = new JProgressBar(0, maxHp);
        hpBar.setValue(curHp);
        hpBar.setForeground(getHPColor(curHp, maxHp));
        hpBar.setStringPainted(true);
        hpBar.setString(curHp + "/" + maxHp);

        panel.add(nameLabel);
        panel.add(new JLabel("HP:"));
        panel.add(hpBar);
        
        return panel;
    }

    private Color getHPColor(int current, int max) {
        double percentage = (double) current / max;
        if (percentage > 0.5) return Color.GREEN;
        if (percentage > 0.2) return Color.YELLOW;
        return Color.RED;
    }
    
    private void showMoves(Pokemon p,CardLayout cl) {
    	cl.show(bottomBar, "MOVE_SELECTION");
    	int qtd = p.moveAmount();
    	for(int i = 0;i < qtd;i++) {
    		moveBtns[i].setText(p.getMove(i).getName());
    		moveBtns[i].setVisible(true);
    	}
    	if(qtd<4) {
    		for(int i = 3;i>=qtd;i--) {
    			moveBtns[i].setVisible(false);
    		}
    	}
    }
    
    private void moveChosen(int index) {
    	System.out.println(index);
    }

    public static void main(String[] args) {
        // Exemplo de preenchimento por variáveis
        SwingUtilities.invokeLater(() -> {
            new BattleUI(
            	new Battle(
            			new Pokemon("Pikachu",15,new int[] {30,10,22,40,14,38},new ArrayList<> (List.of(MoveDex.getMove("Tackle"),MoveDex.getMove("Growl")))),
            			new Pokemon("Eevee",14,new int[] {40,25,22,18,23,30},new ArrayList<> (List.of(MoveDex.getMove("Tackle"),MoveDex.getMove("Growl"))))
            	)
            ).setVisible(true);
        });
    }
}