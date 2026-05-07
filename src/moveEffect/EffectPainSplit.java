package moveEffect;

import battle.Attempt;
import battle.Battle;
import battle.BattleActionReport;
import core.Pokemon;
import move.Move;
import move.MoveResult;

public class EffectPainSplit extends EffectNone {

	@Override
	public BattleActionReport execute(Battle b, Move m, Pokemon user, Pokemon target) {
		BattleActionReport report = new BattleActionReport();
		report.result = Attempt.check(b,m,user,target);
		if(report.result == MoveResult.SUCCESS) {		
			int split = (int) Math.floor((user.getCurrentHp() + target.getCurrentHp())/2);
			user.setCurrentHp(split);
			target.setCurrentHp(split);
			report.message = "The battlers shared their pain!";
		}
		return report;
	}

}
