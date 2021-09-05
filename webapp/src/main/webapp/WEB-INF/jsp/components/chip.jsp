<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<a href="<c:url value="${param.url}"/>" title="<c:out value="${param.tooltip}"/>"><button type="button" class="btn btn-outline-dark rounded-pill m-1 p-1"><c:out value="${param.text}"/></button></a>

