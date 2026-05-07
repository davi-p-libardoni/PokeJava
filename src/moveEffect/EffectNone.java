package moveEffect;

import java.util.concurrent.ThreadLocalRandom;

import battle.Attempt;
import battle.Battle;
import battle.BattleActionReport;
import battle.Effectiveness;
import battle.WeatherCondition;
import core.Pokemon;
import core.Stat;
import core.StatusCondition;
import core.Type;
import move.DamageClass;
import move.Move;
import move.MoveResult;

public class EffectNone implements MoveEffect {
	@Override
	public BattleActionReport execute(Battle b, Move m, Pokemon user, Pokemon target) {
		BattleActionReport report = new BattleActionReport();
		report.moveName = m.getName();
		report.user = user;
		report.target = target;
		report.result = Attempt.check(b,m,user,target);
		if(m.getDamageClass() != DamageClass.STATUS && report.result.successful()) {			
			report.damageDealt = calculateDamage(b, m, user, target,report);
			target.takeDamage(report.damageDealt);
		}else {
			report.damageDealt = 0;
		}
		applyPostEffect(report,b,m,user,target);
		return report;
	}

	protected void applyPostEffect(BattleActionReport report,Battle b,Move m,Pokemon user,Pokemon target) { }
	
	protected int calculateDamage(Battle b, Move m, Pokemon user, Pokemon target,BattleActionReport report) {
		report.isCritical = false;
		
		// mult efetividade
		double effectiveness = Effectiveness.check(m, target);
		if(effectiveness == 0) return 0; // imune
		
		// multiplicador de clima
		double weatherMult = 1;
		Type moveType = m.getType();
		switch(b.getWeather()) {
			case WeatherCondition.SUN:
				if(moveType == Type.FIRE) weatherMult = 2;
				if(moveType == Type.WATER) weatherMult = 0.5;
				break;
			case WeatherCondition.RAIN:
				if(moveType == Type.FIRE) weatherMult = 0.5;
				if(moveType == Type.WATER) weatherMult = 2;
				break;
			default:
				break;
		}
		
		// mult burn 
		double burnMult = 1;
		if(m.getDamageClass() == DamageClass.PHYSICAL && user.getStatusCondition() == StatusCondition.BURN) {
			burnMult = 0.5;
		}
		
		// mult crit
		double critMult = 1;
		if(checkCrit(m,user)) {
			critMult = 2;
			report.isCritical = true;
		}
		
		// mult stab
		double stabMult = 1;
		if(user.hasType(moveType)) {
			stabMult = 1.5;
		}
		
		return damageFormula(b,m,user,target,weatherMult,critMult,stabMult,effectiveness,burnMult);
	}
	
	protected int getBasePower(Move m) {
		return m.getPower();
	}
	
	protected int damageFormula(Battle b,Move m,Pokemon user,Pokemon target, double weatherMod, double critMod,double stabMod,double effMod,double burnMod) {
		// Formula completa
		// {[(2 * Lvl) / 5 + 2] * Power * A/D} / 5 * Modifiers 
		
		int levelModifier = (2 * user.getLevel())/5+2;
		
		Stat attackingStat = (m.getDamageClass() == DamageClass.PHYSICAL)? Stat.ATK:Stat.SPATK;
		Stat defendingStat = (m.getDamageClass() == DamageClass.PHYSICAL)? Stat.DEF:Stat.SPDEF;
		
		int atkStat = user.getStat(attackingStat);
		int defStat = target.getStat(defendingStat);
		
		int effectiveAtkStat = user.getEffectiveStat(attackingStat);
		int effectiveDefStat = target.getEffectiveStat(defendingStat);
		
		if(critMod > 1) {
			if(effectiveAtkStat < atkStat) effectiveAtkStat = atkStat;
			if(effectiveDefStat > defStat) effectiveDefStat = defStat;
		}
		
		double ADRatio = (double) effectiveAtkStat / effectiveDefStat;
		double preMods = levelModifier * getBasePower(m) * ADRatio;
		preMods /= 50;
		preMods += 2;
		
		double postMods = preMods * weatherMod * critMod * stabMod * effMod * burnMod;
		
		return (int) Math.round(postMods);
	}
	
	protected boolean checkCrit(Move m, Pokemon user) {
		int critThreshold = calcCritThreshold(user.getCritMod());
		int critAttempt = ThreadLocalRandom.current().nextInt(critThreshold);
		if(critAttempt == 1) return true;
		return false;
	}

	protected int calcCritThreshold(int critMod) {
		if(critMod >= 0 && critMod <=2) return 16/(critMod+1);
		if(critMod == 3) return 3;
		if(critMod >= 4) return 2;
		return 16;
	}
}
