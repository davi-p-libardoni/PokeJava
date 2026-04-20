package moveEffect;
import core.Battle;
import core.BattleActionReport;
import core.Move;
import core.Pokemon;

public interface MoveEffect {
	BattleActionReport execute(Battle b,Move m, Pokemon user, Pokemon target);
}