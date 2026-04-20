package moveEffect;

import core.Attempt;
import core.Battle;
import core.BattleActionReport;
import core.Move;
import core.MoveResult;
import core.Pokemon;

public class EffectPainSplit extends EffectNone {

	@Override
	public BattleActionReport execute(Battle b, Move m, Pokemon user, Pokemon target) {
		BattleActionReport report = new BattleActionReport();
		report.result = Attempt.check(b,m,user,target);
		if(report.result == MoveResult.SUCCESS) {		
			int split = (int) Math.floor((user.getCurrentHp() + target.getCurrentHp())/2);
			user.setCurrentHp(split);
			target.setCurrentHp(split);
			report.effectMessage = "The battlers shared their pain!";
		}
		return report;
	}

}
