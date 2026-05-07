package battle;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

import core.Pokemon;
import core.Type;
import move.Move;

public class TrainerAI {
	public static Move selectMove(Battle b,Pokemon attacker,Pokemon defender) {
		// if (seesKill) {
		
		// } else {
		int n = attacker.moveAmount();
		int number = ThreadLocalRandom.current().nextInt(n);
		return attacker.getMove(number);
	}

	public static int selectNextMon(Battle b) {
		Pokemon opponent = b.getBattler(0);
		ArrayList<Type> enemyTypes = opponent.getTypes();
		Pokemon current;
		int greatestScore = -1,greatestScoreIndex = -1;
		for(int i = 0;i<b.battlerCount(1, false);i++) {
			current = b.getBattler(1, i);
			if(current.isFainted()) {
				continue;				
			}
			
			int score = 0;
			
			// effectiveness score
			double effectiveness = Effectiveness.check(enemyTypes.get(0), current);
			if(enemyTypes.size() > 1) {
				effectiveness *= Effectiveness.check(enemyTypes.get(1), current);
			}
			
			if(effectiveness == 0.0) score += 70;
			if(effectiveness == 0.25) score += 50;
			if(effectiveness == 0.5) score += 30;
			if(effectiveness == 2) score -= 30;
			if(effectiveness == 4) score -= 50;
			
			if(greatestScore == -1 || score > greatestScore) {
				greatestScore = score;
				greatestScoreIndex = i;
			}
		}
		return greatestScoreIndex;
	}
}
