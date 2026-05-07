package core;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import move.Move;

public class PokeDex {
	private static Map<String,Pokemon> pokemonList = new HashMap<>();
	private static boolean loaded = false;
	
	public static void loadPokemon() {
		try(Scanner scanner = new Scanner(new File("src/pokemon.csv"))){
			if (scanner.hasNextLine()) scanner.nextLine();
			
			while(scanner.hasNextLine()) {
				String line = scanner.nextLine();
				if (line.trim().isEmpty()) continue;
				String[] data = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);
				if(data.length < 12) continue;
				String name = data[0];
				int id = Integer.parseInt(data[1]);
				int hp = Integer.parseInt(data[2]);
				int atk = Integer.parseInt(data[3]);
				int def = Integer.parseInt(data[4]);
				int spatk = Integer.parseInt(data[5]);
				int spdef = Integer.parseInt(data[6]);
				int speed = Integer.parseInt(data[7]);
				Type type1 = Type.valueOf(data[10].trim().toUpperCase());
				ArrayList<Type> types = new ArrayList<> ();
				types.add(type1);
				if(data[11].length() > 0) {
					types.add(Type.valueOf(data[11].trim().toUpperCase()));
				}
				
				pokemonList.put(name, new Pokemon(name, 0, types, new int[] {hp,atk,def,spatk,spdef,speed}, null, id));
			}
			loaded = true;
		} catch (FileNotFoundException e) {
			System.err.println("Erro ao carregar csv: " + e.getMessage());
			e.printStackTrace();
		}
	}
	
	public static Pokemon getPokemon(String nome,int level,ArrayList<Move> moves) {
		if(loaded == false) loadPokemon();
        Pokemon base = pokemonList.get(nome);
        int[] stats = base.getStats().clone();
        stats[0] = ((2 * base.getStat(Stat.HP) * level)/100)+level+10;
        for(int i = 1;i<6;i++) {
        	int baseStt = base.getStat(i);
        	stats[i] = (((2 * baseStt * level)/100)+5);
        	// x Nature quando implementar
        }
        Pokemon mon = new Pokemon(nome,level,new ArrayList<Type>(base.getTypes()),stats,moves,base.getId());
        return mon;
    }
	
	public static Pokemon getPokemon(String nome,int level,ArrayList<Move> moves,boolean shiny) {
		if(loaded == false) loadPokemon();
        Pokemon base = pokemonList.get(nome);
        int[] stats = base.getStats().clone();
        stats[0] = ((2 * base.getStat(Stat.HP) * level)/100)+level+10;
        for(int i = 1;i<6;i++) {
        	int baseStt = base.getStat(i);
        	stats[i] = (((2 * baseStt * level)/100)+5);
        	// x Nature quando implementar
        }
        Pokemon mon = new Pokemon(nome,level,new ArrayList<Type>(base.getTypes()),stats,moves,base.getId(),shiny);
        return mon;
    }
	
	public static int getId(Pokemon p) {
		if(p.getId() == -1) {
			int id = getPokemon(p.getName(),0,null).getId();
			p.setId(id);
		}
		if(p.getId() < 1 || p.getId() > 649) return 0;
		return p.getId();
	}
}
