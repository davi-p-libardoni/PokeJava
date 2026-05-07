import javax.swing.*;
import javax.swing.border.EmptyBorder;

import battle.ActResult;
import battle.ActionType;
import battle.Battle;
import battle.BattleActionReport;
import battle.TrainerAI;
import core.MoveDex;
import core.PokeDex;
import core.Pokemon;
import core.Stat;
import core.StatusCondition;
import move.Move;
import move.MoveResult;

import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class BattleUI extends JFrame {

    private static final long serialVersionUID = 5957516073717325365L;
    private Battle b;
    private CardLayout cl;
	private JProgressBar barP1HP, barP2HP;
    private JProgressBar[] bars;
    private JLabel playerSprite,enemySprite;
    @SuppressWarnings("unused")
	private JLabel[] sprites;
    private JLabel playerStatusLabel,enemyStatusLabel;
    private JLabel[] statusLabels;
    private JLabel playerNameLabel,enemyNameLabel;
    private JLabel[] nameLabels;
    private JTextArea txtLog;
    private String tMessage;
    private JPanel bottomBar;
    private JTextArea actionTxtArea;
    private JPanel actionButtons;
    private JTextArea moveDescTxtArea;
    private Font pixelFont = new Font("Monospaced",Font.BOLD,16);
    JButton[] moveBtns = new JButton[] {new JButton(),new JButton(),new JButton(),new JButton()};

    public BattleUI(Battle b) {
    	this.b = b;
        Pokemon p1 = b.getBattler(0);
        Pokemon p2 = b.getBattler(1);
    	
        setTitle("Pokémon Battle Interface");
        setSize(550, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        getContentPane().setLayout(new BorderLayout());

        // Painel Principal de Batalha
        JPanel battlePanel = new JPanel(null);
        battlePanel.setBackground(Color.WHITE);

        // --- STATUS DO INIMIGO (Topo Esquerdo ou Direito) ---
        barP2HP = new JProgressBar(0, p2.getStat(Stat.HP));
        enemyNameLabel = new JLabel();
        enemyStatusLabel = new JLabel();
        JPanel enemyStatus = createStatusPanel(p2,enemyNameLabel,barP2HP,enemyStatusLabel);
        enemyStatus.setBounds(20, 20, 260, 60);
        battlePanel.add(enemyStatus);
        
        enemySprite = new JLabel(getSprite(p2, false));
        enemySprite.setBounds(350, 20, 200, 150); // Posição do inimigo
        battlePanel.add(enemySprite);

        // --- STATUS DO JOGADOR (Baixo Direito) ---
        barP1HP = new JProgressBar(0, p1.getStat(Stat.HP));
        playerNameLabel = new JLabel();
        playerStatusLabel = new JLabel();
        JPanel playerStatus = createStatusPanel(p1,playerNameLabel,barP1HP,playerStatusLabel);
        playerStatus.setBounds(250, 200, 260, 80);
        battlePanel.add(playerStatus);
        
        playerSprite = new JLabel(getSprite(p1, true));
        playerSprite.setBounds(50, 150, 150, 150);
        battlePanel.add(playerSprite);
        
        bars = new JProgressBar[] {barP1HP,barP2HP};
        sprites = new JLabel[] {playerSprite,enemySprite};
        nameLabels = new JLabel[] {playerNameLabel,enemyNameLabel};
        statusLabels = new JLabel[] {playerStatusLabel,enemyStatusLabel};

        cl = new CardLayout();
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
        	        updateMoveButtons();
        			showMoves();
        			break;
        		case "PKMN":
        			selectNextMon(false);
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
        	moveBtns[j] = new JButton();
        	moveBtns[j].setFont(pixelFont);
        	moveNamesArea.add(moveBtns[j]);
        }
        
        JPanel moveDetailsArea = new JPanel();
        moveDetailsArea.setPreferredSize(new Dimension(160, 0)); 
        moveDetailsArea.setLayout(new BorderLayout());
        moveDetailsArea.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        moveDescTxtArea = new JTextArea();
        moveDescTxtArea.setBorder(new EmptyBorder(4,4,4,4));
        moveDescTxtArea.setFont(pixelFont);
        moveDetailsArea.add(moveDescTxtArea);
        moveSelectArea.add(moveDetailsArea,BorderLayout.EAST);
        
        bottomBar.add(moveSelectArea,"MOVE_SELECTION");
        
        getContentPane().add(battlePanel, BorderLayout.CENTER);
        getContentPane().add(bottomBar,BorderLayout.SOUTH);
        
        cl.show(bottomBar,"ACTION_BAR");
        
        drawTurn(b,cl);
    }
    
    private void updateMoveButtons() {
    	Pokemon p = b.getBattler(0);
    	for(int j = 0; j < 4; j++) {
    		final int index = j;
			for (ActionListener al : moveBtns[j].getActionListeners()) {
        	    moveBtns[j].removeActionListener(al);
        	}
			for (java.awt.event.MouseListener ml : moveBtns[j].getMouseListeners()) {
	            if (ml.getClass().getName().contains("BattleUI")) {
	                moveBtns[j].removeMouseListener(ml);
	            }
	        }
			
        	if(j < p.moveAmount()) {
				Move m = p.getMove(index);
           	 	moveBtns[j].setText(m.getName());
    			moveBtns[j].addActionListener(e->{
        			BattleActionReport[] reports = b.runTurn(m);
        			cl.show(bottomBar, "TEXT_LOG");
        			animateTurn(reports,0);
        		});
        		moveBtns[j].addMouseListener(new java.awt.event.MouseAdapter(){
        			public void mouseEntered(java.awt.event.MouseEvent evt) {
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
        		moveBtns[j].setEnabled(true);
            }else {
            	moveBtns[j].setEnabled(false);
            }
    	}
	}

    private void drawSprites(Battle b) {
        playerSprite.setIcon(getSprite(b.getBattler(0),true));
        enemySprite.setIcon(getSprite(b.getBattler(1),false));
    }
    
	private void drawTurn(Battle b,CardLayout cl) {
        Pokemon p1 = b.getBattler(0);
        Pokemon p2 = b.getBattler(1);
        
    	// --- STATUS DO INIMIGO
        barP2HP.setMaximum(p2.getStat(Stat.HP));
        updateStatusBar(1);
        
        // --- STATUS DO JOGADOR
        barP1HP.setMaximum(p1.getStat(Stat.HP));
        updateStatusBar(0);
        
        // --- sprites
        drawSprites(b);
        
        // --- menu de ações ---
        actionTxtArea.setText("What will " + p1.getName() + " do?");
        
        cl.show(bottomBar,"ACTION_BAR");
    }

    private ImageIcon getSprite(Pokemon p, boolean isPlayer) {
    	String folder = (isPlayer)?"back/":"front/";
    	if(p.isShiny()) folder += "shiny/";
		String path = "assets/sprites/" + folder + PokeDex.getId(p) + ".png";
    	ImageIcon icon = new ImageIcon(path);
    	Image img = icon.getImage();
        Image newImg = img.getScaledInstance(180, 200, Image.SCALE_SMOOTH);
        return new ImageIcon(newImg);
	}

	private void animateTurn(BattleActionReport[] reports,int currentIndex) {
		final int index = currentIndex;
		BattleActionReport info = reports[index];
		if (reports[index].type == ActionType.SWITCH) {
		    updateStatusBar(0);
		    txtLog.setText("You sent out " + b.getBattler(0).getName() + "!");

		    Timer switchMsg = new Timer(1200, sw -> {
		        if (currentIndex == 0 && reports.length > 1) {
		            animateTurn(reports, 1);
		        } else {
		            drawTurn(b, cl);
		            cl.show(bottomBar, "ACTION_BAR");
		        }
		    });
		    
		    switchMsg.setRepeats(false);
		    switchMsg.start();
		    
		    return; 
		}
		
		if(reports[index].type == ActionType.MOVE) {
			Pokemon battler = info.user;
			Pokemon target = b.getBattler(info.targetIndex);
			txtLog.setText("");
			System.out.println(reports[index].action+"\n"+reports[index].result+"\n\n");
			if(reports[index].action != null && reports[index].action.result == ActResult.SLEEP_AWOKE) {
				txtLog.append(battler.getName()+" awoke!\n");
				updateStatusBar(1 - info.targetIndex);
			}
			if(reports[index].result != MoveResult.COULDNT_MOVE) {
				txtLog.append(battler.getName() + " used " + info.moveName);				
			}else {
				txtLog.append(reports[index].action.getMessage());
			}
				
			Timer timer = new Timer(1200,e->{
				updateStatusBar(info.targetIndex);
				
				if(info.result == MoveResult.SUPER) txtLog.append("\nIt was super effective!");
				if(info.result == MoveResult.NOT_VERY) txtLog.append("\nIt was not very effective.");
				if(info.result == MoveResult.MISSED) txtLog.append("\nBut it missed.");
				if(info.result == MoveResult.FAILED) txtLog.append("\nBut it failed.");
				if(info.message != null) txtLog.append("\n"+info.message);
				if(info.isCritical) txtLog.append("\nIt was a critical hit!");
				
				Timer nextStepTimer = new Timer(1200,next->{
					if(target.isFainted()) {
						txtLog.setText(target.getName() + " fainted!");
						Timer littleOne = new Timer(500,little->{
							if(b.battlerCount(info.targetIndex,true) == 0) {
								String trainerName = (info.targetIndex == 0)?"Player":"Opponent";
								txtLog.append("\n"+trainerName+" has run out of Pokémon!");
							} else {
								if(info.targetIndex == 0) {
									selectNextMon(true);
								} else {
									b.setBattler(1,TrainerAI.selectNextMon(b));
									txtLog.append("\nThe opponent sent out "+b.getBattler(1).getName()+"!");
								}
								drawTurn(b,cl);
								cl.show(bottomBar, "ACTION_BAR");
							}
						});
						littleOne.setRepeats(false);
						littleOne.start();
					} else if(currentIndex == 0){
						animateTurn(reports,1);
					} else {
						drawTurn(b,cl);
						cl.show(bottomBar,"ACTION_BAR");
					}
				});
			    nextStepTimer.setRepeats(false);
			    nextStepTimer.start();
			});
			timer.setRepeats(false);
		    timer.start();
		}
	}
    
	private void selectNextMon(boolean previousDied) {
	    JDialog selectionDialog = new JDialog(this, "Select a Pokémon", true);
	    selectionDialog.setLayout(new BorderLayout());
	    selectionDialog.setSize(300, 400);
	    selectionDialog.setLocationRelativeTo(this);
	    final boolean[] turnConsumed = new boolean[] {false};

	    JPanel listPanel = new JPanel(new GridLayout(3, 2, 5, 5));
	    
	    int i;
	    for (i = 0; i < b.battlerCount(0,false); i++) {
	        Pokemon p = b.getBattler(0,i);
	        JButton pBtn = new JButton(p.getName() + " (HP: " + p.getCurrentHp() + ")");
	        int pkmnIndex = i;
	        if(previousDied || p == b.getBattler(0)) {
	        	pBtn.addActionListener(e -> {
		            b.setBattler(0,pkmnIndex);
		            selectionDialog.dispose();
		        });
	        } else {
	        	pBtn.addActionListener(e -> {
	        		turnConsumed[0] = true;
	        		cl.show(bottomBar, "TEXT_LOG");
		            BattleActionReport[] reports = b.runTurnSwitch(pkmnIndex);
		            animateTurn(reports,0);
		            selectionDialog.dispose();
		        });
	        }
	        pBtn.setEnabled(!p.isFainted());
	        
	        listPanel.add(pBtn);
	    }
	    for(int j = i;j<6;j++) {
	    	JButton emptyBtn = new JButton("--");
	    	setButtonEnabled(emptyBtn,false);
	    	listPanel.add(emptyBtn);
	    }

	    selectionDialog.add(new JLabel("Choose your pokemon:"), BorderLayout.NORTH);
	    selectionDialog.add(new JScrollPane(listPanel), BorderLayout.CENTER);
	    
	    // O código vai "parar" aqui até o selectionDialog.dispose() ser chamado
	    selectionDialog.setVisible(true); 
	    
	    // Só redesenha imediatamente quando a troca não consumiu turno.
	    if(!turnConsumed[0]) {
	    	drawTurn(b, cl);
	    }else {
	    	drawSprites(b);
	    }
	}

	private void updateStatusBar(int targetIndex) {
		Pokemon target = b.getBattler(targetIndex);
		nameLabels[targetIndex].setText(target.getName()+ "  Lv." + target.getLevel());
    	bars[targetIndex].setValue(target.getCurrentHp());
    	bars[targetIndex].setString(target.getCurrentHp() + "/" + target.getStat(Stat.HP));
    	if(target.getStatusCondition() != StatusCondition.NONE) {
    		statusLabels[targetIndex].setText("["+target.getStatusCondition().toLabel()+"]");
    	}else {
    		statusLabels[targetIndex].setText("");
    	}
    }

	private JPanel createStatusPanel(Pokemon p,JLabel nameLabel,JProgressBar hpBar,JLabel statusLabel) {
		String name = p.getName();
		int lvl = p.getLevel();
		int maxHP = p.getStat(Stat.HP);
		int curHP = p.getCurrentHp();
		
        JPanel panel = new JPanel(new GridLayout(3, 1));
        panel.setBorder(BorderFactory.createTitledBorder(""));
        
        JPanel topPanel = new JPanel(new BorderLayout());
        nameLabel.setText(name + "  Lv." + lvl);
        nameLabel.setFont(new Font("Monospaced", Font.BOLD, 14));
        
        statusLabel.setFont(new Font("Monospaced", Font.BOLD, 16));
        if(p.getStatusCondition() != StatusCondition.NONE) {
        	statusLabel.setText("["+p.getStatusCondition().toLabel()+"]");
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
	
	public void setButtonEnabled(JButton btn, boolean enabled) {
	    btn.setEnabled(enabled);
	    if (enabled) {
	        btn.setBackground(UIManager.getColor("Button.background"));
	        btn.setForeground(Color.BLACK);
	        btn.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
	    } else {
	        btn.setBackground(new Color(100, 100, 100));
	        btn.setForeground(new Color(150, 150, 150));
	        btn.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 2));
	    }
	}

    private Color getHPColor(int current, int max) {
        double percentage = (double) current / max;
        if (percentage > 0.5) return Color.GREEN;
        if (percentage > 0.2) return Color.YELLOW;
        return Color.RED;
    }
    
    private void showMoves() {
    	cl.show(bottomBar, "MOVE_SELECTION");
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
            			new ArrayList<> (List.of(
            					PokeDex.getPokemon("Zapdos", 15, new ArrayList<> (List.of(MoveDex.getMove("Thunder Shock"),MoveDex.getMove("Growl"),MoveDex.getMove("Thunder Wave")))),
            					PokeDex.getPokemon("Articuno",16, new ArrayList<> (List.of(MoveDex.getMove("Icy Wind"),MoveDex.getMove("Surf"))))
            			)),
            			new ArrayList<> (List.of(
            					PokeDex.getPokemon("Regigigas", 14, new ArrayList<> (List.of(MoveDex.getMove("Hypnosis"))),true),
            					PokeDex.getPokemon("Regirock", 17, new ArrayList<> (List.of(MoveDex.getMove("Rock Throw")))),
            					PokeDex.getPokemon("Regice", 16, new ArrayList<> (List.of(MoveDex.getMove("Blizzard"))))
            			))
            	)
            ).setVisible(true);
        });
    }
}
