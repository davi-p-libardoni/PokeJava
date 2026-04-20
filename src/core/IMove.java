package core;

public interface IMove {
	public String getName();
	public String getDescription();
	public Type getType();
	public int getPP();
	public int getPower();
	public int getAccuracy();
	public int getPriority();
	public DamageClass getDamageClass();
	
	public BattleActionReport execute(Battle b,Pokemon user, Pokemon target);
	public boolean canExecute(Battle b,Pokemon user,Pokemon target);
}
