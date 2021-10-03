<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<div class="flex justify-center items-center bg-transparent py-4">
    <nav class="flex flex-col sm:flex-row">
        <a href=<c:url value="/admin/reports/lists"/>>
            <c:choose>
                <c:when test="${param.path == 'listsReports'}">
                    <button class="text-gray-600 py-2 px-6 block hover:text-purple-500 focus:outline-none text-purple-500 border-b-2 font-medium border-purple-500">
                        <spring:message code="admin.title.listReports"/>
                    </button>
                </c:when>
                <c:otherwise>
                    <button class="text-gray-600 py-2 px-6 block hover:text-purple-500 focus:outline-none">
                        <spring:message code="admin.title.listReports"/>
                    </button>
                </c:otherwise>
            </c:choose>
        </a>
        <a href=<c:url value="/admin/reports/lists/comments"/>>
            <c:choose>
                <c:when test="${param.path == 'listsCommentsReports'}">
                    <button class="text-gray-600 py-2 px-6 block hover:text-purple-500 focus:outline-none text-purple-500 border-b-2 font-medium border-purple-500">
                        <spring:message code="admin.title.listCommentReports"/>
                    </button>
                </c:when>
                <c:otherwise>
                    <button class="text-gray-600 py-2 px-6 block hover:text-purple-500 focus:outline-none">
                        <spring:message code="admin.title.listCommentReports"/>
                    </button>
                </c:otherwise>
            </c:choose>
        </a>
        <a href=<c:url value="/admin/reports/media/comments"/>>
            <c:choose>
                <c:when test="${param.path == 'mediaCommentsReports'}">
                    <button class="text-gray-600 py-2 px-6 block hover:text-purple-500 focus:outline-none text-purple-500 border-b-2 font-medium border-purple-500">
                        <spring:message code="admin.title.mediaCommentReports"/>
                    </button>
                </c:when>
                <c:otherwise>
                    <button class="text-gray-600 py-2 px-6 block hover:text-purple-500 focus:outline-none">
                        <spring:message code="admin.title.mediaCommentReports"/>
                    </button>
                </c:otherwise>
            </c:choose>
        </a>
    </nav>
</div>
</html>
