package core;

public class Battle {
	private int turn;
	private Pokemon[] battlers = new Pokemon[2];
	private Weather weather;
	
	public Battle(Pokemon battler1,Pokemon battler2) {
		this.turn = 1;
		this.battlers[0] = battler1;
		this.battlers[1] = battler2;
		this.weather = new Weather();
	}
	
	public Pokemon getBattler(int index) {
		return this.battlers[index];
	}
	
	public BattleActionReport[] runTurn(Move m1) {
		BattleActionReport[] report = new BattleActionReport[2];
		Move m2 = TrainerAI.selectMove(this,this.battlers[1],this.battlers[0]);
		Move[] movesSpeedOrder = getFastest(this,new Move[] {m1,m2});
		
		for(int i = 0;i<2;i++) {
			Move m = movesSpeedOrder[i];
			if(m.canExecute(this, this.battlers[i], this.battlers[1-i])) {
				report[i] = m.execute(this, this.battlers[i], this.battlers[1-i]);
			}else {
				report[i] = new BattleActionReport(m.getName(),this.battlers[i],this.battlers[1-i],0,false,MoveResult.FAILED);
			}
		}
		
		if(movesSpeedOrder[0] == m1) {
			report[0].userIndex = 0;
			report[0].targetIndex = 1;
			report[1].userIndex = 1;
			report[1].targetIndex = 0;
		}else {
			report[0].userIndex = 1;
			report[0].targetIndex = 0;
			report[1].userIndex = 0;
			report[1].targetIndex = 1;
		}
		
		weather.decrement();
		turn++;
		return report;
	}
	
	public Move[] getFastest(Battle b,Move[] moves) {
		Move fastest = moves[0],slowest = moves[1];
		int fastIndex;
		
		int[] priorities = new int[] {moves[0].getPriority(),moves[1].getPriority()};
		if(priorities[0] != priorities[1]) {
			fastIndex = (priorities[0]>priorities[1])?0:1;
		} else {
			fastIndex = (b.battlers[0].getStat(Stat.SPD) > b.battlers[1].getStat(Stat.SPD))?0:1;
		}
		fastest = moves[fastIndex];
		slowest = moves[1-fastIndex];
		return new Move[] {fastest,slowest};
	}

	public WeatherCondition getWeather() {
		return this.weather.getWeather();
	}
}
