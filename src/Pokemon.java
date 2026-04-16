import java.util.ArrayList;

public class Pokemon {
	private String name;
	private int level;
	private ArrayList<Move> moves = new ArrayList<>();
	private int[] stats = new int[6];
	private int currentHP;
	
	public Pokemon(String name,int level,int stats[],ArrayList<Move> moves) {
		this.name = name;
		this.level = level;
		this.moves = moves;
		this.stats = stats;
		this.currentHP = stats[0];
	}
	
	public String getName() {
		return this.name;
	}
	
	public int getLevel() {
		return this.level;
	}
	
	public int moveAmount() {
		return this.moves.size();
	}
	
	public Move getMove(int index) {
		return this.moves.get(index);
	}
	
	public int getStat(Stat stat) {
		return this.stats[stat.ordinal()];
	}
	
	public int getCurrentHp() {
		return this.currentHP;
	}
	
	//public Move selectMove() {
		
	//}
}
