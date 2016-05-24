<%@page import="java.util.List"%>
<%@page import="model.Historic"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="pt-br">
	<head>
            <meta charset="utf-8">
            <meta content="width=device-width, initial-scale=1, maximum-scale=1" name="viewport">
            <meta name="description" content="Atividade da disciplina Programação Web (AIEC), cujo objetivo visa complementar os conhecimentos adquiridos pelos discentes no âmbito da disciplina pela abordagem dos conceitos estudados com o desenvolvimento de páginas estáticas em linguagem HTML." />
            <meta name="keywords" content="calculadora,fracao,matematica,conta">
            <meta name="theme-color" content="#333">
            
            <!-- Favicons Generated with favicon.il.ly -->
            <link rel="icon" sizes="16x16 32x32 48x48 64x64" href="<%= request.getContextPath() %>/assets/images/favicons/favicon.ico"/>
            <!--[if IE]>
            <link rel="shortcut icon" href="<%= request.getContextPath() %>/assets/images/favicons/favicon.ico"/>
            <![endif]-->
            <!-- Optional: Android & iPhone-->
            <link rel="apple-touch-icon-precomposed" href="<%= request.getContextPath() %>/assets/images/favicons/favicon-152.png"/>
            <!-- Optional: ipads, androids, iphones, ...-->
            <link rel="apple-touch-icon-precomposed" sizes="152x152" href="<%= request.getContextPath() %>/assets/images/favicons/favicon-152.png"/>
            <link rel="apple-touch-icon-precomposed" sizes="144x144" href="<%= request.getContextPath() %>/assets/images/favicons/favicon-144.png"/>
            <link rel="apple-touch-icon-precomposed" sizes="120x120" href="<%= request.getContextPath() %>/assets/images/favicons/favicon-120.png"/>
            <link rel="apple-touch-icon-precomposed" sizes="114x114" href="<%= request.getContextPath() %>/assets/images/favicons/favicon-114.png"/>
            <link rel="apple-touch-icon-precomposed" sizes="72x72" href="<%= request.getContextPath() %>/assets/images/favicons/favicon-72.png"/>
            <link rel="apple-touch-icon-precomposed" href="<%= request.getContextPath() %>/assets/images/favicons/favicon-57.png"/>
            <link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/assets/css/base.css">
            <link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/assets/css/home.css">
            <link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/assets/css/calculadora.css">
            <link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/assets/css/mobile.css" media="screen and (max-width: 768px)">
            <script type="text/javascript" src="<%= request.getContextPath() %>/assets/js/jquery-1.9.0.js" ></script>
            <script type="text/javascript" async src="https://cdn.mathjax.org/mathjax/latest/MathJax.js?config=AM_HTMLorMML-full.js"></script>
            <script type="text/javascript" src="<%= request.getContextPath() %>/assets/js/calculator.js" ></script>
            <title>Calculadora de frações - Home</title>
	</head>
	<body class="home">
		<header class="main" >
			<h1><a href="<%= request.getContextPath() %>/index.html"><img src="<%= request.getContextPath() %>/assets/images/aiec-logo.png" class="logo"></a> Calculador de fração 1.0</h1>
			<%@ include file="/parts/menu.jsp" %>
		</header>
		<section class="content-left" >
			<article>
				<header>
					<h2>Calculadora de fração</h2>
					<p>Quando precisamos representar numericamente uma parte de um todo, utilizamos as frações. A estrutura da fração é dada por a/b, onde a é o numerador e b o denominador.
					a: numerador, b: denominador O traço entre o a e o b significa divisão.</p>
				</header>
				<p>
					Fração é uma parte do todo – inteiro. O conceito de fração é gerado em situação nas quais precisamos dividir um número menor por outro maior.
					<br>Com o intuito de auxiliar nas medições de terras, as frações surgiram no Egito há cerca de 3000 anos.
				</p>
				<p>
					Cada tipo de fração possui sua especificidade. Por isso, é necessária uma forma de classificá-las. Assim, há três tipos de fração: própria, imprópria e aparente.<br>
					<br><strong>Fração própria:</strong> o numerador é menor que o denominador.
					<br><strong>Fração imprópria:</strong> o numerador é maior do que o denominador.
					<br><strong>Fração aparente:</strong> São as frações em que o numerador é múltiplo do denominador.
				</p>
			</article>
		<%@ include file="/parts/calculadora.jsp" %>	
		</section>
		<section class="content-right" >
                    <%
                        List<Historic> historico = (List<Historic>) request.getAttribute("historico");
                    %>
			<aside class="history <%= historico == null || historico.isEmpty() ? "hide" : "" %>">
				<table>
					<caption><img src="<%= request.getContextPath() %>/assets/images/calculator.png" class="icon"> Cálculos recentes</caption>
					<thead>
						<tr>
                                                    <th>Expressão</th>
						</tr>
					</thead>
					<tbody>
                                            <%
                                                // imprime somente os últimos 10 cálculos
                                                if( historico != null && ! historico.isEmpty()){
                                                    for (Historic hist : historico) {
                                            %>
						<tr>
                                                    <td>`<%= hist.getExpressao() %>`</td>
						</tr>
                                            <%
                                                    }
                                                    
                                                    String link = String.format("<tr><td><a href=\"%s/historico\">Clique aqui para ver os resultados</a></td></tr>", request.getContextPath());
                                                    out.print(link);
                                                }
                                            %>
					</tbody>
				</table>
			</aside>
		</section>
		<%@ include file="/parts/footer.jsp" %>
        </body>
</html>