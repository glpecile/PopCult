<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<a href="<c:url value="${param.url}"/>" title="<c:out value="${param.tooltip}"/>">
    <button
            class="rounded-full capitalize whitespace-nowrap text-center text-gray-700 bg-yellow-50 shadow-md p-2 hover:bg-yellow-100 hover:shadow-lg">
        <c:out value="${param.text}"/>
    </button>
</a>

