package battle;
import java.util.concurrent.ThreadLocalRandom;

import core.Pokemon;
import move.DamageClass;
import move.Move;
import move.MoveResult;

public class Attempt {
	public static MoveResult check(Battle b, Move m, Pokemon attacker, Pokemon defender) {
		// fazer mudança do threshold pra contabilizar acc e evasion depois
		int threshold = m.getAccuracy();
		if(m.getAccuracy() == -1 || threshold >= 100 || ThreadLocalRandom.current().nextInt(101) <= threshold) {
			double effectiveness = Effectiveness.check(m,defender);
			if(effectiveness == 0.0) return MoveResult.IMMUNE;
			if(effectiveness == 1.0 || m.getDamageClass() == DamageClass.STATUS) return MoveResult.SUCCESS;
			if(effectiveness <= 0.5) return MoveResult.NOT_VERY;
			if(effectiveness >= 2.0) return MoveResult.SUPER;
		}
		return MoveResult.MISSED;
	}
}
