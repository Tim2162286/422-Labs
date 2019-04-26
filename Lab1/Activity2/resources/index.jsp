<%@ page import="Util.Search" %>
<%! boolean[] days; %>
<%! Search search = new Search(null,null,null,null,null); %>
<%
    Cookie[] cookies = request.getCookies();
    for(Cookie c:cookies) {
        if (c.getName().equals("search")) {
            System.out.println("Cooke Value: "+c.getValue());
            search = new Search(c.getValue());
        }
    }
    days = search.getDays();
    for(int i=0;i<days.length;i++)
        System.out.println(days[i]);
%>

<html xmlns="http://www.w3.org/1999/html">
<head>
    <title>Home</title>
</head>
<body>
<a href="newprogrammer">Add new Programmer</a>
<br>
<h3>
    Find a Programmer
</h3>
<form action="programmers" method="post">
    First Name:
    <input type="text" name="firstname" autocomplete="false" value="<%out.print(search.getFname());%>"></br>
    Last Name:
    <input type="text" name="lastname" autocomplete="false" value="<%out.print(search.getLname());%>"></br>
    Known Programing Languages(comma separated list):
    <input type="text" name="languages" autocomplete="false" value="<%out.print(search.getLangs());%>"></br>
    Available Dates<br>
    <input id="1" type="checkbox" name="day" value="M"<%if(days[0]) out.print("checked");%>>Monday</input>
    <input type="checkbox" name="day" value="Tu"<%if(days[1]) out.print("checked");%>>Tuesday</input>
    <input type="checkbox" name="day" value="W"<%if(days[2]) out.print("checked");%>>Wednesday</input>
    <input type="checkbox" name="day" value="Th"<%if(days[3]) out.print("checked");%>>Thursday</input>
    <input type="checkbox" name="day" value="F"<%if(days[4]) out.print("checked");%>>Friday</input>
    <input type="checkbox" name="day" value="Sa"<%if(days[5]) out.print("checked");%>>Saturday</input>
    <input type="checkbox" name="day" value="Su"<%if(days[6]) out.print("checked");%>>Sunday</input><br>
    Focus area:
    <input name="focus" type="text" value="<%out.print(search.getFocus());%>"></br>
    <input type="submit" value="Find Programmers" autocomplete="false">

</form>
</body>
</html>
