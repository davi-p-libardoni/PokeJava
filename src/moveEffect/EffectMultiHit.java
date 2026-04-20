package moveEffect;

import java.util.concurrent.ThreadLocalRandom;

import core.Attempt;
import core.Battle;
import core.BattleActionReport;
import core.Move;
import core.Pokemon;

public class EffectMultiHit extends EffectNone{
	private int maxHits;
	private double multIfRamp = 0;
	private int currentHit = 0;
	
	public EffectMultiHit() {
		this.maxHits = 5;
	}
	
	public EffectMultiHit(int degree, int i) {
		this.maxHits = degree;
		this.multIfRamp = i;
	}

	@Override
	public BattleActionReport execute(Battle b, Move m, Pokemon user, Pokemon target) {
		BattleActionReport report = new BattleActionReport();
		report.moveName = m.getName();
		report.user = user;
		report.target = target;
		report.result = Attempt.check(b,m,user,target);
		if(report.result.successful()) {				
			// primeiro hit
			report.damageDealt = super.calculateDamage(b, m, user, target,report);
			target.takeDamage(report.damageDealt);
			this.currentHit++;
			
			// proximos hits

			int attempt = ThreadLocalRandom.current().nextInt(1, 21);
			int hits = 1;
			if(this.maxHits == 5) {
				if(attempt <= 20) hits = 5;
				if(attempt <= 17) hits = 4;
				if(attempt <= 14) hits = 3;
				if(attempt <= 7) hits = 2;
			}
			if(this.maxHits == 3) {
				if(Attempt.check(b, m, user, target).successful()) {
					hits = 2;
					if(Attempt.check(b, m, user, target).successful()){
						hits = 3;
					}
				}
			}
			int index;
			for(index = 1;index < hits;index++) {
				if(target.isFainted()) {
					break;
				}		
				report.damageDealt = super.calculateDamage(b, m, user, target,report);
				target.takeDamage(report.damageDealt);
				this.currentHit++;
			}
			report.effectMessage = "It hit "+index+" times.";
		}else {
			report.damageDealt = 0;
		}

		this.currentHit = 0;
		return report;
	}
	
	@Override
	protected int getBasePower(Move m) {
		if(this.multIfRamp != 0) {
			return (int) (m.getPower() + (m.getPower() * this.multIfRamp * this.currentHit));
		}else {
			return m.getPower();
		}
	}

}
