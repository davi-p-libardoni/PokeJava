package moveEffect;

import java.util.concurrent.ThreadLocalRandom;

import core.Battle;
import core.BattleActionReport;
import core.Move;
import core.Pokemon;

public class EffectFixedDamage extends EffectNone {
	protected int degree;
	protected FixedDamageType type;
	
	public EffectFixedDamage(int degree, int choice) {
		this.degree = degree;
		this.type = FixedDamageType.values()[choice];
	}
	
	@Override
	protected int calculateDamage(Battle b, Move m, Pokemon user, Pokemon target,BattleActionReport report) {
		int damage = 0;
		report.isCritical = false;
		if(this.type == FixedDamageType.FLAT) {
			damage = this.degree;
		}else if(this.type == FixedDamageType.LEVEL) {
			damage = user.getLevel();
		}else if(this.type == FixedDamageType.HALF) {
			damage = target.getCurrentHp()/2;
		}else if(this.type == FixedDamageType.PSYWAVE) {
			double modifier = ThreadLocalRandom.current().nextDouble(0.5, 1.5);
			damage = (int) (user.getLevel() * modifier);
 		}
		report.damageDealt = damage;
		return damage;
	}

}
