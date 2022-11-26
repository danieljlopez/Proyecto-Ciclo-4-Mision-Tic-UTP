
package controller;

import java.util.Map;

public interface IUsuarioController {
    
    public String login(String usuario, String contrasena);
    
    public String register(int idUsuario, String usuario, String contrasena, String nombre, String apellido, int telefono, String email,  String ciudad);
    
    public String pedir(String usuario);
    
    public String modificar(int idUsuario, String usuario, String nuevaContrasena, String nuevoNombre, String nuevoApellido,  int nuevoTelefono, String nuevoEmail, String nuevaCiudad);
    
    public String verCopias(String usuario);
    
    public String devolverInmuebles(String usuario, Map<Integer, Integer> copias);
    
    public String eliminar(String usuario);
    
    
  
    
    
    
}
