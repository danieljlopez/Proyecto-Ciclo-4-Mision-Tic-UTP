

package controller;

import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
//import java.util.Date;
import java.util.List;

import com.google.gson.Gson;
import beans.Inmueble;
import connection.DBConnection;
import java.sql.Date;


public class InmuebleController implements IInmuebleController {

    @Override
    public String listar(boolean ordenar, String orden) {

        Gson gson = new Gson();

        DBConnection con = new DBConnection();
        String sql = "Select * from inmueble";

        if (ordenar == true) {
            sql += " order by genero " + orden;
        }

        List<String> inmuebles = new ArrayList<String>();

        try {

            Statement st = con.getConnection().createStatement();
            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {

                int idInmueble = rs.getInt("id_inmueble");
                String tipoInmueble = rs.getString("tipo_inmueble");
                String ciudadInmueble = rs.getString("ciudad_inmueble");
                String zonaInmueble = rs.getString("zona_inmueble");
                String descripcionInmueble = rs.getString("descripcion_inmueble");
                int precioInmueble = rs.getInt("precio_inmueble");
                boolean disponibleInmueble = rs.getBoolean("disponible_inmueble");
                Date reservaCita = rs.getDate("reserva_cita");

                Inmueble inmueble = new Inmueble(idInmueble, tipoInmueble, ciudadInmueble, zonaInmueble, descripcionInmueble, precioInmueble, disponibleInmueble, reservaCita);
                
                

                inmuebles.add(gson.toJson(inmueble));
                
                
                
            }
            
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        } finally {
            con.desconectar();
        }

        return gson.toJson(inmuebles);

    }
    
    @Override
    public String devolver(int idUsuario, String usuario){
        DBConnection con = new DBConnection();
        String sql = "Delete from contrato where id_usuario= " + idUsuario + " and usuario = '"
                + usuario + "' limit 1";
        
        try {
            Statement st = con.getConnection().createStatement();
            st.executeQuery(sql);
            
            this.sumarCantidad(idUsuario);
            
            return "true";
        } catch (Exception ex) {
            System.out.println(ex.toString());
        } finally {
            con.desconectar();
        }
        
        return "false";
    }

    @Override
    public String sumarCantidad(int idUsuario){
        DBConnection con = new DBConnection();
        
        String sql = "Update inmueble set copias = (Select copias from inmueble where id_usuario = "
                + idUsuario + ") + 1 where id_usuario = " + idUsuario;
        
        try {
            Statement st = con.getConnection().createStatement();
            st.executeUpdate(sql);
            return "true";
        } catch (Exception ex) {
            System.out.println(ex.toString());
        } finally {
            con.desconectar();
        }
        return "false";
    }

    


}
