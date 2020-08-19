package Usuario;

import BaseDeDatos.BaseDeDatos;

public class BorradorUsuario {
    private String nombre;
    private String password;

    public BorradorUsuario(String nombre, String password){
        this.nombre = nombre;
        this.password = password;
    }

    public Usuario crearUsuario(){
        Usuario usuarioGenerado = new Usuario(nombre, password);
        new BaseDeDatos().insertarUsuario(usuarioGenerado);

        return usuarioGenerado;
    }

}
