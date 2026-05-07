package battle;

import core.Pokemon;

public class ActionCapacityReport {
	public ActResult result;
	private Pokemon mon;
	
	public ActionCapacityReport(Pokemon mon) {
		this.mon = mon;
	}
	
	public String getMessage() {
		if(this.result == ActResult.SLEEP) {
			return mon.getName() + " is fast asleep.";
		} else if(this.result == ActResult.PARALYZED) {
			return mon.getName() + " was fully paralyzed.";
		} else if(this.result == ActResult.FLINCHED) {
			return mon.getName() + " flinched!";
		}else if(this.result == ActResult.SLEEP_AWOKE) {
			return mon.getName() + " woke up!";
		}
		return "";
	}
	
	public boolean successful() {
		return (this.result == ActResult.SUCCESS || this.result == ActResult.SLEEP_AWOKE);
	}
}
