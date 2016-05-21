/*
 * Página de controle da página de Histórico
 */
package controller;

import dao.historicoDAO;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Página de controle da página de Histórico
 * 
 * @author GRA (Anne, Gilmar Ricardo)
 */
@WebServlet(name = "Historico", urlPatterns = {"/historico", "/historico/remover"})
public class Historico extends HttpServlet {
    
    /**
     * Constante para ação de remoção de histórico
     */
    public final static String ACTION_REMOVE = "remove";
    
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        
        try {
            historicoDAO dao = new historicoDAO();
            if ( request.getParameter("action") != null && request.getParameter("action").equals(ACTION_REMOVE) && request.getParameter("idx") != null ) {
                int id = Integer.parseInt(request.getParameter("idx"));
                dao.excluir(id);
                response.sendRedirect(request.getRequestURL().toString());
            }
           
            request.setAttribute("historico", dao.consultarTodos());
            request.getRequestDispatcher("historico.jsp").include(request, response);
        } 
        finally {
            out.close();
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }
}
