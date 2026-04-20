package moveEffect;

import java.util.concurrent.ThreadLocalRandom;

import core.Move;
import core.Pokemon;

public class EffectCritChance extends EffectNone {

	@Override
	protected boolean checkCrit(Move m, Pokemon user) {
		int critThreshold = calcCritThreshold(user.getCritMod()+1);
		int critAttempt = ThreadLocalRandom.current().nextInt(critThreshold);
		if(critAttempt == 1) return true;
		return false;
	}


}
