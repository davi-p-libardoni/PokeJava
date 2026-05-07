package move;

import core.StatusCondition;
import core.Type;
import moveEffect.EffectCombo;
import moveEffect.EffectFixedDamage;
import moveEffect.EffectMultiHit;
import moveEffect.EffectNone;
import moveEffect.EffectOHKO;
import moveEffect.EffectOmnibuff;
import moveEffect.EffectPainSplit;
import moveEffect.EffectStatChange;
import moveEffect.EffectStatusCondition;
import moveEffect.MoveEffect;

public class MoveFactory {

//	static {
//        // --- EFEITOS BÁSICOS E DANO ---
//        effects.put("FALSE_SWIPE", new EffectFalseSwipe());   // Deixa com 1 HP
//
//        // --- STATS (BUFFS/DEBUFFS) ---
//        effects.put("RESET_STAT_CHANGES", new EffectResetStats());
//        effects.put("PSYCH_UP", new EffectPsychUp());
//        effects.put("MEMENTO", new EffectMemento());
//
//        // --- CONDIÇÕES DE STATUS ---
//        effects.put("CONFUSE", new EffectConfusion());
//        effects.put("FLINCH", new EffectFlinch());
//        effects.put("CURE_CONDITION", new EffectCureCondition());
//        effects.put("CURE_ALL_STATUS", new EffectCureAllStatus());
//        effects.put("TRI_ATTACK", new EffectTriAttack());
//
//        // --- CURA E DRENO ---
//        effects.put("HEAL", new EffectHeal());
//        effects.put("DRAIN", new EffectDrain()); // Giga Drain, Leech Life
//        effects.put("REST", new EffectRest());
//        effects.put("LEECH_SEED", new EffectLeechSeed());
//
//        // --- RECOIL E CRASH ---
//        effects.put("RECOIL", new EffectRecoil(false)); // Take Down, Double-Edge
//        effects.put("STRUGGLE", new EffectRecoil(true)); // Perda de 1/4 HP Max
//        effects.put("CRASH", new EffectCrashDamage());   // Jump Kick, High Jump Kick
//        effects.put("HURT_SELF", new EffectHurtSelf()); // Confusion damage/Substitute cost
//
//        // --- MULTI-HIT E COMBOS ---
//        effects.put("MULTI_HIT", new EffectMultiHit(false)); // Fury Swipes
//        effects.put("COMBO", new EffectMultiHit(true));      // Swift/Double Kick (1 attempt)
//        effects.put("RAMP_MULTI_HIT", new EffectRampMultiHit());
//        effects.put("FURY_CUTTER", new EffectFuryCutter());
//        effects.put("ROLLOUT", new EffectRollout());
//
//        // --- TURNOS (CHARGE/RECHARGE/STAGING) ---
//        effects.put("CHARGE", new EffectCharge());     // Solar Beam
//        effects.put("RECHARGE", new EffectRecharge()); // Hyper Beam
//        effects.put("OUTRAGE", new EffectOutrage());   // Petal Dance, Thrash
//        effects.put("BIDE", new EffectBide());
//        effects.put("FLY", new EffectTwoTurnInvul(Type.FLYING));
//        effects.put("DIG", new EffectTwoTurnInvul(Type.GROUND));
//        effects.put("DIVE", new EffectTwoTurnInvul(Type.WATER));
//        effects.put("BOUNCE", new EffectTwoTurnInvul(Type.FLYING));
//        
//        // --- ACERTAM INVULNERÁVEL ---
//        effects.put("HITS_FLY", new EffectHitsInvul());
//        effects.put("HITS_DIG", new EffectHitsInvul());
//        effects.put("HITS_DIVE", new EffectHitsInvul());
//        effects.put("HITS_BOUNCE", new EffectHitsInvul());
//
//        // --- CLIMA E CAMPO ---
//        effects.put("WEATHER", new EffectWeather());
//        effects.put("HAZARDS", new EffectHazards()); // Spikes, Stealth Rock
//        effects.put("REMOVE_HAZARDS", new EffectRemoveHazards()); // Rapid Spin
//        effects.put("SCREEN", new EffectScreens());   // Reflect, Light Screen
//        effects.put("MIST", new EffectMist());
//
//        // --- PROTEÇÃO E ESPECIAIS ---
//        effects.put("PROTECT", new EffectProtect());
//        effects.put("ENDURE", new EffectEndure());
//        effects.put("SUBSTITUTE", new EffectSubstitute());
//        effects.put("DESTINY_BOND", new EffectDestinyBond());
//        effects.put("PERISH_SONG", new EffectPerishSong());
//        effects.put("COUNTER", new EffectCounter());
//        effects.put("MIRROR_COAT", new EffectMirrorCoat());
//
//        // --- UTILITÁRIOS E VARIÁVEIS ---
//        effects.put("METRONOME", new EffectMetronome());
//        effects.put("TRANSFORM", new EffectTransform());
//        effects.put("CONVERSION", new EffectConversion());
//        effects.put("MIMIC", new EffectMimic());
//        effects.put("ENCORE", new EffectEncore());
//        effects.put("DISABLE", new EffectDisable());
//        effects.put("BATON_PASS", new EffectBatonPass());
//        effects.put("TRAP", new EffectTrap(false));
//        effects.put("DAMAGE_TRAP", new EffectTrap(true)); // Fire Spin, Whirlpool
//        effects.put("STEAL", new EffectStealItem());
//        effects.put("SPITE", new EffectSpite());
//        effects.put("RETURN", new EffectStatBasedDamage(true));
//        effects.put("FRUSTRATION", new EffectStatBasedDamage(false));
//
//        // --- OUTROS (FDS/LOGIC) ---
//        effects.put("DIE", new EffectSelfDestruct()); // Self-Destruct, Explosion
//        effects.put("GUARANTEED_NEXT_HIT", new EffectLockOn());
//        effects.put("FORCE_SWITCH", new EffectForceSwitch());
//        effects.put("PAYDAY", new EffectNone()); // Só dano, sem grana por enquanto
//        effects.put("PRESENT", new EffectPresent());
//        effects.put("MAGNITUDE", new EffectMagnitude());
//        effects.put("HIDDEN_POWER", new EffectHiddenPower());
//    }

