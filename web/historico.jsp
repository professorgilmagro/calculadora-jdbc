<%@page import="controller.Historico"%>
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
		<!-- Favicons Generated with favicon.il.ly -->
                <link rel="icon" sizes="16x16 32x32 48x48 64x64" href="assets/images/favicons/favicon.ico"/>
                <!--[if IE]>
                <link rel="shortcut icon" href="assets/images/favicons/favicon.ico"/>
                <![endif]-->
                <!-- Optional: Android & iPhone-->
                <link rel="apple-touch-icon-precomposed" href="assets/images/favicons/favicon-152.png"/>
                <!-- Optional: ipads, androids, iphones, ...-->
                <link rel="apple-touch-icon-precomposed" sizes="152x152" href="assets/images/favicons/favicon-152.png"/>
                <link rel="apple-touch-icon-precomposed" sizes="144x144" href="assets/images/favicons/favicon-144.png"/>
                <link rel="apple-touch-icon-precomposed" sizes="120x120" href="assets/images/favicons/favicon-120.png"/>
                <link rel="apple-touch-icon-precomposed" sizes="114x114" href="assets/images/favicons/favicon-114.png"/>
                <link rel="apple-touch-icon-precomposed" sizes="72x72" href="assets/images/favicons/favicon-72.png"/>
                <link rel="apple-touch-icon-precomposed" href="assets/images/favicons/favicon-57.png"/>
		<link rel="stylesheet" type="text/css" href="assets/css/dataTables.css">
		<link rel="stylesheet" type="text/css" href="assets/css/base.css">
		<link rel="stylesheet" type="text/css" href="assets/css/historico.css">
		<link rel="stylesheet" type="text/css" href="assets/css/mobile.css" media="screen and (max-width: 768px)">
		<script type="text/javascript" src="assets/js/jquery-1.9.0.js" ></script>
		<script type="text/javascript" src="assets/js/dataTables.min.js" ></script>
                <script type="text/javascript" async src="https://cdn.mathjax.org/mathjax/latest/MathJax.js?config=AM_HTMLorMML-full.js"></script>
		<script type="text/javascript" src="assets/js/historic.js" ></script>
		<title>Páginas HTML Estáticas - Equipe</title>
	</head>
	<body class="equipe">
		<header class="main" >
			<h1><a href="index.html"><img src="assets/images/aiec-logo.png" class="logo"></a> Calculador de fração 1.0</h1>
			<%@ include file="/parts/menu.jsp" %>
		</header>
		<section class="content">
			<h1>Histórico</h1>
			<article class="historic" >
				<table id="tabela-historico" class="table table-responsive">
					<caption>Histórico de cálculos</caption>
					<thead>
						<tr>
							<th>Expressão</th>
							<th>Resultado</th>
							<th>Simplificação</th>
							<th>Valor Decimal</th>
							<th>Classificação</th>
							<th>Remover</th>
						</tr>
					</thead>
					<tbody>
                                                <%
                                                    List<Historic> historico = (List<Historic>) request.getAttribute("historico");
                                                    
                                                    if( historico != null && ! historico.isEmpty()){
                                                        for (Historic hist : historico) {
                                                %>
						<tr>
							<td>`<%= hist.getExpressao() %>`</td>
							<td>`<%= hist.getResultado() %>`</td>
							<td>`<%= hist.getSimplificado() %>`</td>
							<td><%= hist.getDecimal().toPlainString() %></td>
							<td><%= hist.getClassificacao() %></td>
                                                        <td><a onclick="return confirm('Tem certeza que deseja excluir este item?')" href="historico?action=<%= Historico.ACTION_REMOVE %>&idx=<%= hist.getId() %>"><img src="assets/images/delete2.png"></a></td>
						</tr>
                                                <%
                                                        }
                                                    }
                                                %>
					</tbody>
				</table>
			</article>
		</section>
		<%@ include file="/parts/footer.jsp" %>
	</body>
</html>