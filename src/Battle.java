
public class Battle {
	private int turn;
	private Pokemon[] battlers = new Pokemon[2];
	private Weather weather;
	
	public Battle(Pokemon battler1,Pokemon battler2) {
		this.turn = 1;
		this.battlers[0] = battler1;
		this.battlers[1] = battler2;
	}
	
	public Pokemon getBattler(int index) {
		return this.battlers[index];
	}
	
	//public boolean runTurn() {
		//Move m1 = battler1.selectMove();
		//Move m2 = battler2.selectMove();
	//}
}
