<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<div class="flex justify-center items-center bg-transparent py-4">
    <nav class="flex flex-col sm:flex-row">
        <a href=<c:url value="/user/${param.username}/requests"/>>
            <c:choose>
                <c:when test="${param.path == 'requests'}">
                    <button class="text-gray-600 py-2 px-6 block hover:text-purple-500 focus:outline-none text-purple-500 border-b-2 font-medium border-purple-500">
                        <spring:message code="notifications.tabs.requests"/>
                    </button>
                </c:when>
                <c:otherwise>
                    <button class="text-gray-600 py-2 px-6 block hover:text-purple-500 focus:outline-none">
                        <spring:message code="notifications.tabs.requests"/>
                    </button>
                </c:otherwise>
            </c:choose>
        </a>
        <a href=<c:url value="/user/${param.username}/notifications"/>>
            <c:choose>
                <c:when test="${param.path == 'notifications'}">
                    <button class="text-gray-600 py-2 px-6 block hover:text-purple-500 focus:outline-none text-purple-500 border-b-2 font-medium border-purple-500">
                        <spring:message code="notifications.tabs"/>
                    </button>
                </c:when>
                <c:otherwise>
                    <button class="text-gray-600 py-2 px-6 block hover:text-purple-500 focus:outline-none">
                        <spring:message code="notifications.tabs"/>
                    </button>
                </c:otherwise>
            </c:choose>
        </a>
    </nav>
</div>
</html>
