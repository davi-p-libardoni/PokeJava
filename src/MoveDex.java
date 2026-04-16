import java.util.HashMap;
import java.util.Map;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

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
				String name = data[0];
				int accuracy = (data[2] != "")? Integer.parseInt(data[2]) : -1;
				int pp = Integer.parseInt(data[3]);
				int power = (data[4] != "")? Integer.parseInt(data[4]) : -1;
				int priority = Integer.parseInt(data[5]);
				Type type = Type.valueOf(data[6].trim().toUpperCase());
				String description = data[7];
				DamageClass dmgclass = DamageClass.valueOf(data[20].replace("\"", "").trim().toUpperCase());
				
				moveList.put(name, new Move(name,description,type,pp,power,accuracy,priority,dmgclass));
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
