<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Página não encontrada</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
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
        <link rel="stylesheet" type="text/css" href="assets/css/error.css">
    </head>
    <body>
        <div class="main">
             <div class="area-404">
                <h1>A página solicitada não foi encontrada!</h1>
                <a href="<%= request.getContextPath() %>/home">Voltar para home</a>
            </div>
        </div>
       
    </body>
</html>
