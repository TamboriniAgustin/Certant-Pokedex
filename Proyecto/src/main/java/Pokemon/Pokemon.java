package Pokemon;

import BaseDeDatos.BaseDeDatos;
import Excepciones.NoSeExpecificoTipoDePokemonException;
import Usuario.Usuario;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Pokemon {
    private String nombre;
    private List<String> tipos;
    private int nivel;
    private List<String> habilidades;
    private List<Pokemon> evoluciones;
    private Usuario duenio;

    public Pokemon(String nombre, List<String> tipos, int nivel, List<String> habilidades, List<Pokemon> evoluciones, Usuario duenio){
        this.nombre = nombre;
        this.tipos = tipos;
        this.nivel = nivel;
        this.habilidades = habilidades;
        this.evoluciones = evoluciones;
        this.duenio = duenio;
    }

    public String getNombre() { return nombre; }
    public List<String> getTipos() { return tipos; }
    public int getNivel() { return nivel; }
    public List<String> getHabilidades() { return habilidades; }
    public List<Pokemon> getEvoluciones() { return evoluciones; }
    public Usuario getDuenio(){
        return duenio;
    }

    public void modificarNombre(String nuevoNombre){
        this.nombre = nuevoNombre;
    }
    public void modificarNivel(int nuevoNivel) { this.nivel = nuevoNivel; }
    public void nuevoDuenio(Usuario usuario){
        this.duenio = usuario;
    }
    public void guardarCambios(){
        new BaseDeDatos().modificarPokemon(this);
    }

    public void agregarTipo(String tipo){
        new BaseDeDatos().insertarTiposPokemon(this.getNombre(), Arrays.asList(tipo));
        tipos.add(tipo);
    }
    public void removerTipo(String tipo){
        if(tipos.contains(tipo)) {
            new BaseDeDatos().eliminarTipoPokemon(this.getNombre(), tipo);
            tipos.remove(tipo);
        }
    }

    public void agregarHabilidad(String habilidad){
        new BaseDeDatos().insertarHabilidadesPokemon(this.getNombre(), Arrays.asList(habilidad));
        habilidades.add(habilidad);
    }
    public void removerHabilidad(String habilidad){
        if(habilidades.contains(habilidad)) {
            new BaseDeDatos().eliminarHabilidadPokemon(this.getNombre(), habilidad);
            habilidades.remove(habilidad);
        }
    }

    public void nuevaEvolucion(Pokemon pokemon){
        new BaseDeDatos().insertarEvolucionesPokemon(this.getNombre(), Arrays.asList(pokemon));
        this.evoluciones.add(pokemon);
    }
    public void eliminarEvolucion(Pokemon pokemon){
        if(evoluciones.contains(pokemon)) {
            new BaseDeDatos().eliminarEvolucionPokemon(this.getNombre(), pokemon.getNombre());
            this.evoluciones.remove(pokemon);
        }
    }
}