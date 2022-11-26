

package controller;

public interface IInmuebleController {

    public String listar(boolean ordenar, String orden);
    
    public String devolver(int id, String username);
    
    public String sumarCantidad(int id);

}