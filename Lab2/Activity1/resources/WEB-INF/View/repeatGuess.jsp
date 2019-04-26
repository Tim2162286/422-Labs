<%@ page import="edu.asupoly.ser422.cluegame.Model.GameInfo" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Guess Repeated</title>
</head>
<body>
<p style="color:red">You've already made the <%=((GameInfo)session.getAttribute("game")).getPlayerGuess()%>. Please try again</p>
<form method="get" action="game">
    <input type="submit" value="Continue">
</form>
</body>
</html>
