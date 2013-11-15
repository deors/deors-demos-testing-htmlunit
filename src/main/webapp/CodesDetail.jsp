<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>Codes Detail Page</title>
</head>
<body>

<c:choose>
    <c:when test="${param.mode == 'add'}">
        <c:set var="title" value="Add new code"/>
        <c:set var="action" value="CodesAdd.do"/>
        <c:set var="keyreadonly" value=""/>
        <c:set var="nokeyreadonly" value=""/>
    </c:when>
    <c:when test="${param.mode == 'update'}">
        <c:set var="title" value="Update code"/>
        <c:set var="action" value="CodesUpdate.do"/>
        <c:set var="keyreadonly" value=" readonly=\"readonly\""/>
        <c:set var="nokeyreadonly" value=""/>
    </c:when>
    <c:when test="${param.mode == 'delete'}">
        <c:set var="title" value="Delete code"/>
        <c:set var="action" value="CodesDelete.do"/>
        <c:set var="keyreadonly" value=" readonly=\"readonly\""/>
        <c:set var="nokeyreadonly" value=" readonly=\"readonly\""/>
    </c:when>
</c:choose>
<h3><c:out value="${title}"/></h3>
<form action="${action}" method="post" name="detail">
<table>
    <tr>
        <td>Code:</td>
        <td><input name="code" value="${param.code}"<c:out value="${keyreadonly}"/>/></td>
    </tr>
    <tr>
        <td>Value:</td>
        <td><input name="value" value="${param.value}"<c:out value="${nokeyreadonly}"/>/></td>
    </tr>
</table>
<input type="submit" name="ok" value="Ok"/>
<input type="submit" name="cancel" value="Cancel" onclick="document.detail.action='CodesView.do'"/>
</form>

</body>
</html>
