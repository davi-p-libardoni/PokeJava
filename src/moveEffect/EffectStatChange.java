package moveEffect;

import core.Battle;
import core.BattleActionReport;
import core.Move;
import core.Pokemon;
import core.SpecialStat;
import core.Stat;
import java.util.concurrent.ThreadLocalRandom;

public class EffectStatChange extends EffectNone {
	int degree;
	boolean isSpecialStat;
	Stat stat;
	SpecialStat specialStat;
	double chance;
	boolean onSelf;
	
	public EffectStatChange(int degree,int choice,double chance,boolean onSelf) {
		this.degree = degree;
		if(choice > 0 && choice < 6) {			
			this.stat = Stat.values()[choice];
			this.isSpecialStat = false;
		}else {
			this.specialStat = SpecialStat.values()[choice-6];
			this.isSpecialStat = true;
		}
		this.chance = chance;
		this.onSelf = onSelf;
	}
	
	@Override
	protected void applyPostEffect(BattleActionReport report,Battle b,Move m,Pokemon user,Pokemon target) {
		Pokemon trgt = (onSelf)?user:target;
		boolean result = false;
		if(chance == 1 || chance == 0 || ThreadLocalRandom.current().nextDouble() < this.chance) {
			String statName,quantifier = "",qualifier = "",sum = "";
			if(this.isSpecialStat) {
				result = trgt.applySpecialStatModifier(specialStat, degree);
				statName = specialStat.toString();
			}else {
				result = trgt.applyStatModifier(stat, degree);
				statName = stat.toString();
			}
			switch(this.degree) {
			case 1:
			case -1:
				quantifier = "";
				break;
			case 2:
				quantifier = "sharply";
				break;
			case 3:
				quantifier = "drastically";
				break;
			case -2:
				quantifier = "harshly";
				break;
			case -3:
				quantifier = "severely";
			}
			qualifier = (this.degree < 0)?"fell":"rose";
			String space = (this.degree != -1 && this.degree != 1)? " ":"";
			sum = (this.degree<0)? quantifier+space+qualifier:qualifier+space+quantifier;
			sum += ".";
			String msg = "";
			if(result == false) {
				msg += "But "+trgt.getName()+"'s "+statName+" can't get any "+((degree > 0)?"higher":"lower")+".";
			}else {
				msg += trgt.getName()+"'s "+statName+" "+sum;
			}
			report.effectMessage = msg;
		}
	}

}
