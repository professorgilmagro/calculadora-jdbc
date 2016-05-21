/*
 * Página de controle da página de Histórico
 */
package controller;

import dao.historicoDAO;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Historic;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Página de controle da página de Histórico
 * 
 * @author GRA (Anne, Gilmar Ricardo)
 */
@WebServlet(name = "Historico", urlPatterns = {"/historico", "/historico/remover","/historico/alterar"})
public class Historico extends HttpServlet {
    
    /**
     * Constante para ação para CRUD
     */
    public final static String ACTION_DELETE = "remove";
    public final static String ACTION_UPDATE = "update";
    
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
            if ( request.getParameter("action") != null && request.getParameter("action").equals(ACTION_DELETE) && request.getParameter("idx") != null ) {
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
            
        response.setCharacterEncoding("UTF-8");
        if ( request.getParameter("action") != null && request.getParameter("action").equals(ACTION_UPDATE)) {
            response.setContentType("application/json");
            JSONObject json = new JSONObject();
            PrintWriter out = response.getWriter();
            try {
                historicoDAO dao = new historicoDAO();
                Historic historico = new Historic();
                int id = Integer.parseInt(request.getParameter("historicoID"));
                BigDecimal vlrDecimal = new BigDecimal(request.getParameter("valorDecimal"));
                historico.setId(id);
                historico.setExpressao(request.getParameter("expressao"));
                historico.setResultado(request.getParameter("resultado"));
                historico.setSimplificado(request.getParameter("simplificado"));
                historico.setClassificacao(request.getParameter("classificacao"));
                historico.setDecimal(vlrDecimal);

                dao.atualizar(historico);
                json.put("success", true);
            } catch (Exception e) {
                try {
                    json.put("success", false);
                } catch (JSONException ex) {
                    Logger.getLogger(Historico.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
            out.write(json.toString());
        }
    }
}
