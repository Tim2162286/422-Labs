<%! boolean canSubmit=true; %>
<html>
<head><title>Name</title></head>
<body>
<h4>
    First name:
<%
    String fName = (String)session.getAttribute("fName");
    if(fName==null){
        canSubmit = false;
        out.print("First name not found, please click <a href=\"/newprogrammer/name\">here</a>");
    }else out.print(fName);
%>
</h4>
<h4>
    Last name:
    <%
        String lName = (String)session.getAttribute("lName");
        if(lName==null){
            canSubmit = false;
            out.print("Last name not found, please click <a href=\"/newprogrammer/name\">here</a>");
        }else out.print(lName);
    %>
</h4>
<h4>
    Languages:
    <%
        String langs = (String)session.getAttribute("langs");
        if(langs==null){
            canSubmit = false;
            out.print("No languages found, please click <a href=\"/newprogrammer/languages\">here</a>");
        }else out.print(langs);
    %>
</h4>
<h4>
    Days Available:
    <%
        String days = (String)session.getAttribute("days");
        if(days==null){
            canSubmit = false;
            out.print("No days found, please click <a href=\"/newprogrammer/days\">here</a>");
        }else out.print(days);
    %>
</h4>
<h4>
    Focus Area:
    <%
        String focus = (String)session.getAttribute("focus");
        if(fName==null){
            canSubmit = false;
            out.print("No focus area found, please click <a href=\"/newprogrammer/focus\">here</a>");
        }else out.print(focus);
    %>
</h4>

<form method="post">
    <input formaction="/newprogrammer/focus" type="submit" value="prev">
    <input formaction="/newprogrammer/add" type="submit" value="Submit" <%if(!canSubmit) out.print("disabled");%>>
    <input formaction="/newprogrammer/cancel" type="submit" value="Cancel">
</form>
</body>
</html>