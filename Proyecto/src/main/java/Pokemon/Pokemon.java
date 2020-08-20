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
    public int nivel;
    public List<String> habilidades;
    public List<Pokemon> evoluciones;
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

    public void modificarNivel(int nuevoNivel) {
        this.nivel = nuevoNivel;
        new BaseDeDatos().modificarPokemon(this);
    }
    public void nuevoDuenio(Usuario usuario){
        this.duenio = usuario;
        new BaseDeDatos().modificarPokemon(this);
    }

    public void agregarTipo(String tipo){
        tipos.add(tipo);
        new BaseDeDatos().insertarTiposPokemon(this.getNombre(), Arrays.asList(tipo));
    }
    public void removerTipo(String tipo){
        if(tipos.contains(tipo)) {
            tipos.remove(tipo);
            new BaseDeDatos().eliminarTipoPokemon(this.getNombre(), tipo);
        }
    }

    public void agregarHabilidad(String habilidad){
        habilidades.add(habilidad);
        new BaseDeDatos().insertarHabilidadesPokemon(this.getNombre(), Arrays.asList(habilidad));
    }
    public void removerHabilidad(String habilidad){
        if(habilidades.contains(habilidad)) {
            habilidades.remove(habilidad);
            new BaseDeDatos().eliminarHabilidadPokemon(this.getNombre(), habilidad);
        }
    }

    public void nuevaEvolucion(Pokemon pokemon){
        this.evoluciones.add(pokemon);
        new BaseDeDatos().insertarEvolucionesPokemon(this.getNombre(), Arrays.asList(pokemon));
    }
    public void eliminarEvolucion(Pokemon pokemon){
        if(evoluciones.contains(pokemon)) {
            this.evoluciones.remove(pokemon);
            new BaseDeDatos().eliminarEvolucionPokemon(this.getNombre(), pokemon.getNombre());
        }
    }
}