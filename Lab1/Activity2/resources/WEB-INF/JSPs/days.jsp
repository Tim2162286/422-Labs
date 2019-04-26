<html>
<head><title>Name</title></head>
<body>

<H4>
    Please enter the days you are available.
</H4>
<form method="post">
    Days:<br>
    <input type="text" name="days"<%
    String days =(String)session.getAttribute("days");
    if(days!=null)
        out.print("value=\""+days+"\"");%>>
    <input formaction="/newprogrammer/languages" type="submit" value="prev">
    <input formaction="/newprogrammer/focus" type="submit" value="next">
</form>
</body>
</html>