package Pokemon;

import Excepciones.NoSeExpecificoTipoDePokemonException;
import Excepciones.PokemonNoEncontradoException;
import org.junit.Test;
import java.util.Arrays;
import java.util.List;
import static org.junit.Assert.*;

public class TestPokemon {
    /*
        Tomamos un repositorioMock para que el estado
        de la base de datos en algún momento específico
        no afecte el comportamiento de los tests
    */
    private RepositorioPokemonesMock pokemonesDB = new RepositorioPokemonesMock();

    //Testing obtención de pokemones desde la base de datos
    @Test
    public void obtenerPokemonesDesdeLaBaseDeDatos(){
        assertTrue(pokemonesDB.getPokemonesFromDB().size() > 0);
    }

    //Testing obtención de datos principales desde la base de datos
    @Test
    public void obtenerDatosPrincipalesDePokemon(){
        String pokemonBuscado = "Pokemon9";

        assertEquals(pokemonBuscado, pokemonesDB.getPokemonFromDB(pokemonBuscado).getNombre());
        assertEquals(Arrays.asList("Psiquico"), pokemonesDB.getPokemonFromDB(pokemonBuscado).getTipos());
        assertEquals(1, pokemonesDB.getPokemonFromDB(pokemonBuscado).getNivel());
    }
    @Test
    public void obtenerHabilidadesDePokemon(){
        String pokemonBuscado = "Pokemon4";

        assertEquals(Arrays.asList("Leer Mentes"), pokemonesDB.getPokemonFromDB(pokemonBuscado).getHabilidades());
    }
    @Test
    public void obtenerEvolucionesDePokemon(){
        String pokemonBuscado = "Pokemon5";
        Pokemon pokemonObtenido = pokemonesDB.getPokemonFromDB(pokemonBuscado);
        Pokemon pokemonEsperado = new Pokemon("Pokemon4", Arrays.asList("Psiquico"), 1, Arrays.asList("Leer Mentes"), Arrays.asList(), null);

        assertEquals(1, pokemonesDB.getPokemonFromDB(pokemonBuscado).getEvoluciones().size());
    }
    @Test(expected = PokemonNoEncontradoException.class)
    public void pokemonInexistenteDevuelveExcepcion(){
        String pokemonBuscado = "Pikachu";

        pokemonesDB.getPokemonFromDB(pokemonBuscado);
    }

    //Testing obtención de evoluciones
    @Test
    public void obtenerInformacionDeUnaUnicaEvolucion(){
        String pokemonBuscado = "Pokemon7";
        Pokemon pokemonEncontrado = pokemonesDB.getPokemonFromDB(pokemonBuscado);
        Pokemon evolucionPokemon = pokemonEncontrado.getEvoluciones().get(0);

        assertEquals("Pokemon6", evolucionPokemon.getNombre());
        assertEquals(Arrays.asList("Tierra"), evolucionPokemon.getTipos());
        assertEquals(8, evolucionPokemon.getNivel());
    }
    @Test
    public void obtenerInformacionDeMultiplesEvoluciones(){
        String pokemonBuscado = "Pokemon10";
        Pokemon pokemonEncontrado = pokemonesDB.getPokemonFromDB(pokemonBuscado);
        List<Pokemon> evolucionesPokemon = pokemonEncontrado.getEvoluciones();

        evolucionesPokemon.forEach(
                pokemon -> {
                    assertTrue(Arrays.asList("Pokemon7", "Pokemon8", "Pokemon9").contains(pokemon.getNombre()));
                    assertTrue(Arrays.asList(Arrays.asList("Tierra", "Volador", "Psiquico"), Arrays.asList("Volador"), Arrays.asList("Psiquico")).contains(pokemon.getTipos()));
                    assertTrue(Arrays.asList(2, 2, 1).contains(pokemon.getNivel()));
                }
        );
    }

    //Testing creación de pokemones
    @Test
    public void creacionDeNuevoPokemon(){
        Pokemon pokemon11 = new Pokemon("Pokemon11", Arrays.asList("Desconocido"), 12, Arrays.asList("Destruir planetas"), Arrays.asList(pokemonesDB.getPokemonFromDB("Pokemon10")), null);

        pokemonesDB.actualizarPokemones();

        assertEquals(pokemon11.getNombre(), pokemonesDB.getPokemonFromDB("Pokemon11").getNombre());
    }
    @Test(expected = NoSeExpecificoTipoDePokemonException.class)
    public void noPuedoCrearPokemonSinTipo(){
        BorradorPokemon creadorPokemon11 = new BorradorPokemon("Pokemon11");
        creadorPokemon11.setearNivel(12);
        Pokemon pokemon11 = creadorPokemon11.crearPokemon();
    }

    //Testing modificación de pokemones
    @Test
    public void actualizacionDePokemon(){
        Pokemon pokemon3 = pokemonesDB.getPokemonFromDB("Pokemon3");
        pokemon3.nivel = 6;
        pokemon3.habilidades.add("Planear durante 30 segundos");

        pokemonesDB.actualizarPokemones();

        assertEquals(pokemon3.getNivel(), pokemonesDB.getPokemonFromDB("Pokemon3").getNivel());
        assertEquals(pokemon3.getHabilidades(), pokemonesDB.getPokemonFromDB("Pokemon3").getHabilidades());
    }
}
