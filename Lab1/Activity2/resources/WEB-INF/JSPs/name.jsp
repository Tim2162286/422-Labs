<html>
<head><title>Name</title></head>
<body>

<H4>
    Please enter your first and last name.
</H4>
<form method="post">
    First Name:<br>
    <input type="text" name="fName"<%
    String fName =(String)session.getAttribute("fName");
    if(fName!=null)
        out.print("value=\""+fName+"\"");%>><br>
    Last Name:<br>
    <input type="text" name="lName"<%
    String lName = (String)session.getAttribute("lName");
    if(fName!=null)
        out.print("value=\""+lName+"\"");%>>
    <input formaction="/newprogrammer/languages" type="submit" value="next">
</form>
</body>
</html>