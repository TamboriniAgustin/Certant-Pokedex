package BaseDeDatos;

import Excepciones.NoPudoEjecutarseLaConsultaException;
import Excepciones.NoSePudoConectarConLaBaseDeDatosException;
import Pokemon.Pokemon;
import Usuario.Usuario;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class BaseDeDatos {
    Connection connection;

    public BaseDeDatos(){
        try {
            String connectionUrl = "jdbc:sqlserver://;database=Pokedex;integratedSecurity=true;";
            this.connection = DriverManager.getConnection(connectionUrl);
        }
        catch(Exception unaExcepcion) {
            throw new NoSePudoConectarConLaBaseDeDatosException();
        }
    }

    public boolean getUsuario(String nombre, String password){
        boolean estadoLogin = false;

        try {
            String sql = "SELECT * FROM usuario WHERE usuario_nombre = " + nombre + " AND usuario_password = " + password;
            Statement statement = connection.createStatement();
            ResultSet resultados = statement.executeQuery(sql);
            while(resultados.next()) {
                estadoLogin = true;
            }
        } catch(Exception unaExcepcion){
            throw new NoPudoEjecutarseLaConsultaException();
        }

        return estadoLogin;
    }
    public void insertarUsuario(Usuario usuario){
        String nombre = usuario.getNombre();
        String password = usuario.getPassword();

        try{
            String sql = "INSERT INTO usuario(usuario_nombre, usuario_password) VALUES(" + nombre + ", " + password + ")";
            Statement statement = connection.createStatement();
            statement.executeUpdate(sql);
        } catch (Exception unaExcepcion){
            throw new NoPudoEjecutarseLaConsultaException();
        }
    }

    public List<Pokemon> getPokemones(){
        List<Pokemon> pokemonesObtenidos = new ArrayList();

        try {
            String sql = "SELECT pokemon_nombre, pokemon_nivel, usuario_nombre, usuario_password FROM pokemon p1 FULL JOIN usuario u1 ON p1.pokemon_duenio = u1.usuario_nombre WHERE pokemon_nombre IS NOT NULL";
            Statement statement = connection.createStatement();
            ResultSet resultados = statement.executeQuery(sql);
            while(resultados.next()) {
                String nombrePokemon = resultados.getString("pokemon_nombre");
                int nivelPokemon = resultados.getInt("pokemon_nivel");
                Usuario duenioPokemon = new Usuario(resultados.getString("usuario_nombre"), resultados.getString("usuario_password"));
                List<String> tiposPokemon = this.getTiposPokemon(nombrePokemon);
                List<String> habilidadesPokemon = this.getHabilidadesPokemon(nombrePokemon);
                List<Pokemon> evolucionesPokemon = this.getEvolucionesPokemon(nombrePokemon);

                pokemonesObtenidos.add(new Pokemon(nombrePokemon, tiposPokemon, nivelPokemon, habilidadesPokemon, evolucionesPokemon, duenioPokemon));
            }
        } catch(Exception unaExcepcion){
            throw new NoPudoEjecutarseLaConsultaException();
        }

        return  pokemonesObtenidos;
    }
    private List<String> getTiposPokemon(String pokemon){
        List<String> tipos = new ArrayList();

        try {
            String sql = "SELECT * FROM tipo_pokemon WHERE nombre_pokemon = " + pokemon;
            Statement statement = connection.createStatement();
            ResultSet resultados = statement.executeQuery(sql);
            while(resultados.next()) {
                tipos.add(resultados.getString("tipo_pokemon"));
            }
        } catch(Exception unaExcepcion){
            throw new NoPudoEjecutarseLaConsultaException();
        }

        return tipos;
    }
    private List<String> getHabilidadesPokemon(String pokemon){
        List<String> habilidades = new ArrayList();

        try {
            String sql = "SELECT * FROM habilidad_pokemon WHERE nombre_pokemon = " + pokemon;
            Statement statement = connection.createStatement();
            ResultSet resultados = statement.executeQuery(sql);
            while(resultados.next()) {
                habilidades.add(resultados.getString("habilidad_nombre"));
            }
        } catch(Exception unaExcepcion){
            throw new NoPudoEjecutarseLaConsultaException();
        }

        return habilidades;
    }
    private List<Pokemon> getEvolucionesPokemon(String pokemon){
        List<Pokemon> evolucionesObtenidas = new ArrayList();

        try {
            String sql = "SELECT pokemon_nombre, pokemon_nivel, usuario_nombre, usuario_password FROM evolucion_pokemon ep1 JOIN pokemon p1 ON ep1.nombre_pokemon = p1.pokemon_nombre FULL JOIN usuario u1 ON p1.pokemon_duenio = u1.usuario_nombre WHERE nombre_pokemon = '" + pokemon + "'";
            Statement statement = connection.createStatement();
            ResultSet resultados = statement.executeQuery(sql);
            while(resultados.next()) {
                String nombreEvolucion = resultados.getString("pokemon_nombre");
                int nivelEvolucion = resultados.getInt("pokemon_nivel");
                Usuario duenioEvolucion = new Usuario(resultados.getString("usuario_nombre"), resultados.getString("usuario_password"));
                List<String> tiposEvolucion = this.getTiposPokemon(nombreEvolucion);
                List<String> habilidadesEvolucion = this.getHabilidadesPokemon(nombreEvolucion);
                List<Pokemon> evolucionesEvolucion = this.getEvolucionesPokemon(nombreEvolucion);

                evolucionesObtenidas.add(new Pokemon(nombreEvolucion, tiposEvolucion, nivelEvolucion, habilidadesEvolucion, evolucionesEvolucion, duenioEvolucion));
            }
        } catch(Exception unaExcepcion){
            throw new NoPudoEjecutarseLaConsultaException();
        }

        return  evolucionesObtenidas;
    }
    public void insertarPokemon(Pokemon pokemon){
        String nombre = pokemon.getNombre();
        int nivel = pokemon.getNivel();
        Usuario duenio = pokemon.getDuenio();

        try{
            String sql = "INSERT INTO pokemones(pokemon_nombre, pokemon_nivel, pokemon_duenio) VALUES(" + nombre + ", " + nivel + ", " + duenio.getNombre() + ")";
            Statement statement = connection.createStatement();
            statement.executeUpdate(sql);
        } catch (Exception unaExcepcion){
            throw new NoPudoEjecutarseLaConsultaException();
        }

        List<String> tipos = pokemon.getTipos();
        this.insertarTiposPokemon(nombre, tipos);

        List<String> habilidades = pokemon.getHabilidades();
        this.insertarHabilidadesPokemon(nombre, habilidades);

        List<Pokemon> evoluciones = pokemon.getEvoluciones();
        this.insertarEvolucionesPokemon(nombre, evoluciones);
    }
    public void modificarPokemon(Pokemon pokemon){
        String nombre = pokemon.getNombre();
        int nivel = pokemon.getNivel();
        Usuario duenio = pokemon.getDuenio();

        try{
            String sql = "UPDATE pokemones SET pokemon_nombre = '" + nombre + "', pokemon_nivel = " + nivel + ", pokemon_duenio = " + duenio.getNombre() + " WHERE pokemon_nombre = '" + nombre + "'";
            Statement statement = connection.createStatement();
            statement.executeUpdate(sql);
        } catch (Exception unaExcepcion){
            throw new NoPudoEjecutarseLaConsultaException();
        }
    }

    public void insertarTiposPokemon(String pokemon, List<String> tipos){
        tipos.forEach(
                tipo -> {
                    try{
                        String sql = "INSERT INTO tipo_pokemon(tipo_pokemon, nombre_pokemon) VALUES(" + tipo + ", " + pokemon + ")";
                        Statement statement = connection.createStatement();
                        statement.executeUpdate(sql);
                    } catch (Exception unaExcepcion){
                        throw new NoPudoEjecutarseLaConsultaException();
                    }
                }
        );
    }
    public void eliminarTipoPokemon(String pokemon, String tipo){
        try{
            String sql = "DELETE FROM tipo_pokemon WHERE nombre_pokemon = '" + pokemon + "' AND tipo_pokemon = '" + tipo + "'";
            Statement statement = connection.createStatement();
            statement.executeUpdate(sql);
        } catch (Exception unaExcepcion){
            throw new NoPudoEjecutarseLaConsultaException();
        }
    }

    public void insertarHabilidadesPokemon(String pokemon, List<String> habilidades){
        habilidades.forEach(
                habilidad -> {
                    try{
                        String sql = "INSERT INTO habilidad_pokemon(habilidad_nombre, nombre_pokemon) VALUES(" + habilidad + ", " + pokemon + ")";
                        Statement statement = connection.createStatement();
                        statement.executeUpdate(sql);
                    } catch (Exception unaExcepcion){
                        throw new NoPudoEjecutarseLaConsultaException();
                    }
                }
        );
    }
    public void eliminarHabilidadPokemon(String pokemon, String habilidad){
        try{
            String sql = "DELETE FROM habilidad_pokemon WHERE nombre_pokemon = '" + pokemon + "' AND habilidad_nombre = '" + habilidad + "'";
            Statement statement = connection.createStatement();
            statement.executeUpdate(sql);
        } catch (Exception unaExcepcion){
            throw new NoPudoEjecutarseLaConsultaException();
        }
    }

    public void insertarEvolucionesPokemon(String pokemon, List<Pokemon> evoluciones){
        evoluciones.forEach(
                evolucion -> {
                    String nombre = evolucion.getNombre();

                    try{
                        String sql = "INSERT INTO evolucion_pokemon(nombre_evolucion, nombre_pokemon) VALUES(" + nombre + ", " + pokemon + ")";
                        Statement statement = connection.createStatement();
                        statement.executeUpdate(sql);
                    } catch (Exception unaExcepcion){
                        throw new NoPudoEjecutarseLaConsultaException();
                    }
                }
        );
    }
    public void eliminarEvolucionPokemon(String pokemon, String evolucion){
        try{
            String sql = "DELETE FROM evolucion_pokemon WHERE nombre_pokemon = '" + pokemon + "' AND nombre_evolucion = '" + evolucion + "'";
            Statement statement = connection.createStatement();
            statement.executeUpdate(sql);
        } catch (Exception unaExcepcion){
            throw new NoPudoEjecutarseLaConsultaException();
        }
    }
}