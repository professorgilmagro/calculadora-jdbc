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
		<link rel="stylesheet" type="text/css" href="assets/css/base.css">
		<link rel="stylesheet" type="text/css" href="assets/css/sobre.css">
		<link rel="stylesheet" type="text/css" href="assets/css/mobile.css" media="screen and (max-width: 768px)">
		<script type="text/javascript" src="assets/js/jquery-1.9.0.js" ></script>
		<title>Páginas HTML Estáticas - Sobre</title>
	</head>
	<body class="equipe">
		<header class="main" >
			<h1><a href="index.html"><img src="assets/images/aiec-logo.png" class="logo"></a> Calculador de fração 1.0</h1>
			<%@ include file="/parts/menu.jsp" %>
		</header>
		<section class="content">
			<article>
				<header>
					<h1>Sobre</h1>
					<p>Trabalho da disciplina Programação WEB</p>
				</header>
				<p>
					<strong>Objetivo 1:</strong> Complementar os conhecimentos adquiridos pelos
					discentes no âmbito da disciplina pela abordagem dos conceitos estudados
					com o desenvolvimento de páginas estáticas em linguagem HTML.
				</p>
                                <p>
                                    <strong>Objetivo 2:</strong> Complementar os conhecimentos adquiridos pelos
discentes no âmbito da disciplina pela abordagem dos conceitos estudados
com o desenvolvimento de páginas web dinâmicas com Servlets (linguagem
Java).
                                </p>
                                <p>
                                    <strong>Objetivo 3:</strong> Vincular as páginas estáticas criadas em HTML aos Sevlets e, por último, às
páginas web dinâmicas que serão criadas pelas páginas JSP. Deste modo, esta
atividade visa permitir ao usuário a integração entre HTML, Servlets e JSP,
procurando estruturar, ainda que de modo embrionário, a separação lógica do
aplicativo web em três camadas (apresentação, controle e negócio).
                                </p>
                                <p>
                                    <strong>Objetivo 4:</strong> 
                                    Permitir ao usuário a integração entre HTML, Servlets e
JSP, procurando estruturar, por meio do uso de padrões de projeto, a
separação lógica do aplicativo web em quatro camadas (apresentação,
controle, negócio e persistência). A atividade visa a manipulação de uma
fração matemática.
                                </p>
				<p>Sistema para permitir ao usuário a manipulação de uma fração (numerador e denominador) matemática.</p>
				<p>&nbsp;</p>
				<p><strong>Professor:</strong> Guilherme Veloso Neves Oliveira</p>
			</article>
		</section>
		<%@ include file="/parts/footer.jsp" %>
	</body>
</html>