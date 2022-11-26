

    
package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.InmuebleController;


@WebServlet("/ServletInmuebleDevolver")
public class ServletInmuebleDevolver extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public ServletInmuebleDevolver() {
        super();
    }
    
    

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        InmuebleController pelicula = new InmuebleController();
        
        String usuario = request.getParameter("usuario");
        int idUsuario = Integer.parseInt(request.getParameter("idUsuario"));
        
        String libroStr = pelicula.devolver(idUsuario, usuario);
        
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
	out.println(libroStr);
	out.flush();
	out.close();
        
        
    }

    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

    

}
