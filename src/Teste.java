import java.util.ArrayList;
import java.util.List;

public class Teste {

	public static void main(String[] args) {
		Pokemon p1 = new Pokemon(
			"Pikachu",
			15,
			new int[] {30,10,22,40,14,38},
			new ArrayList<> (
				List.of(
					MoveDex.getMove("Thunder Shock"),
					MoveDex.getMove("Growl")
				)
			)
		);
		Pokemon p2 = new Pokemon(
			"Eevee",
			14,
			new int[] {40,25,22,18,23,30},
			new ArrayList<> (
				List.of(
					MoveDex.getMove("Tackle"),
					MoveDex.getMove("Growl")
				)
			)
		);
		Battle b = new Battle(p1,p2);
		
		BattleUI ui = new BattleUI(b);
	}

}
