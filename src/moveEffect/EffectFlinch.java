package moveEffect;

import battle.Battle;
import battle.BattleActionReport;
import core.Pokemon;
import move.Move;

public class EffectFlinch extends EffectNone {
	protected double chance;
	
	public EffectFlinch(double chance) {
		this.chance = chance;
	}
	
	@Override
	public void applyPostEffect(BattleActionReport report,Battle b,Move m,Pokemon user,Pokemon target) {
		if(report.result.successful() == false) return;
		
		if(fill here) {			
			target.flinch();
			report.message = target.getName() + " flinched.";
		}
	}
}
