
public abstract class MoveWithPrecondition implements IMove {
	protected IMove wrappedMove;
	
	public MoveWithPrecondition(Move move) {
		this.wrappedMove = move;
	}
	
	public String getName() { return this.wrappedMove.getName(); }
	public Type getType() { return this.wrappedMove.getType(); }
	public int getPP() { return this.wrappedMove.getPP(); }
	public int getPower() { return this.wrappedMove.getPower(); }
	public int getAccuracy() { return this.wrappedMove.getAccuracy(); }
	public int getPriority() { return this.wrappedMove.getPriority(); }
	public DamageClass getDamageClass() { return this.wrappedMove.getDamageClass();}
	
	public void execute(Battle b,Pokemon user,Pokemon target) { this.wrappedMove.execute(b,user,target); }
	
	public abstract boolean canExecute(Battle b,Pokemon user,Pokemon target);
}
