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
    }

    public List<Pokemon> getPokemonesFromDB(){
        return pokemones;
    }
    public Pokemon getPokemonFromDB(String nombrePokemon){
        if(!pokemones.stream().anyMatch(pokemon -> pokemon.getNombre() == nombrePokemon)) throw new PokemonNoEncontradoException();
        return pokemones.stream().filter(pokemon -> pokemon.getNombre() == nombrePokemon).collect(Collectors.toList()).get(0);
    }
    public List<Pokemon> getPokemonesFromUsuario(Usuario usuario){
        return pokemones.stream().filter(pokemon -> pokemon.getDuenio() == usuario).collect(Collectors.toList());
    }
}
