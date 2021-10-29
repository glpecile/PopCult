<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<div class="flex justify-center items-center bg-transparent py-4">
    <nav class="flex flex-col sm:flex-row">
        <a href=<c:url value="/admin/mods"/>>
            <c:choose>
                <c:when test="${param.path == 'moderators'}">
                    <button class="text-gray-600 py-2 px-6 block hover:text-purple-500 focus:outline-none text-purple-500 border-b-2 font-medium border-purple-500">
                        <spring:message code="mods.moderators.title" arguments="${param.moderators}"/>
                    </button>
                </c:when>
                <c:otherwise>
                    <button class="text-gray-600 py-2 px-6 block hover:text-purple-500 focus:outline-none">
                        <spring:message code="mods.moderators.title" arguments="${param.moderators}"/>
                    </button>
                </c:otherwise>
            </c:choose>
        </a>
        <a href=<c:url value="/admin/mods/requests"/>>
            <c:choose>
                <c:when test="${param.path == 'modRequests'}">
                    <button class="text-gray-600 py-2 px-6 block hover:text-purple-500 focus:outline-none text-purple-500 border-b-2 font-medium border-purple-500">
                        <spring:message code="mods.requests.title" arguments="${param.modRequests}"/>
                    </button>
                </c:when>
                <c:otherwise>
                    <button class="text-gray-600 py-2 px-6 block hover:text-purple-500 focus:outline-none">
                        <spring:message code="mods.requests.title" arguments="${param.modRequests}"/>
                    </button>
                </c:otherwise>
            </c:choose>
        </a>
    </nav>
</div>
</html>
