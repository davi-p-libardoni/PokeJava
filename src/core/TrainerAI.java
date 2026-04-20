package core;
import java.util.concurrent.ThreadLocalRandom;

public class TrainerAI {
	public static Move selectMove(Battle b,Pokemon attacker,Pokemon defender) {
		// if (seesKill) {
		
		// } else {
		int n = attacker.moveAmount();
		int number = ThreadLocalRandom.current().nextInt(n);
		return attacker.getMove(number);
	}
}
