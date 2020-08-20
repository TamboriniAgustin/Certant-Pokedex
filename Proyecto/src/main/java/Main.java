import BaseDeDatos.BaseDeDatos;
import Pokemon.Pokemon;
import Pokemon.RepositorioPokemones;
import Pokemon.BorradorPokemon;
import Usuario.BorradorUsuario;
import Usuario.Usuario;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    private static final RepositorioPokemones repositorioPokemones = inicializarRepositorioPokemones();
    private static final BaseDeDatos baseDeDatos = new BaseDeDatos();

    public static void main(String[] args) throws IOException {
        System.out.println("******************************** POKEDEX ********************************\n");
        abrirMenuPrincipal();
    }

    private static RepositorioPokemones inicializarRepositorioPokemones(){
        return RepositorioPokemones.inicializar();
    }
    private static void abrirMenuPrincipal() throws IOException {
        System.out.println("Bienvenido a Pokedex, seleccione una de las siguientes acciones para continuar");

        int opcionElegida;
        do {
            System.out.println("1_ Ver lista global de pokemones");
            System.out.println("2_ Registrar un nuevo pokemon");
            System.out.println("3_ Crear una cuenta de entrenador");
            System.out.println("4_ Ingresar como entrenador\n");
            System.out.println("Seleccione una opción: ");
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            opcionElegida = Integer.parseInt(reader.readLine());

            if(opcionElegida == 1) obtenerPokemones();
            else if(opcionElegida == 2) crearPokemon();
            else if(opcionElegida == 3) crearCuenta();
            else if(opcionElegida == 4) ingresarAlSistema();
            else System.out.println("La opción elegida no está disponible\n");
        } while(opcionElegida <= 0 || opcionElegida >= 4);
    }
    private static void obtenerPokemones() {
        System.out.println("\n\n\n********** LISTA GLOBAL DE POKEMONES **********");
        repositorioPokemones.getPokemonesFromDB().forEach(
                pokemon -> {
                    System.out.println("Nombre: " + pokemon.getNombre());
                    System.out.println("Nivel: " + pokemon.getNivel());
                    System.out.println("Dueño: " + pokemon.getDuenio().getNombre());

                    System.out.println("Tipos: ");
                    pokemon.getTipos().forEach(
                            tipo -> System.out.println("- " + tipo)
                    );

                    System.out.println("Habilidades: ");
                    pokemon.getHabilidades().forEach(
                            habilidad -> System.out.println("- " + habilidad)
                    );

                    System.out.println("Evoluciones: ");
                    pokemon.getEvoluciones().forEach(
                            evolucion -> System.out.println("- " + evolucion.getNombre())
                    );

                    System.out.println("------------------------------------------------");
                }
        );
    }
    private static void crearPokemon() throws IOException {
        System.out.println("\n\n\n********** CREACIÓN DE POKEMON **********");
        int opcionElegida;
        BufferedReader readerOption;

        //Primer paso: setear nombre
        System.out.println("Nombre de pokemon: ");
        BufferedReader readerName = new BufferedReader(new InputStreamReader(System.in));
        String nombrePokemon = readerName.readLine();

        //Segundo paso: creo el borrador de un pokemon
        BorradorPokemon creacionPokemon = new BorradorPokemon(nombrePokemon);

        //Tercer paso: asigno el nivel del pokemon
        System.out.println("Nivel de pokemon: ");
        BufferedReader readerLevel = new BufferedReader(new InputStreamReader(System.in));
        int nivelPokemon = Integer.parseInt(readerLevel.readLine());

        creacionPokemon.setearNivel(nivelPokemon);

        //Cuarto paso: asigno los tipos del pokemon
        String tipo;
        do {
            System.out.println("Tipo: ");
            BufferedReader readerType = new BufferedReader(new InputStreamReader(System.in));
            tipo = readerType.readLine();

            creacionPokemon.agregarTipo(tipo);

            System.out.println("Selecciona 1 para añadir otro tipo\nSelecciona 2 para pasar al siguiente paso");
            readerOption = new BufferedReader(new InputStreamReader(System.in));
            opcionElegida = Integer.parseInt(readerOption.readLine());
        } while(opcionElegida != 2);

        //Quinto paso: asigno las habilidades del pokemon
        String habilidad;
        opcionElegida = 0;
        do {
            if(opcionElegida > 0){
                System.out.println("Habilidad: ");
                BufferedReader readerType = new BufferedReader(new InputStreamReader(System.in));
                habilidad = readerType.readLine();

                creacionPokemon.agregarHabilidad(habilidad);
            }

            System.out.println("Selecciona 1 para añadir otra habilidad\nSelecciona 2 para pasar al siguiente paso");
            readerOption = new BufferedReader(new InputStreamReader(System.in));
            opcionElegida = Integer.parseInt(readerOption.readLine());
        } while(opcionElegida != 2);

        //Sexto paso: asigno las evoluciones del pokemon
        Pokemon evolucion;
        opcionElegida = 0;
        do {
            if(opcionElegida > 0) {
                System.out.println("Nombre de Evolución: ");
                BufferedReader readerType = new BufferedReader(new InputStreamReader(System.in));
                evolucion = repositorioPokemones.getPokemonFromDB(readerType.readLine());

                creacionPokemon.nuevaEvolucion(evolucion);
            }

            System.out.println("Selecciona 1 para añadir otra evolucion\nSelecciona 2 para pasar al siguiente paso");
            readerOption = new BufferedReader(new InputStreamReader(System.in));
            opcionElegida = Integer.parseInt(readerOption.readLine());
        } while(opcionElegida != 2);

        //Séptimo paso: genero el pokemon
        creacionPokemon.crearPokemon();
        repositorioPokemones.actualizarPokemones();
    }
    private static void crearCuenta() throws IOException {
        System.out.println("\n\n\n********** INGRESAR TUS DATOS **********");

        System.out.println("Nombre de usuario: ");
        BufferedReader readerUser = new BufferedReader(new InputStreamReader(System.in));
        String nombreUsuario = readerUser.readLine();

        System.out.println("Contraseña: ");
        BufferedReader readerPassword = new BufferedReader(new InputStreamReader(System.in));
        String password = readerPassword.readLine();

        new BorradorUsuario(nombreUsuario, password).crearUsuario();
    }
    private static void ingresarAlSistema() throws IOException {
        System.out.println("\n\n\n********** INGRESAR TUS DATOS **********");

        System.out.println("Nombre de usuario: ");
        BufferedReader readerUser = new BufferedReader(new InputStreamReader(System.in));
        String nombreUsuario = readerUser.readLine();

        System.out.println("Contraseña: ");
        BufferedReader readerPassword = new BufferedReader(new InputStreamReader(System.in));
        String password = readerPassword.readLine();

        if(baseDeDatos.getUsuario(nombreUsuario, password)) abrirMenuUsuario(new Usuario(nombreUsuario, password));
        else System.out.println("El usuario ingresado no se ha encontrado en la base de datos");
    }
    private static void abrirMenuUsuario(Usuario usuario) throws IOException {
        System.out.println("Bienvenido al panel de usuario, seleccione una de las siguientes acciones para continuar");

        int opcionElegida;
        do {
            System.out.println("1_ Ver mis pokemones");
            System.out.println("2_ Atrapar un pokemon");
            System.out.println("3_ Modificar un pokemon de mi pertenencia");
            System.out.println("4_ Salir\n");

            System.out.println("Seleccione una opción: ");
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            opcionElegida = Integer.parseInt(reader.readLine());

            if(opcionElegida == 1) obtenerPokemonesUsuario(usuario);
            else if(opcionElegida == 2) agarrarUnPokemon(usuario);
            else if(opcionElegida == 3) modificarUnPokemon(usuario);
            else System.out.println("La opción elegida no está disponible\n");
        } while(opcionElegida != 4);
    }
    private static void obtenerPokemonesUsuario(Usuario usuario){
        System.out.println("\n\n\n********** LISTA DE MIS POKEMONES **********");
        repositorioPokemones.getPokemonesFromUsuario(usuario).forEach(
                pokemon -> {
                    System.out.println("Nombre: " + pokemon.getNombre());
                    System.out.println("Nivel: " + pokemon.getNivel());
                    System.out.println("Dueño: " + pokemon.getDuenio().getNombre());

                    System.out.println("Tipos: ");
                    pokemon.getTipos().forEach(
                            tipo -> System.out.println("- " + tipo)
                    );

                    System.out.println("Habilidades: ");
                    pokemon.getHabilidades().forEach(
                            habilidad -> System.out.println("- " + habilidad)
                    );

                    System.out.println("Evoluciones: ");
                    pokemon.getEvoluciones().forEach(
                            evolucion -> System.out.println("- " + evolucion.getNombre())
                    );

                    System.out.println("------------------------------------------------");
                }
        );
    }
    private static void agarrarUnPokemon(Usuario usuario) throws IOException {
        System.out.println("\n\n\n********** AGARRAR UN NUEVO POKEMON **********");

        System.out.println("Nombre del Pokemon: ");
        BufferedReader readerUser = new BufferedReader(new InputStreamReader(System.in));
        String nombrePokemon = readerUser.readLine();
        Pokemon pokemon = repositorioPokemones.getPokemonFromDB(nombrePokemon);

        pokemon.nuevoDuenio(usuario);
        repositorioPokemones.actualizarPokemones();
    }
    private static void modificarUnPokemon(Usuario usuario) throws IOException {
        System.out.println("\n\n\n********** MODIFICAR UN POKEMON **********");

        System.out.println("Nombre del Pokemon: ");
        BufferedReader readerUser = new BufferedReader(new InputStreamReader(System.in));
        String nombrePokemon = readerUser.readLine();

        Pokemon pokemon = repositorioPokemones.getPokemonFromDB(nombrePokemon);
        if(pokemon.getDuenio().getNombre().equals(usuario.getNombre())){
            System.out.println("Opciones de modificación:");
            System.out.println("1_ Modificar nivel");
            System.out.println("2_ Insertar/Eliminar un tipo");
            System.out.println("3_ Insertar/Eliminar una habilidad");
            System.out.println("4_ Insertar/Eliminar una evolución");

            int opcionElegida;
            do {
                System.out.println("Seleccione una opción: ");
                BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
                opcionElegida = Integer.parseInt(reader.readLine());

                if(opcionElegida == 1) modificarNivelPokemon(pokemon);
                else if(opcionElegida == 2) modificarTiposPokemon(pokemon);
                else if(opcionElegida == 3) modificarHabilidadesPokemon(pokemon);
                else if(opcionElegida == 4) modificarEvolucionesPokemon(pokemon);
                else System.out.println("La opción elegida no está disponible\n");
            } while(opcionElegida <= 0 || opcionElegida >= 6);
        }
        else System.out.println("El pokemon no es de tu pertenencia. No puedes modificarlo.");
    }
    private static void modificarNivelPokemon(Pokemon pokemon) throws IOException {
        System.out.println("\n\n\n********** MODIFICAR NIVEL DE POKEMON **********");

        System.out.println("Nivel del Pokemon: ");
        BufferedReader readerUser = new BufferedReader(new InputStreamReader(System.in));
        int nivelPokemon = Integer.parseInt(readerUser.readLine());

        pokemon.modificarNivel(nivelPokemon);
        repositorioPokemones.actualizarPokemones();
    }
    private static void modificarTiposPokemon(Pokemon pokemon) throws IOException {
        System.out.println("\n\n\n********** ADMINISTRACIÓN DE TIPOS DE POKEMON **********");

        int accionElegida;
        int opcionElegida;

        do {
            System.out.println("Selecciona 1 para añadir un tipo\nSelecciona 2 para eliminar un tipo");
            BufferedReader readerUser = new BufferedReader(new InputStreamReader(System.in));
            accionElegida = Integer.parseInt(readerUser.readLine());

            String tipo;
            if(accionElegida == 1){
                System.out.println("Tipo: ");
                BufferedReader readerType = new BufferedReader(new InputStreamReader(System.in));
                tipo = readerType.readLine();

                pokemon.agregarTipo(tipo);
            }
            else if(accionElegida == 2){
                System.out.println("Tipo: ");
                BufferedReader readerType = new BufferedReader(new InputStreamReader(System.in));
                tipo = readerType.readLine();

                pokemon.removerTipo(tipo);
            }

            System.out.println("\nSelecciona 1 para repetir\nSelecciona 2 para salir");
            BufferedReader readerOption = new BufferedReader(new InputStreamReader(System.in));
            opcionElegida = Integer.parseInt(readerOption.readLine());
        } while(opcionElegida != 2);

        repositorioPokemones.actualizarPokemones();
    }
    private static void modificarHabilidadesPokemon(Pokemon pokemon) throws IOException {
        System.out.println("\n\n\n********** ADMINISTRACIÓN DE HABILIDADES DE POKEMON **********");

        int accionElegida;
        int opcionElegida;

        do {
            System.out.println("Selecciona 1 para añadir una habilidad\nSelecciona 2 para eliminar una habilidad");
            BufferedReader readerUser = new BufferedReader(new InputStreamReader(System.in));
            accionElegida = Integer.parseInt(readerUser.readLine());

            String habilidad;
            if(accionElegida == 1){
                System.out.println("Habilidad: ");
                BufferedReader readerHability = new BufferedReader(new InputStreamReader(System.in));
                habilidad = readerHability.readLine();

                pokemon.agregarHabilidad(habilidad);
            }
            else if(accionElegida == 2){
                System.out.println("Habilidad: ");
                BufferedReader readerHability = new BufferedReader(new InputStreamReader(System.in));
                habilidad = readerHability.readLine();

                pokemon.removerHabilidad(habilidad);
            }

            System.out.println("\nSelecciona 1 para repetir\nSelecciona 2 para salir");
            BufferedReader readerOption = new BufferedReader(new InputStreamReader(System.in));
            opcionElegida = Integer.parseInt(readerOption.readLine());
        } while(opcionElegida != 2);

        repositorioPokemones.actualizarPokemones();
    }
    private static void modificarEvolucionesPokemon(Pokemon pokemon) throws IOException {
        System.out.println("********** ADMINISTRACIÓN DE EVOLUCIONES DE POKEMON **********\n");

        int accionElegida;
        int opcionElegida;

        do {
            System.out.println("Selecciona 1 para añadir una evolucion\nSelecciona 2 para eliminar una evolucion");
            BufferedReader readerUser = new BufferedReader(new InputStreamReader(System.in));
            accionElegida = Integer.parseInt(readerUser.readLine());

            Pokemon evolucion;
            if(accionElegida == 1){
                System.out.println("Nombre de la Evolucion: ");
                BufferedReader readerHability = new BufferedReader(new InputStreamReader(System.in));
                evolucion = repositorioPokemones.getPokemonFromDB(readerHability.readLine());

                pokemon.nuevaEvolucion(evolucion);
            }
            else if(accionElegida == 2){
                System.out.println("Nombre de la Evolucion: ");
                BufferedReader readerHability = new BufferedReader(new InputStreamReader(System.in));
                evolucion = repositorioPokemones.getPokemonFromDB(readerHability.readLine());

                pokemon.eliminarEvolucion(evolucion);
            }

            System.out.println("\nSelecciona 1 para repetir\nSelecciona 2 para salir");
            BufferedReader readerOption = new BufferedReader(new InputStreamReader(System.in));
            opcionElegida = Integer.parseInt(readerOption.readLine());
        } while(opcionElegida != 2);

        repositorioPokemones.actualizarPokemones();
    }
}