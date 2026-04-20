package core;
import java.util.ArrayList;

public class Pokemon {
	private String name;
	private int id;
	private int level;
	private ArrayList<Move> moves = new ArrayList<>();
	private ArrayList<Type> types = new ArrayList<>();
	private int[] stats = new int[6];
	private int[] statMods = new int[6];
	private int currentHP;
	private int[] specialStatMods = new int[3];
	private StatusCondition scond;
	
	public Pokemon(String name,int level,ArrayList<Type> types,int stats[],ArrayList<Move> moves) {
		this.name = name;
		this.level = level;
		this.types = types;
		this.moves = moves;
		this.stats = stats;
		this.currentHP = stats[0];
		this.scond = StatusCondition.NONE;
		this.specialStatMods = new int[] {0,0,0};
		this.id = -1;
	}
	
	public Pokemon(String name,int level,ArrayList<Type> types,int stats[],ArrayList<Move> moves,int id) {
		this.name = name;
		this.level = level;
		this.types = types;
		this.moves = moves;
		this.stats = stats;
		this.currentHP = stats[0];
		this.scond = StatusCondition.NONE;
		this.specialStatMods = new int[] {0,0,0};
		this.id = id;
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
	
	public int getStat(int i) {
		return this.stats[i];
	}

	public int[] getStats() {
		return this.stats;
	}
	
	public int getEffectiveStat(Stat stat) {
		int upper = 2;
		int lower = 2;
		int modifier = statMods[stat.ordinal()];
		if(modifier < 0) lower += Math.abs(modifier);
		if(modifier > 0) upper += modifier;
		return upper/lower * stats[stat.ordinal()];
	}
	
	public int getCurrentHp() {
		return this.currentHP;
	}

	public Type getType(int i) {
		return this.types.get(i);
	}

	public ArrayList<Type> getTypes() {
		return this.types;
	}
	
	public boolean isDoubleType() {
		return (this.types.size() > 1);
	}
	
	public StatusCondition getStatusCondition() {
		return this.scond;
	}

	public void applyStatusCondition(StatusCondition scond) {
		this.scond = scond;
	}
	
	public boolean applyStatModifier(Stat stat, int degree) {
		boolean result = true;
		int current = this.statMods[stat.ordinal()];
		int updated = current + degree;
		if(updated > 6 || updated < -6) result = false;
		if(updated > 6) updated = 6;
		if(updated < -6) updated = -6;
		this.statMods[stat.ordinal()] = updated;
		return result;
	}
	
	public boolean applySpecialStatModifier(SpecialStat stat,int degree) {
		boolean result = true;
		int current = this.specialStatMods[stat.ordinal()];
		int updated = current + degree;
		int limit = (stat.ordinal() == 2)? 4:6;
		if(updated > limit || updated < -1*limit) result = false;
		if(updated > limit) updated = 6;
		if(updated < -1*limit) updated = 6;
		this.specialStatMods[stat.ordinal()] = updated;
		return result;
	}

	public boolean isFainted() {
		return (this.currentHP == 0);
	}
	
	public boolean hasType(Type type) {
		if(this.types.get(0) == type) return true;
		if(this.types.size() > 1 && this.types.get(1) == type) return true; 
		return false;
	}
	
	public void takeDamage(int amount) {
		int newHP = this.currentHP - amount;
		if(newHP < 0) newHP = 0;
		this.currentHP = newHP;
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public int getCritMod() {
		return this.specialStatMods[SpecialStat.CRIT.ordinal()];
	}
	
	public void buffCrit() {
		this.specialStatMods[SpecialStat.CRIT.ordinal()]++;
	}

	public void setCurrentHp(int split) {
		this.currentHP = split;
		if(this.currentHP > this.getStat(Stat.HP)) this.currentHP = this.getStat(Stat.HP);
		if(this.currentHP < 0) this.currentHP = 0;
	}
}
