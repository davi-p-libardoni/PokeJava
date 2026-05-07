package moveEffect;

import battle.Attempt;
import battle.Battle;
import battle.BattleActionReport;
import core.Pokemon;
import core.Stat;
import move.Move;
import move.MoveResult;

public class EffectOHKO implements MoveEffect {

	@Override
	public BattleActionReport execute(Battle b, Move m, Pokemon user, Pokemon target) {
		BattleActionReport report = new BattleActionReport();
		report.moveName = m.getName();
		report.user = user;
		report.target = target;
		report.result = Attempt.check(b,m,user,target);
		if(report.result == MoveResult.SUCCESS || report.result == MoveResult.SUPER || report.result == MoveResult.NOT_VERY) {
			report.result = MoveResult.OHKO;
			report.damageDealt = target.getStat(Stat.HP);
			report.message = "It was a One-Hit KO!";
			target.takeDamage(report.damageDealt);
		}
		return report;
	}

}
