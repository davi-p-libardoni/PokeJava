package moveEffect;
import battle.Battle;
import battle.BattleActionReport;
import core.Pokemon;
import move.Move;

public interface MoveEffect {
	BattleActionReport execute(Battle b,Move m, Pokemon user, Pokemon target);
}