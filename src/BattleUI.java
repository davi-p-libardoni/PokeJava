import javax.swing.*;
import javax.swing.border.EmptyBorder;

import core.Battle;
import core.BattleActionReport;
import core.Move;
import core.MoveDex;
import core.MoveResult;
import core.PokeDex;
import core.Pokemon;
import core.Stat;
import core.StatusCondition;

import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class BattleUI extends JFrame {

    private static final long serialVersionUID = 5957516073717325365L;
	private JProgressBar barP1HP, barP2HP;
    private JProgressBar[] bars;
    private JLabel playerSprite,enemySprite;
    @SuppressWarnings("unused")
	private JLabel[] sprites;
    private JLabel playerStatusLabel,enemyStatusLabel;
    private JLabel[] statusLabels;
    private JTextArea txtLog;
    private String tMessage;
    private JPanel bottomBar;
    private JTextArea actionTxtArea;
    private JPanel actionButtons;
    private JTextArea moveDescTxtArea;
    private Font pixelFont = new Font("Monospaced",Font.BOLD,16);
    JButton[] moveBtns = new JButton[] {new JButton(),new JButton(),new JButton(),new JButton()};

    public BattleUI(Battle b) {
        Pokemon p1 = b.getBattler(0);
        Pokemon p2 = b.getBattler(1);
    	
        setTitle("Pokémon Battle Interface");
        setSize(550, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setLayout(new BorderLayout());

        // Painel Principal de Batalha
        JPanel battlePanel = new JPanel(null);
        battlePanel.setBackground(Color.WHITE);

        // --- STATUS DO INIMIGO (Topo Esquerdo ou Direito) ---
        barP2HP = new JProgressBar(0, p2.getStat(Stat.HP));
        enemyStatusLabel = new JLabel();
        JPanel enemyStatus = createStatusPanel(p2,barP2HP,enemyStatusLabel);
        enemyStatus.setBounds(20, 20, 260, 60);
        battlePanel.add(enemyStatus);
        
        enemySprite = new JLabel(getSprite(p2, false));
        enemySprite.setBounds(350, 20, 200, 150); // Posição do inimigo
        battlePanel.add(enemySprite);

        // --- STATUS DO JOGADOR (Baixo Direito) ---
        barP1HP = new JProgressBar(0, p1.getStat(Stat.HP));
        playerStatusLabel = new JLabel();
        JPanel playerStatus = createStatusPanel(p1,barP1HP,playerStatusLabel);
        playerStatus.setBounds(250, 200, 260, 80);
        battlePanel.add(playerStatus);
        
        playerSprite = new JLabel(getSprite(p1, true));
        playerSprite.setBounds(50, 150, 150, 150);
        battlePanel.add(playerSprite);
        
        bars = new JProgressBar[] {barP1HP,barP2HP};
        sprites = new JLabel[] {playerSprite,enemySprite};
        statusLabels = new JLabel[] {playerStatusLabel,enemyStatusLabel};

        CardLayout cl = new CardLayout();
        bottomBar = new JPanel(cl);
        bottomBar.setVisible(true);
        
        // --- LOG DE MENSAGENS ---
        JPanel txtLogPanel = new JPanel(new BorderLayout());
        txtLogPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        
        txtLog = new JTextArea(tMessage);
        txtLog.setRows(4);
        txtLog.setEditable(false);
        txtLog.setLineWrap(true);
        txtLog.setBorder(new EmptyBorder(10, 10, 10, 10));
        txtLog.setFont(pixelFont);
        txtLogPanel.add(txtLog,BorderLayout.CENTER);
        bottomBar.add(txtLogPanel,"TEXT_LOG");
        
        // --- menu de ações ---
        JPanel actionArea = new JPanel();
        actionArea.setLayout(new BorderLayout());
        actionArea.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        
        actionTxtArea = new JTextArea("What will " + p1.getName() + " do?");
        actionTxtArea.setRows(4);
        actionTxtArea.setEditable(false);
        actionTxtArea.setLineWrap(true);
        actionTxtArea.setBorder(new EmptyBorder(10, 10, 10, 10));
        actionTxtArea.setFont(pixelFont);
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
        		BattleActionReport[] reports = b.runTurn(p1.getMove(index));
        		cl.show(bottomBar, "TEXT_LOG");
        		animateTurn(b,reports,cl,0);
        	});
        	moveBtns[j].addMouseListener(new java.awt.event.MouseAdapter(){
        		public void mouseEntered(java.awt.event.MouseEvent evt) {
        			Move m = p1.getMove(index);
        			String power = Integer.toString(m.getPower());
        			if(m.getPower() == -1) power = "--";
        			String accuracy = Integer.toString(m.getAccuracy());
        			if(m.getAccuracy() == -1) accuracy = "--";
        			moveDescTxtArea.setText("Power:"+power+"\n");
        			moveDescTxtArea.append("Accuracy:"+accuracy+"\n");
        			moveDescTxtArea.append(m.getPP()+"/"+m.getPP()+"\n");
        			moveDescTxtArea.append(m.getType().name()+"\n");
        		}
        		public void mouseExited(java.awt.event.MouseEvent evt) {
        			moveDescTxtArea.setText("");
        		}
        	});
        }
        bottomBar.add(moveSelectArea,"MOVE_SELECTION");
        JPanel moveDetailsArea = new JPanel();
        moveDetailsArea.setPreferredSize(new Dimension(120, 0)); 
        moveDetailsArea.setLayout(new BorderLayout());
        moveDetailsArea.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        moveDescTxtArea = new JTextArea();
        moveDescTxtArea.setText("Power:\nType:\n");
        moveDetailsArea.add(moveDescTxtArea);
        moveSelectArea.add(moveDetailsArea,BorderLayout.EAST);
        
        getContentPane().add(battlePanel, BorderLayout.CENTER);
        getContentPane().add(bottomBar,BorderLayout.SOUTH);
        
        cl.show(bottomBar,"ACTION_BAR");
    }

    private ImageIcon getSprite(Pokemon p, boolean isPlayer) {
    	String folder = (isPlayer)?"back/":"front/";
		String path = "assets/sprites/" + folder + PokeDex.getId(p) + ".png";
    	ImageIcon icon = new ImageIcon(path);
    	Image img = icon.getImage();
        Image newImg = img.getScaledInstance(180, 200, Image.SCALE_SMOOTH);
        return new ImageIcon(newImg);
	}

	private void animateTurn(Battle b,BattleActionReport[] reports,CardLayout cl,int currentIndex) {
		final int index = currentIndex;
		BattleActionReport info = reports[index];
		Pokemon battler = info.user;
		Pokemon target = info.target;
		txtLog.setText(battler.getName() + " used " + info.moveName);
			
		Timer timer = new Timer(1200,e->{
			updateStatusBar(info.targetIndex,info.target);
			
			if(info.result == MoveResult.SUPER) txtLog.append("\nIt was super effective!");
			if(info.result == MoveResult.NOT_VERY) txtLog.append("\nIt was not very effective.");
			if(info.result == MoveResult.MISSED) txtLog.append("\nBut it missed.");
			if(info.result == MoveResult.FAILED) txtLog.append("\nBut it failed.");
			if(info.effectMessage != null) txtLog.append("\n"+info.effectMessage);
			if(info.isCritical) txtLog.append("\nIt was a critical hit!");
			
			Timer nextStepTimer = new Timer(1200,next->{
				if(target.isFainted()) {
					txtLog.setText(target.getName() + " fainted!");
				} else if(currentIndex == 0){
					animateTurn(b,reports,cl,1);
				} else {
					cl.show(bottomBar,"ACTION_BAR");
				}
			});
		    nextStepTimer.setRepeats(false);
		    nextStepTimer.start();
		});
		timer.setRepeats(false);
	    timer.start();
	}
    
    private void updateStatusBar(int targetIndex,Pokemon target) {
    	bars[targetIndex].setValue(target.getCurrentHp());
    	bars[targetIndex].setString(target.getCurrentHp() + "/" + target.getStat(Stat.HP));
    	if(target.getStatusCondition() != StatusCondition.NONE) {
    		statusLabels[targetIndex].setText("["+target.getStatusCondition().toLabel()+"]");
    	}else {
    		statusLabels[targetIndex].setText("");
    	}
    }

	private JPanel createStatusPanel(Pokemon p,JProgressBar hpBar,JLabel statusLabel) {
		String name = p.getName();
		int lvl = p.getLevel();
		int maxHP = p.getStat(Stat.HP);
		int curHP = p.getCurrentHp();
		
        JPanel panel = new JPanel(new GridLayout(3, 1));
        panel.setBorder(BorderFactory.createTitledBorder(""));
        
        JPanel topPanel = new JPanel(new BorderLayout());
        JLabel nameLabel = new JLabel(name + "  Lv." + lvl);
        nameLabel.setFont(new Font("Monospaced", Font.BOLD, 14));
        
        nameLabel.setFont(new Font("Monospaced", Font.BOLD, 16));
        if(p.getStatusCondition() != StatusCondition.NONE) {
        	nameLabel.setText("["+p.getStatusCondition().toLabel()+"]");
        }
        topPanel.add(nameLabel,BorderLayout.CENTER);
        topPanel.add(statusLabel,BorderLayout.EAST);
        
        hpBar.setValue(curHP);
        hpBar.setForeground(getHPColor(curHP, maxHP));
        hpBar.setStringPainted(true);
        hpBar.setString(curHP + "/" + maxHP);

        panel.add(topPanel);
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

    public void openUI(Battle b) {
        SwingUtilities.invokeLater(() -> {
            BattleUI ui = new BattleUI(b);
            ui.setVisible(true);
        });
    }
    
    public static void main(String[] args) {
        // Exemplo de preenchimento por variáveis
        SwingUtilities.invokeLater(() -> {
            new BattleUI(
            	new Battle(
            			PokeDex.getPokemon("Zapdos", 15, new ArrayList<> (List.of(MoveDex.getMove("Thunder Shock"),MoveDex.getMove("Triple Kick")))),
            			PokeDex.getPokemon("Regigigas", 14, new ArrayList<> (List.of(MoveDex.getMove("Double Kick"),MoveDex.getMove("Growl"))))
            	)
            ).setVisible(true);
        });
    }
}