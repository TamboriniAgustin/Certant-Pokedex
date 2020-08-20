package Pokemon;

import BaseDeDatos.BaseDeDatos;
import Excepciones.NoSeExpecificoTipoDePokemonException;
import Usuario.Usuario;

import java.util.ArrayList;
import java.util.List;

public class BorradorPokemon {
    private String nombre;
    private List<String> tipos = new ArrayList();
    private int nivel = 0;
    private List<String> habilidades = new ArrayList();
    private List<Pokemon> evoluciones = new ArrayList();
    private Usuario duenio;

    public BorradorPokemon(String nombre){
        this.nombre = nombre;
    }

    public void agregarTipo(String tipo){
        this.tipos.add(tipo);
    }
    public void setearNivel(int nivel){
        this.nivel = nivel;
    }
    public void agregarHabilidad(String habilidad){
        this.habilidades.add(habilidad);
    }
    public void nuevaEvolucion(Pokemon pokemon){
        this.evoluciones.add(pokemon);
    }

    public Pokemon crearPokemon(){
        if(tipos.size() == 0) throw new NoSeExpecificoTipoDePokemonException();

        Pokemon pokemonGenerado = new Pokemon(nombre, tipos, nivel, habilidades, evoluciones, duenio);
        new BaseDeDatos().insertarPokemon(pokemonGenerado);

        return pokemonGenerado;
    }
}
