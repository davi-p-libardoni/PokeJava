
public class Move implements IMove {
	private int id;
	private String name;
	
	private Type type;
	private int pp;
	private int power;
	private int accuracy;
	private int priority;
	private DamageClass dmgClass;
	private MoveEffect effect;
	
	public Move(String name, Type type, int pp, int power,int accuracy, int priority, DamageClass dmgClass, MoveEffect effect) {
		this.name = name;
		this.type = type;
		this.pp = pp;
		this.power = power;
		this.accuracy = accuracy;
		this.priority = priority;
		this.dmgClass = dmgClass;
		this.effect = effect;
	}
	
	public String getName() {
		return this.name;
	}
	
	public Type getType() {
		return this.type;
	}
	
	public int getPP() {
		return this.pp;
	}
	
	public int getPower() {
		return this.power;
	}
	
	public int getAccuracy() {
		return this.accuracy;
	}
	
	public int getPriority() {
		return this.priority;
	}
	
	public DamageClass getDamageClass() {
		return this.dmgClass;
	}
	
	public void execute(Battle b,Pokemon user, Pokemon target) {
		this.effect.execute(b,user, target);
	}
	
	public boolean canExecute(Battle b,Pokemon user, Pokemon target) {
		return true;
	}
}
