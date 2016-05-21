<%@page import="java.util.ArrayList"%>
<form id="fraction_calculator" method="post" class="calculator" action="<%= request.getContextPath() %>/calculadora">
    <%
        String resultado = (String) request.getAttribute("resultado");
        String resultadoSimplificado = (String) request.getAttribute("resultadoSimplificado");
        String resultadoDecimal = (String) request.getAttribute("resultadoDecimal");
        ArrayList<String> tipos = (ArrayList<String>) request.getAttribute("tipos");
        ArrayList<String> avisos = (ArrayList<String>) request.getAttribute("avisos");        
        Boolean hasResult = resultado != null && ! resultado.isEmpty() ;
        Boolean hasWarnings = avisos != null && ! avisos.isEmpty() ;
    %>
   <div class="bg-calc">
    <table class="calc-board">
        <caption>
            <a class="active" href="#" >Resultado</a>
        </caption>
        <tbody>
            <tr>
                <td colspan="5">
                    <input name="mathText" id="mathText" type="hidden" value="">
                </td>
            </tr>
            <tr class="visor classification">
                <td colspan="4" class="content <%= tipos == null || tipos.isEmpty() ? "hide" : "" %>">
                <h4>Classificação da fração:</h4>
                    <ul>
                        <%
                        if( tipos != null ) {
                            for (String tipo : tipos) {
                               out.print( "<li>" + tipo + "</li>" );
                            }
                        }
                        %>
                    </ul>
                </td>
            </tr>
            <tr class="visor calc">
                <td colspan="4" class="content" >
                    <div class="cursor-frac"><span class="active" >x</span><span class="line" >&nbsp;</span><span>y</span></div>
                    <div class="details <%= hasResult && ! hasWarnings ? "" : "hide" %>">
                        <ul>
                            <li>Valor Decimal: <span class="decimal" >`<%= resultadoDecimal %>`</span></li>
                            <li class="<%= resultadoSimplificado == null || resultadoSimplificado.isEmpty() ? "hide" : "" %>">Fração Simplificada: <span class="math" >`<%= resultadoSimplificado %>`</span></li>
                        </ul>
                    </div>
                    <div id="calc-screen">
                        `<%= hasResult ? resultado : "0" %>`
                    </div>
                    <ul class="warnings">
                    <%
                    if( avisos != null ) {
                        for (String aviso : avisos) {
                           out.print( "<li>" + aviso + "</li>" );
                        }
                    }
                    %>
                    </ul>
                </td>
            </tr>
            <tr class="keyboard" >
                <td><button id="btn-clean" type="button">C</button></td>
                <td><button type="button" id="btn-backspace" ><img src="assets/images/back-backspace.png"></button></td>
                <td><button id="btn-toogle-cursor" type="button"><span class="active">a</span><span>b</span></button></td>
                <td><button type="button" class="btn-operator" >÷</button></td>
            </tr>
            <tr>
                <td><button type="button" >7</button></td>
                <td><button type="button" >8</button></td>
                <td><button type="button" >9</button></td>
                <td><button type="button" class="btn-operator">×</button></td>
            </tr>
             <tr>
                <td><button type="button" >4</button></td>
                <td><button type="button" >5</button></td>
                <td><button type="button" >6</button></td>
                <td><button type="button" class="btn-operator">-</button></td>
            </tr>
            <tr>
                <td><button type="button" >1</button></td>
                <td><button type="button" >2</button></td>
                <td><button type="button" >3</button></td>
                <td><button type="button" class="btn-operator">+</button></td>
            </tr>
             <tr>
                <td colspan="2"><button type="button">0</button></td>
                <td colspan="2" ><button type="submit" id="btn-calculate">=</button></td>
            </tr>
        </tbody>
    </table>
   </div>
</form>
                    

