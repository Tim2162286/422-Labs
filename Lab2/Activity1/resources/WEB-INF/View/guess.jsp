<%@ page import="edu.asupoly.ser422.cluegame.Model.GameInfo" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<% session.setAttribute("state","guess");%>
<%GameInfo game = (GameInfo)session.getAttribute("game");%>
<html>
<head>
    <title>Guess</title>
</head>
<body>
<span><%
    for (String s:game.getSUSPECTS()){
        out.print(s+",");
    }
%></span><br>
<span><%
    for (String s:game.getROOMS()){
        out.print(s+",");
    }
%></span><br>
<span><%
    for (String s:game.getWEAPONS()){
        out.print(s+",");
    }
%></span><br>
<form method="post" action="game">
    <select id="suspect" name="suspect"><%
        for (String s:game.getPlayerSuspects()){%>
        <option value="<%=s%>"><%=s%></option>
        <%}%></select><br>
    <select id="room" name="room"><%
        for (String s:game.getPlayerRooms()){%>
        <option value="<%=s%>"><%=s%></option>
        <%}%></select><br>
    <select id="weapon" name="weapon"><%
        for (String s:game.getPlayerWeapons()){%>
        <option value="<%=s%>"><%=s%></option>
        <%}%></select><br>
    <input type="submit" value="Submit">
</form>
</body>
</html>
