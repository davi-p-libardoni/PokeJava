package battle;

import java.util.ArrayList;

import core.Pokemon;
import core.Stat;
import move.Move;
import move.MoveResult;

public class Battle {
	@SuppressWarnings("unused")
	private int turn;
	private ArrayList<ArrayList<Pokemon>> battlers = new ArrayList<> ();
	private int[] currentMon = new int[2];
	private Weather weather;
	
	public Battle(Pokemon battler1,Pokemon battler2) {
		this.turn = 1;
		this.battlers.add(new ArrayList<Pokemon>());
		this.battlers.add(new ArrayList<Pokemon>());
		this.battlers.get(0).add(battler1);
		this.battlers.get(1).add(battler2);
		this.currentMon[0] = 0;
		this.currentMon[1] = 0;
		this.weather = new Weather();
	}
	
	public Battle(ArrayList<Pokemon> battlers1,ArrayList<Pokemon> battlers2) {
		this.turn = 1;
		this.battlers.add(battlers1);
		this.battlers.add(battlers2);
		this.currentMon[0] = 0;
		this.currentMon[1] = 0;
		this.weather = new Weather();
	}
	
	public Pokemon getBattler(int side) {
		return this.battlers.get(side).get(this.currentMon[side]);
	}
	
	public Pokemon getBattler(int side,int index) {
		return this.battlers.get(side).get(index);
	}
	
	public BattleActionReport[] runTurn(Move m1) {
		BattleActionReport[] report = new BattleActionReport[2];
		Move m2 = TrainerAI.selectMove(this,getBattler(1),getBattler(0));
		int[] speedOrder = getFastest(this,new Move[] {m1,m2});
		Move[] moves = new Move[] {m1,m2};
		
		Pokemon[] mons = new Pokemon[] {this.getBattler(speedOrder[0]),this.getBattler(speedOrder[1])};
		Move[] movesSpeedOrder = new Move[] {moves[speedOrder[0]],moves[speedOrder[1]]};
		
		for(int i = 0;i<2;i++) {
			Move m = movesSpeedOrder[i];
			ActionCapacityReport act = mons[i].canMove();
			if(act.successful()) {
				if(m.canExecute(this, mons[i], mons[1-i]) && !mons[i].isFainted()) {
					report[i] = m.execute(this, mons[i], mons[1-i]);
				}else {
					report[i] = new BattleActionReport(m.getName(),mons[i],mons[1-i],0,false,MoveResult.FAILED);
				}
				report[i].action = act;
			}else {
				report[i] = new BattleActionReport(m.getName(),mons[i],mons[1-i],MoveResult.COULDNT_MOVE,"",act);
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
	
	public BattleActionReport[] runTurnSwitch(int switchToIndex) {
		BattleActionReport[] report = new BattleActionReport[2];
		Move m = TrainerAI.selectMove(this,getBattler(1),getBattler(0));
		
		setBattler(0,switchToIndex);
		report[0] = new BattleActionReport();
		report[0].type = ActionType.SWITCH;
		report[0].switchTo = getBattler(0,switchToIndex).getName();
		
		if(m.canExecute(this, getBattler(1), getBattler(0)) && !getBattler(1).isFainted()) {
			report[1] = m.execute(this, getBattler(1), getBattler(0));
		}else {
			report[1] = new BattleActionReport(m.getName(),getBattler(1),getBattler(0),0,false,MoveResult.FAILED);
		}
		
		report[0].userIndex = 0;
		report[0].targetIndex = 1;
		report[1].userIndex = 1;
		report[1].targetIndex = 0;
		
		weather.decrement();
		turn++;
		return report;
	}
	
	public int[] getFastest(Battle b,Move[] moves) {
		int fastIndex;
		
		int[] priorities = new int[] {moves[0].getPriority(),moves[1].getPriority()};
		if(priorities[0] != priorities[1]) {
			fastIndex = (priorities[0]>priorities[1])?0:1;
		} else {
			fastIndex = (getBattler(0).getEffectiveStat(Stat.SPD) > getBattler(1).getEffectiveStat(Stat.SPD))?0:1;
		}
		return new int[] {fastIndex,1-fastIndex};
	}

	public WeatherCondition getWeather() {
		return this.weather.getWeather();
	}

	public int battlerCount(int index,boolean onlyAlive) {
		if(onlyAlive == false) {
			return this.battlers.get(index).size();
		}
		int count = 0;
		ArrayList<Pokemon> battlers = this.battlers.get(index);
		for(int i = 0;i<battlers.size();i++) {
			if(!battlers.get(i).isFainted()) {
				count++;
			}
		}
		System.out.println(count);
		return count;
	}

	public void setBattler(int side, int index) {
		this.currentMon[side] = index;
	}
}
