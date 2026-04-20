package core;
import java.util.HashMap;
import java.util.Map;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import moveEffect.EffectEnum;
import moveEffect.EffectNone;
import moveEffect.EffectStatusCondition;
import moveEffect.MoveEffect;

public class MoveDex {
	private static Map<String,Move> moveList = new HashMap<>();
	private static boolean loaded = false;
	
	public static void loadMoves() {
		try(Scanner scanner = new Scanner(new File("src/moves.csv"))){
			if (scanner.hasNextLine()) scanner.nextLine();
			
			while(scanner.hasNextLine()) {
				String line = scanner.nextLine();
				if (line.trim().isEmpty()) continue;
				String[] data = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);
				if(data.length < 21) continue;
				
				Move move = MoveFactory.createMoveFromCsv(data);
				moveList.put(move.getName(), move);
			}
			loaded = true;
		} catch (FileNotFoundException e) {
			System.err.println("Erro ao carregar csv: " + e.getMessage());
			e.printStackTrace();
		}
	}

	public static Move getMove(String nome) {
		if(loaded == false) loadMoves();
        return moveList.get(nome);
    }
}
