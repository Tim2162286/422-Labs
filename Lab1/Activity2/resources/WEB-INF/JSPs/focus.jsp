<html>
<head><title>Name</title></head>
<body>

<H4>
    Please enter your primary focus area.
</H4>
<form method="post">
    Focus Area:<br>
    <input type="text" name="focus"<%
    String focus =(String)session.getAttribute("focus");
    if(focus!=null)
        out.print("value=\""+focus+"\"");%>>
    <input formaction="/newprogrammer/days" type="submit" value="prev">
    <input formaction="/newprogrammer/submit" type="submit" value="next">
</form>
</body>
</html>