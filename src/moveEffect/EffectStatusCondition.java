package moveEffect;

import java.util.concurrent.ThreadLocalRandom;

import core.Battle;
import core.BattleActionReport;
import core.Move;
import core.Pokemon;
import core.StatusCondition;

public class EffectStatusCondition extends EffectNone {
	protected StatusCondition scond;
	protected double chance;
	protected boolean onSelf;
	
	public EffectStatusCondition(int choice, double chance,boolean onSelf) {
		this.scond = StatusCondition.values()[choice];
		this.chance = chance;
		this.onSelf = onSelf;
	}
	
	@Override
	protected void applyPostEffect(BattleActionReport report, Battle b, Move m, Pokemon user, Pokemon target) {
		Pokemon trgt = (this.onSelf)?user:target;
		int threshold = (int)chance * 100;
		if(!trgt.isFainted() && (chance == 1 || ThreadLocalRandom.current().nextInt() <= threshold)) {
			if(report.effectMessage==null) report.effectMessage = "";
			if(report.effectMessage!="") report.effectMessage += "\n";
			if(trgt.getStatusCondition() == StatusCondition.NONE) {
				String msg = trgt.getName() + " has ";
				switch(scond) {
					case StatusCondition.PARALYZE:
						msg += "been paralyzed.";
						break;
					case StatusCondition.BURN:
						msg += "been burned.";
						break;
					case StatusCondition.POISON:
						msg += "been poisoned.";
						break;
					case StatusCondition.SLEEP:
						msg += "fallen asleep.";
						break;
					case StatusCondition.FREEZE:
						msg += "been frozen.";
						break;
					default:
						break;
				}
				report.effectMessage += msg;
				trgt.applyStatusCondition(scond);
			}
		}
	}
}
