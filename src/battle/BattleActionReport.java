package battle;

import core.Pokemon;
import move.MoveResult;

public class BattleActionReport {
	public ActionType type = ActionType.MOVE;
	public String switchTo;
	public String moveName;
	public Pokemon user;
	public Pokemon target;
	public int userIndex;
	public int targetIndex;
	public int damageDealt = 0;
    public boolean isCritical = false;
    public MoveResult result;
	public String message;
	public ActionCapacityReport action;
    
	public BattleActionReport(String moveName,Pokemon user,Pokemon target,MoveResult result,String effMessage,ActionCapacityReport action) {
		this.moveName = moveName;
		this.user = user;
		this.target = target;
		this.result = result;
		this.message = effMessage;
		this.action = action;
	}
	
	public BattleActionReport(String moveName,Pokemon user,Pokemon target,MoveResult result,String effMessage) {
		this.moveName = moveName;
		this.user = user;
		this.target = target;
		this.result = result;
		this.message = effMessage;
	}
	
    public BattleActionReport(String moveName,Pokemon user,Pokemon target,int damageDealt,boolean isCritical,MoveResult result) {
    	this.moveName = moveName;
    	this.user = user;
    	this.target = target;
    	this.damageDealt = damageDealt;
    	this.isCritical = isCritical;
    	this.result = result;
    }
    
    public BattleActionReport(String moveName,Pokemon user,Pokemon target,int damageDealt,boolean isCritical,MoveResult result,String effMessage) {
    	this.moveName = moveName;
    	this.user = user;
    	this.target = target;
    	this.damageDealt = damageDealt;
    	this.isCritical = isCritical;
    	this.result = result;
    	this.message = effMessage;
    }

    public BattleActionReport() {
    	
    }
}