	public static Move createMoveFromCsv(String[] data) {
		String name = data[0];
		int accuracy = (data[2] != "")? Integer.parseInt(data[2]) : -1;
		int pp = Integer.parseInt(data[3]);
		int power = (data[4] != "")? Integer.parseInt(data[4]) : -1;
		int priority = Integer.parseInt(data[5]);
		Type type = Type.valueOf(data[6].trim().toUpperCase());
		String description = data[7];
		String effKey = data[8].trim().toUpperCase();
		int degree = Integer.parseInt(data[9]);
		int choice = Integer.parseInt(data[10]);
		double chance = Double.parseDouble(data[11]);
		MoveEffect effectObj = getEffect(effKey,degree,choice,chance);
		
		DamageClass dmgclass = DamageClass.valueOf(data[20].replace("\"", "").trim().toUpperCase());
		return new Move(name,description,type,pp,power,accuracy,priority,dmgclass,effectObj);
	}
	
	private static MoveEffect getEffect(String effectKey, int degree, int choice, double chance) {
		switch(effectKey) {
		case "NONE": return new EffectNone();
		case "STATUS_CONDITION": return new EffectStatusCondition(choice,chance,false);
		case "STATUS_CONDITION_SELF": return new EffectStatusCondition(choice,chance,true);
		case "BUFF": return new EffectStatChange(degree,choice,chance,true);
		case "BUFF_TARGET": return new EffectStatChange(degree,choice,chance,false);
		case "DEBUFF": return new EffectStatChange(-1*degree,choice,chance,false);
		case "DEBUFF_SELF": return new EffectStatChange(-1*degree,choice,chance,true);
		case "OHKO": return new EffectOHKO();
		case "FIXED_DAMAGE": return new EffectFixedDamage(degree,choice);
		case "PAIN_SPLIT": return new EffectPainSplit();
		case "OMNIBUFF": return new EffectOmnibuff(chance);
		case "TWINEEDLE": return new EffectCombo(degree,new EffectStatusCondition(StatusCondition.POISON.ordinal(),0.2,false));
		case "COMBO": return new EffectCombo(degree);
		case "RAMP_MULTI_HIT": return new EffectMultiHit(3,1);
		case "MULTI_HIT": return new EffectMultiHit();
		default: return new EffectNone();
		}
	}
}