<%@ page import="edu.asupoly.ser422.model.PhoneEntry" %>
<%String[] listings = (String[])request.getAttribute("listings"); %>
<html>
<head>
    <title>Listings</title>
</head>
<body>
<%for(int i=0; i<listings.length;i++){%>
<b><%=i%>:</b> <%=listings[i]%> <br/>
<%}%>
</body>
</html>