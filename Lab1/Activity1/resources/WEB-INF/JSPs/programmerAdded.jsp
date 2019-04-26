<%@ page language="java" %>
<html>
<head><title>Programmer Added</title></head>
<body>

<H4>
    <%= request.getAttribute("result") %>
</H4>
<p>
    Number of current programmers:
    <%=request.getAttribute("count")%>
</p>
<a href="<%=request.getContextPath()%>/">Add another programmer</a>
</body>
</html>