package controller;

/*
 * Página de controle da página de Calculadora
 */

import dao.historicoDAO;
import model.Fraction;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Historic;
import util.FractionException;

/**
 * Página de controle da Calculadora
 * 
 * @author GRA (Anne, Gilmar Ricardo)
 */
@WebServlet(name = "Calculadora", urlPatterns = {"/calculadora"})
public class Calculadora extends HttpServlet {
    
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
           request.setAttribute("resultado", "");
           request.setAttribute("resultadoSimplificado", "");
           request.setAttribute("resultadoDecimal", "");
           request.setAttribute("tipos", new ArrayList<String>());
           request.setAttribute("historico", dao.consultarUtimos(10));
           request.getRequestDispatcher("calculadora.jsp").forward(request, response);
        } finally {
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
        try {
            response.setContentType("text/html;charset=UTF-8");
            request.setCharacterEncoding("UTF-8");
            String mathText = request.getParameter("mathText");

            Fraction result = this._getResultFromMathTeX(mathText, response);
            
            String simplificado = result.getSimplifiedResult().getPrettyMathResult();
            String resultado = result.getPrettyMathResult() ;
            if ( simplificado.equals(resultado) ) {
                simplificado = "";
            }
        
            /**
             * Neste ponto, vamos persistir os dados em banco
             */
            historicoDAO dao = new historicoDAO();
            dao.inserir(new Historic(result));
        
            request.setAttribute("resultado", resultado);
            request.setAttribute("resultadoSimplificado", simplificado);
            request.setAttribute("resultadoDecimal", result.getDecimalResult());
            request.setAttribute("expressao", result.getMathExpression());
            request.setAttribute("avisos", null);
            request.setAttribute("tipos", result.getTypes());
        }catch( FractionException ex ){
            List<String> avisos = new ArrayList<String>();
            avisos.add(ex.getMessage());
            request.setAttribute("avisos", avisos);
        } catch (Exception ex) {
            Logger.getLogger(Calculadora.class.getName()).log(Level.SEVERE, null, ex);
        }finally {
            request.getRequestDispatcher("calculadora.jsp").forward(request, response);
        }
    }
    
    /**
     * Popula o objeto fracao para os calculos e retorna uma fracao de resultado
     * 
     * @param mathText  Composição textual do cálculo
     * 
     * @return Fraction
     */
    private Fraction _getResultFromMathTeX( String mathText , HttpServletResponse response  ) throws Exception, FractionException{
        String[] fracs = mathText.split("\\+|-|×|÷");
        List<String> operators = new ArrayList<String>();
        
        Matcher m = Pattern.compile("\\+|-|×|÷").matcher(mathText);
        while (m.find()) {
            operators.add(m.group(0));
        }
        
        Fraction mainFrac = null ;
        for (int i = 0; i < fracs.length; i++) {
            String[] numbers = fracs[i].split("/");
            int numerador = Integer.parseInt(numbers[0]);
            int denominador = numbers.length == 2 ? Integer.parseInt(numbers[1]) : 1;

            // cria a fracao que vai gerir o cálculo com a primeira parte da equação
            if ( i == 0 ) {
                mainFrac = new Fraction(numerador, denominador);
                continue;
            }

            mainFrac.add(new Fraction(numerador, denominador, operators.get(i-1)) );
        }
        
        return mainFrac;
    }
}
