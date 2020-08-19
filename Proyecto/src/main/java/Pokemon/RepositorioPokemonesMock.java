package Pokemon;

import Excepciones.PokemonNoEncontradoException;
import Usuario.Usuario;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class RepositorioPokemonesMock {
    private List<Pokemon> pokemones = new ArrayList();

    public RepositorioPokemonesMock(){
        Pokemon pokemon1 = new Pokemon("Pokemon1", new ArrayList<>(Arrays.asList("Tierra")), 1, new ArrayList<>(Arrays.asList("Formar una Tormenta Tierra")), new ArrayList<>(), null);
        Pokemon pokemon2 = new Pokemon("Pokemon2", new ArrayList<>(Arrays.asList("Veneno")), 3, new ArrayList<>(), new ArrayList<>(Arrays.asList(pokemon1)), null);
        Pokemon pokemon3 = new Pokemon("Pokemon3", new ArrayList<>(Arrays.asList("Volador")), 4, new ArrayList<>(), new ArrayList<>(Arrays.asList(pokemon2)), null);
        Pokemon pokemon4 = new Pokemon("Pokemon4", new ArrayList<>(Arrays.asList("Psiquico")), 1, new ArrayList<>(Arrays.asList("Leer Mentes")), new ArrayList<>(Arrays.asList(pokemon3)), null);
        Pokemon pokemon5 = new Pokemon("Pokemon5", new ArrayList<>(Arrays.asList("Tierra", "Veneno")), 1, new ArrayList<>(Arrays.asList("Formar una Tormenta de Arena", "Escupir ácido")), new ArrayList<>(Arrays.asList(pokemon4)), null);
        Pokemon pokemon6 = new Pokemon("Pokemon6", new ArrayList<>(Arrays.asList("Tierra")), 8, new ArrayList<>(Arrays.asList("Tirar piedras")), new ArrayList<>(Arrays.asList(pokemon5)), null);
        Pokemon pokemon7 = new Pokemon("Pokemon7", new ArrayList<>(Arrays.asList("Tierra", "Volador", "Psiquico")), 2, new ArrayList<>(Arrays.asList("Formar un tornado", "Leer mentes", "Dormir enemigos")), new ArrayList<>(Arrays.asList(pokemon6)), null);
        Pokemon pokemon8 = new Pokemon("Pokemon8", new ArrayList<>(Arrays.asList("Volador")), 2, new ArrayList<>(), new ArrayList<>(), null);
        Pokemon pokemon9 = new Pokemon("Pokemon9", new ArrayList<>(Arrays.asList("Psiquico")), 1, new ArrayList<>(Arrays.asList("Dormir enemigos", "Destruir la memoria de un enemigo")), new ArrayList<>(), null);
        Pokemon pokemon10 = new Pokemon("Pokemon10", new ArrayList<>(Arrays.asList("Tierra", "Volador")), 1, new ArrayList<>(), new ArrayList<>(Arrays.asList(pokemon7, pokemon8, pokemon9)), null);

        pokemones.add(pokemon1);
        pokemones.add(pokemon2);
        pokemones.add(pokemon3);
        pokemones.add(pokemon4);
        pokemones.add(pokemon5);
        pokemones.add(pokemon6);
        pokemones.add(pokemon7);
        pokemones.add(pokemon8);
        pokemones.add(pokemon9);
        pokemones.add(pokemon10);
    }

    public void actualizarPokemones(){
        pokemones.clear();

        //Se cargarían todos los pokemones nuevamente con las modificaciones realizadas y los nuevos añadidos
        Pokemon pokemon1 = new Pokemon("Pokemon1", new ArrayList<>(Arrays.asList("Tierra")), 1, new ArrayList<>(Arrays.asList("Formar una Tormenta Tierra")), new ArrayList<>(), null);
        Pokemon pokemon2 = new Pokemon("Pokemon2", new ArrayList<>(Arrays.asList("Veneno")), 3, new ArrayList<>(), new ArrayList<>(Arrays.asList(pokemon1)), null);
        Pokemon pokemon3 = new Pokemon("Pokemon3", new ArrayList<>(Arrays.asList("Volador")), 6, new ArrayList<>(Arrays.asList("Planear durante 30 segundos")), new ArrayList<>(Arrays.asList(pokemon2)), null);
        Pokemon pokemon4 = new Pokemon("Pokemon4", new ArrayList<>(Arrays.asList("Psiquico")), 1, new ArrayList<>(Arrays.asList("Leer Mentes")), new ArrayList<>(Arrays.asList(pokemon3)), null);
        Pokemon pokemon5 = new Pokemon("Pokemon5", new ArrayList<>(Arrays.asList("Tierra", "Veneno")), 1, new ArrayList<>(Arrays.asList("Formar una Tormenta de Arena", "Escupir ácido")), new ArrayList<>(Arrays.asList(pokemon4)), null);
        Pokemon pokemon6 = new Pokemon("Pokemon6", new ArrayList<>(Arrays.asList("Tierra")), 8, new ArrayList<>(Arrays.asList("Tirar piedras")), new ArrayList<>(Arrays.asList(pokemon5)), null);
        Pokemon pokemon7 = new Pokemon("Pokemon7", new ArrayList<>(Arrays.asList("Tierra", "Volador", "Psiquico")), 2, new ArrayList<>(Arrays.asList("Formar un tornado", "Leer mentes", "Dormir enemigos")), new ArrayList<>(Arrays.asList(pokemon6)), null);
        Pokemon pokemon8 = new Pokemon("Pokemon8", new ArrayList<>(Arrays.asList("Volador")), 7, new ArrayList<>(), new ArrayList<>(), null);
        Pokemon pokemon9 = new Pokemon("Pokemon9", new ArrayList<>(Arrays.asList("Psiquico")), 3, new ArrayList<>(Arrays.asList("Dormir enemigos", "Destruir la memoria de un enemigo")), new ArrayList<>(), null);
        Pokemon pokemon10 = new Pokemon("Pokemon10", new ArrayList<>(Arrays.asList("Tierra", "Volador")), 1, new ArrayList<>(), new ArrayList<>(Arrays.asList(pokemon7, pokemon8, pokemon9)), null);
        Pokemon pokemon11 = new Pokemon("Pokemon11", new ArrayList<>(Arrays.asList("Desconocido")), 12, new ArrayList<>(Arrays.asList("Destruir planetas")), new ArrayList<>(Arrays.asList(pokemon10)), null);

        pokemones.add(pokemon1);
        pokemones.add(pokemon2);
        pokemones.add(pokemon3);
        pokemones.add(pokemon4);
        pokemones.add(pokemon5);
        pokemones.add(pokemon6);
        pokemones.add(pokemon7);
        pokemones.add(pokemon8);
        pokemones.add(pokemon9);
        pokemones.add(pokemon10);
        pokemones.add(pokemon11);
    }

    public List<Pokemon> getPokemonesFromDB(){
        return pokemones;
    }
    public Pokemon getPokemonFromDB(String nombrePokemon){
        if(!pokemones.stream().anyMatch(pokemon -> pokemon.getNombre() == nombrePokemon)) throw new PokemonNoEncontradoException();
        return pokemones.stream().filter(pokemon -> pokemon.getNombre() == nombrePokemon).collect(Collectors.toList()).get(0);
    }
}
