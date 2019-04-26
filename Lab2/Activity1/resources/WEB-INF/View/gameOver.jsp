<% session.invalidate(); %>
<p style="color:red"><%=request.getAttribute("outcome")%></p>
<form method="get" action="./">
    <input type="submit" value="New Game">
</form>
