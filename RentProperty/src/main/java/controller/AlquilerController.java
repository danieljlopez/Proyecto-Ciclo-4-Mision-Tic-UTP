
package controller;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import java.util.List;

import com.google.gson.Gson;

import beans.Contrato;
import connection.DBConnection;


public class AlquilerController implements IAlquilerController {
    @Override
    public String listarAlquileres(String usuario){
        Gson gson = new Gson();
        
        DBConnection con = new DBConnection();
        
        String sql = "Select l.id_suario, l.id_inmueble, l.id_contrato, a.fecha_contrato from inmueble l"
                + "inner join contrato a on l.id_suario = a.id_suario inner join usuario u on a.usuario = u.usuario"
                + "where a.usuario = '" + usuario + "'";
        List<String> alquileres = new ArrayList<String>();
        
        try {
            Statement st = con.getConnection().createStatement();
            ResultSet rs = st.executeQuery(sql);
            
            while(rs.next()) {
                int idUsuario = rs.getInt("id_suario");
                int idInmueble = rs.getInt("id_inmueble");
                int idContrato = rs.getInt("id_contrato");
                Date fechaContrato = rs.getDate("fecha_contrato");
                
                Contrato alquiler = new Contrato(idUsuario, idInmueble, idContrato, fechaContrato);
                
                alquileres.add(gson.toJson(alquiler));
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        } finally {
            con.desconectar();
        }
        return gson.toJson(alquileres);
    }
    
    
}
