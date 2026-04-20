package moveEffect;

import java.util.concurrent.ThreadLocalRandom;

import core.Battle;
import core.BattleActionReport;
import core.Move;
import core.Pokemon;
import core.Stat;

public class EffectOmnibuff extends EffectNone {
	private double chance;
	
	public EffectOmnibuff(double chance) {
		this.chance = chance;
	}

	@Override
	protected void applyPostEffect(BattleActionReport report,Battle b,Move m,Pokemon user,Pokemon target) {
		if(chance == 1 || chance == 0 || ThreadLocalRandom.current().nextDouble() > this.chance) {
			report.effectMessage = user.getName() + "'s stats all rose!";
			user.applyStatModifier(Stat.ATK, 1);
			user.applyStatModifier(Stat.DEF, 1);
			user.applyStatModifier(Stat.SPATK, 1);
			user.applyStatModifier(Stat.SPDEF, 1);
			user.applyStatModifier(Stat.SPD, 1);
		}
	}

}
