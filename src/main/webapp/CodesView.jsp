<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>Codes View Page</title>
</head>
<body>

<c:if test="${not empty requestScope['list']}">
    <table>
        <tr>
            <th>Code</th>
            <th>Value</th>
        </tr>
    <c:forEach var="item" items="${requestScope['list']}">
        <tr>
            <td>${item.code}</td>
            <td>${item.value}</td>
            <td>
                <form action="CodesDetail.jsp" method="post" name="form_${item.code}">
                    <input type="hidden" name="mode"/>
                    <input type="hidden" name="code" value="${item.code}"/>
                    <input type="hidden" name="value" value="${item.value}"/>
                    <input type="submit" name="update" value="Update" onclick="document.form_${item.code}.mode.value='update'"/>
                    <input type="submit" name="delete" value="Delete" onclick="document.form_${item.code}.mode.value='delete'"/>
                </form>
            </td>
        </tr>
    </c:forEach>
    </table>
</c:if>

<c:if test="${not empty requestScope['message']}">
    <h3 style="color: #ef2f2f">${requestScope['message']}</h3>
</c:if>

    <br/>
    <form action="CodesDetail.jsp" method="post" name="add">
        <input type="hidden" name="mode" value="add"/>
        <input type="submit" name="add" value="Add new"/>
    </form>

</body>
</html>
