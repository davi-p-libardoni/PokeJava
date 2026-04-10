
public class Pokemon {
	private String name;
	private int level;
	private Move[] moves = new Move[4];
	
	public Pokemon(String name,int level) {
		this.name = name;
		this.level = level;
		
	}
	
	public Move getMove(int index) {
		return this.moves[index];
	}
	
	//public Move selectMove() {
		
	//}
}
