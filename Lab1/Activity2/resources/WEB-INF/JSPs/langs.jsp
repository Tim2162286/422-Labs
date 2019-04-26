<html>
<head><title>Name</title></head>
<body>

<H4>
    Please enter any programing languages you know as a comma separated list.
</H4>
<form method="post">
    Languages:<br>
    <input type="text" name="langs"<%
    String langs =(String)session.getAttribute("langs");
    if(langs!=null)
        out.print("value=\""+langs+"\"");%>>
    <input formaction="/newprogrammer/name" type="submit" value="prev">
    <input formaction="/newprogrammer/days" type="submit" value="next">
</form>
</body>
</html>