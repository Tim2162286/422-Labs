<%@ page import="edu.asupoly.ser422.cluegame.Model.Guess" %>
<%@ page import="edu.asupoly.ser422.cluegame.Model.GameInfo" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%session.setAttribute("state","results");%>
<%
    GameInfo game = (GameInfo)session.getAttribute("game");
    Guess playerGuess = game.getPlayerGuess();
    Guess computerGuess = game.getComputerGuess();
%>
<html>
<head>
    <title>Round Results</title>
</head>
<body>
<% if(playerGuess.equals(game.getWinningSecret())) {
    request.setAttribute("outcome","Your " + playerGuess + " was correct. You win!");%>
<jsp:include page="gameOver.jsp"></jsp:include>
<% }else {%>
<p style="color: red">Your <%=playerGuess%> was incorrect. You guessed <%=playerGuess.whichIsWrong(game.getWinningSecret())%>
    incorrectly. Please try again</p>
<p>Computers Guess: <%=computerGuess%></p>
<% if(computerGuess.equals(game.getWinningSecret())){
    request.setAttribute("outcome","Computer guess is correct. Computer wins!");%>
<jsp:include page="gameOver.jsp"></jsp:include>
<% }else {%>
<p style="color: red">Computer guess is incorrect. Computer guessed <%=computerGuess.whichIsWrong(game.getWinningSecret())%>
    incorrectly.</p>
<form method="get" action="game">
    <input type="submit" value="Continue">
</form>
<%}}%>
</body>
</html>