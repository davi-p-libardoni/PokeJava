package moveEffect;

import java.util.EnumMap;

import battle.Battle;
import battle.BattleActionReport;
import battle.WeatherCondition;
import core.Pokemon;
import move.Move;

public class EffectHeal extends EffectNone{
	protected int amount;	// porcentagem
	protected boolean affectedByWeather = false;
	protected EnumMap<WeatherCondition,Double> weathers;
	
	public EffectHeal(int amount,int choice) {
		this.amount = amount;
		this.affectedByWeather = true;
		this.weathers = new EnumMap<WeatherCondition,Double>(WeatherCondition.class);
		
		this.weathers.put(WeatherCondition.SUN, 0.66);
		this.weathers.put(WeatherCondition.RAIN, 0.25);
		this.weathers.put(WeatherCondition.HAIL, 0.25);
		this.weathers.put(WeatherCondition.SANDSTORM, 0.25);
		this.weathers.put(WeatherCondition.SNOW, 0.25);
		this.weathers.put(WeatherCondition.CLEAR, 0.5);
	}
	
	public EffectHeal(int amount) {
		this.amount = amount;
	}

	@Override
	public void applyPostEffect(BattleActionReport report,Battle b,Move m,Pokemon user,Pokemon target) {
		if(!report.action.successful()) return;
		
		if(affectedByWeather) {
			user.healPercent((this.weathers.get(b.getWeather()).intValue())*100);
		}else {
			user.healPercent(this.amount);
		}
	}
}
