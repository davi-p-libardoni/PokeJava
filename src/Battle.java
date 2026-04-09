
public class Battle {
	private int turn;
	private Pokemon battler1;
	private Pokemon battler2;
	private Weather weather;
	
	public Battle(Pokemon battler1,Pokemon battler2) {
		this.turn = 1;
		this.battler1 = battler1;
		this.battler1 = battler2;
	}
	
	public boolean runTurn() {
		Move m1 = battler1.selectMove();
		Move m2 = battler2.selectMove();
	}
}
