
package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.UsuarioController;


@WebServlet("/ServletUsuarioEliminar")
public class ServletUsuarioEliminar extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public ServletUsuarioEliminar() {
        super();
    }  

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        UsuarioController user = new UsuarioController();
        
        String usuario = request.getParameter("usuario");
        
        String usuarioStr = user.verCopias(usuario);
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
	out.println(usuarioStr);
	out.flush();
	out.close();
    }

    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

    

}
