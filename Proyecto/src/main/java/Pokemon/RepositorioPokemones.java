package Pokemon;

import BaseDeDatos.BaseDeDatos;
import Excepciones.PokemonNoEncontradoException;
import Usuario.Usuario;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class RepositorioPokemones {
    private List<Pokemon> pokemones;
    private static RepositorioPokemones instanciacion = null;

    public static RepositorioPokemones inicializar() {
        if(instanciacion == null) instanciacion = new RepositorioPokemones();
        return instanciacion;
    }
    private RepositorioPokemones(){
        this.actualizarPokemones();
    }

    public void actualizarPokemones(){
        pokemones = new BaseDeDatos().getPokemones();
        this.obtenerEvoluciones();
    }

    public List<Pokemon> getPokemonesFromDB(){
        return pokemones;
    }
    public Pokemon getPokemonFromDB(String nombrePokemon){
        if(pokemones.stream().noneMatch(pokemon -> pokemon.getNombre().equals(nombrePokemon))) throw new PokemonNoEncontradoException();
        return pokemones.stream().filter(pokemon -> pokemon.getNombre().equals(nombrePokemon)).collect(Collectors.toList()).get(0);
    }
    public List<Pokemon> getPokemonesFromUsuario(Usuario usuario){
        return pokemones.stream().filter(pokemon -> usuario.getNombre().equalsIgnoreCase(pokemon.getDuenio().getNombre())).collect(Collectors.toList());
    }
    private void obtenerEvoluciones(){
        pokemones.forEach(
                pokemon -> {
                    pokemon.getEvoluciones().clear();
                    new BaseDeDatos().getEvolucionesPokemon(pokemon.getNombre()).forEach(
                            evolucion -> pokemon.evoluciones.add(this.getPokemonFromDB(evolucion.getNombre()))
                    );
                }
        );
    }
}
