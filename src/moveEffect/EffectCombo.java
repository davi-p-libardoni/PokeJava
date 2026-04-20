package moveEffect;

import core.Attempt;
import core.Battle;
import core.BattleActionReport;
import core.Move;
import core.Pokemon;

public class EffectCombo extends EffectNone {
	private int hits;
	private MoveEffect secondaryEffect = null;
	
	public EffectCombo(int degree) {
		this.hits = degree;
	}
	
	public EffectCombo(int degree,MoveEffect secondary) {
		this.hits = degree;
		this.secondaryEffect = secondary;
	}
	
	@Override
	public BattleActionReport execute(Battle b, Move m, Pokemon user, Pokemon target) {
		BattleActionReport report = new BattleActionReport();
		report.moveName = m.getName();
		report.user = user;
		report.target = target;
		report.result = Attempt.check(b,m,user,target);
		if(report.result.successful()) {
			int index;
			for(index = 0;index<this.hits;index++) {
				if(target.isFainted()) {
					break;
				}
				report.damageDealt = super.calculateDamage(b, m, user, target,report);
				target.takeDamage(report.damageDealt);
			}
			report.effectMessage = "It hit "+index+" times.";
		}else {
			report.damageDealt = 0;
		}
		
		if(this.secondaryEffect != null && report.result.successful()) {
			((EffectNone)secondaryEffect).applyPostEffect(report, b, m, user, target);
		}
		return report;
	}

}
