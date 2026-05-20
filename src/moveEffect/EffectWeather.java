package moveEffect;

import battle.Battle;
import battle.BattleActionReport;
import battle.WeatherCondition;
import core.Pokemon;
import move.Move;

public class EffectWeather extends EffectNone{
	protected WeatherCondition cond;
	
	public EffectWeather(WeatherCondition cond) {
		this.cond = cond;
	}
	
	@Override
	protected void applyPostEffect(BattleActionReport report, Battle b, Move m, Pokemon user, Pokemon target) {
		// se falhou por algum motivo, nao faz nada
		if(report.result.successful() == false) return;
		
		int duration = 5;
		// implementar handling de itens que extendem weather.
		
		b.setWeather(this.cond,duration);
	}
}
