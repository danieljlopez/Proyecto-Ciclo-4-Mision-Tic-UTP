
package controller;

import java.sql.ResultSet;
import java.sql.Statement;
import com.google.gson.Gson;
import beans.Usuario;
import connection.DBConnection;
import java.util.HashMap;
import java.util.Map;

public class UsuarioController implements IUsuarioController{
    
    @Override
    public String login(String usuario, String contrasena){
        
        Gson gson = new Gson();
        DBConnection con = new DBConnection();
        String sql = "SELECT * FROM usuario WHERE username ='"+ usuario
                + "' and contrasena = '" + contrasena + "'";
        
        try {
            Statement st = con.getConnection().createStatement();
            ResultSet rs = st.executeQuery(sql);
            
            while(rs.next()) {
                int idUsuario = rs.getInt("id_usuario");
                String nombre = rs.getString("nombre");
                String apellido = rs.getString("apellido");
                int telefono = rs.getInt("telefono");
                String email = rs.getString("email");
                String ciudad = rs.getString("ciudad");
               
                Usuario user = new Usuario(idUsuario, usuario, contrasena, nombre, apellido, telefono, email, ciudad);
                return gson.toJson(user);
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        finally{
            con.desconectar();
        }
        
        return "false";
    }
    
    @Override
    public String register(int idUsuario, String usuario, String contrasena, String nombre, String apellido, int telefono, String email,  String ciudad){
        
        Gson gson = new Gson();
        
        DBConnection con = new DBConnection();
        String sql = "Insert into usuario values(" + idUsuario + ", '" + usuario + "', '" + contrasena + "', '" + nombre
                + "', '" + apellido + "'," + telefono + ", '" + email + "',  '" + ciudad + "')";
        
        try {
            Statement st = con.getConnection().createStatement();
            st.executeUpdate(sql);
            
            Usuario user = new Usuario(idUsuario, usuario, contrasena, nombre, apellido, telefono, email, ciudad);
            
            st.close();
            
            return gson.toJson(user);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        finally{
            con.desconectar();
        }
        
        return "false";
    }
    
    @Override
    public String pedir(String usuario) {

        Gson gson = new Gson();

        DBConnection con = new DBConnection();
        String sql = "Select * from usuario where username = '" + usuario + "'";

        try {

            Statement st = con.getConnection().createStatement();
            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {
                int idUsuario = rs.getInt("id_usuario");
                String contrasena = rs.getString("contrasena");
                String nombre = rs.getString("nombre");
                String apellido = rs.getString("apellido");
                int telefono = rs.getInt("telefono");
                String email = rs.getString("email");
                String ciudad = rs.getString("ciudad");

                Usuario user = new Usuario(idUsuario, usuario, contrasena, nombre, apellido, telefono, email, ciudad);

                return gson.toJson(user);
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        } finally {
            con.desconectar();
        }

        return "false";
    }
    
    @Override 
    public String modificar(int idUsuario, String usuario, String nuevaContrasena, String nuevoNombre, String nuevoApellido,  int nuevoTelefono, String nuevoEmail, String nuevaCiudad){
        
        DBConnection con = new DBConnection();
        
        String sql = "Update usuario set id_usuario = " + idUsuario + ", contrasena = '" + nuevaContrasena  + "', nombre = '" + nuevoNombre + "', "
                    + "apellido = '" + nuevoApellido + "', telefono = " + nuevoTelefono+", email = '"
                    + nuevoEmail + "',ciudad = '" + nuevaCiudad + "'";
        
        /*if(nuevoPremium == true) {
            sql += " 1 ";
        }else{
            sql += " 0 ";
        }*/
        
        sql += " where username = '" + usuario + "'";
        
        try {
            Statement st = con.getConnection().createStatement();
            st.executeUpdate(sql);
            
            return "true";
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        } finally {
            con.desconectar();
        }
        return "false";
    }
    
    @Override
    public String verCopias(String usuario){
       DBConnection con = new DBConnection();
       String sql = "Select id_usuario,count(*) as num_copias from contrato where id_usuario = '"
               + usuario + "' group by id_usuario;";
       
       Map<Integer, Integer> copias = new HashMap<Integer, Integer>();
       
        try {
            Statement st = con.getConnection().createStatement();
            ResultSet rs = st.executeQuery(sql);
            
            while(rs.next()){
                int idUsuario = rs.getInt("id_usuario");
                int num_copias = rs.getInt("num_copias");
                
                copias.put(idUsuario, num_copias);
            }
            
            devolverInmuebles(usuario, copias);
            return "true";
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        } finally {
            con.desconectar();
        }
        return "false";
    }
    public String devolverInmuebles(String usuario, Map<Integer, Integer> copias){
        
        DBConnection con = new DBConnection();
        
        try {
            for (Map.Entry<Integer, Integer> inmueble : copias.entrySet()) {
                int idUsuario = inmueble.getKey();
                int num_copias = inmueble.getValue();
                
                String sql = "Update inmueble set copias = (Select copias + " + num_copias + " from inmueble where id_usuario = " + idUsuario + ") where id_usuario = " + idUsuario;
                
                Statement st = con.getConnection().createStatement();
                st.executeUpdate(sql);
            }
            this.eliminar(usuario);
        }catch(Exception ex){
            System.out.println(ex.getMessage());
        }finally{
            con.desconectar();
        }
        return "false";
    }
    
    
    public String eliminar(String username){
        
        DBConnection con = new DBConnection();
        
        
        String sql = "Delete from usuario where username  = '" + username + "'";
        
        try {
            Statement st = con.getConnection().createStatement();
           
            st.executeUpdate(sql);
            return "true";
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        } finally {
            con.desconectar();
        }
        return "false";
    }
    
}
