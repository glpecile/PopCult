<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<div class="flex justify-center items-center bg-transparent py-4">
    <nav class="flex flex-col sm:flex-row">
        <a href=<c:url value="/user/${param.username}"/>>
            <c:choose>
                <c:when test="${param.path == 'myLists'}">
                    <button class="text-gray-600 py-2 px-6 block hover:text-purple-500 focus:outline-none text-purple-500 border-b-2 font-medium border-purple-500">
                        <c:out value="${param.username}"/><spring:message code="profile.tabs.main"/>
                    </button>
                </c:when>
                <c:otherwise>
                    <button class="text-gray-600 py-2 px-6 block hover:text-purple-500 focus:outline-none">
                        <c:out value="${param.username}"/><spring:message code="profile.tabs.main"/>
                    </button>
                </c:otherwise>
            </c:choose>
        </a>
        <a href=<c:url value="/user/${param.username}/favoriteMedia"/>>
            <c:choose>
                <c:when test="${param.path == 'favMedia'}">
                    <button class="text-gray-600 py-2 px-6 block hover:text-purple-500 focus:outline-none text-purple-500 border-b-2 font-medium border-purple-500">
                        <spring:message code="profile.tabs.favMedia"/>
                    </button>
                </c:when>
                <c:otherwise>
                    <button class="text-gray-600 py-2 px-6 block hover:text-purple-500 focus:outline-none">
                        <spring:message code="profile.tabs.favMedia"/>
                    </button>
                </c:otherwise>
            </c:choose>
        </a>
        <a href=<c:url value="/user/${param.username}/favoriteLists"/>>
            <c:choose>
                <c:when test="${param.path == 'favLists'}">
                    <button class="text-gray-600 py-2 px-6 block hover:text-purple-500 focus:outline-none text-purple-500 border-b-2 font-medium border-purple-500">
                        <spring:message code="profile.tabs.favLists"/>
                    </button>
                </c:when>
                <c:otherwise>
                    <button class="text-gray-600 py-2 px-6 block hover:text-purple-500 focus:outline-none">
                        <spring:message code="profile.tabs.favLists"/>
                    </button>
                </c:otherwise>
            </c:choose>
        </a>
        <a href=<c:url value="/user/${param.username}/watchedMedia"/>>
            <c:choose>
                <c:when test="${param.path == 'WatchedMedia'}">
                    <button class="text-gray-600 py-2 px-6 block hover:text-purple-500 focus:outline-none text-purple-500 border-b-2 font-medium border-purple-500">
                        <spring:message code="profile.tabs.watchedMedia"/>
                    </button>
                </c:when>
                <c:otherwise>
                    <button class="text-gray-600 py-2 px-6 block hover:text-purple-500 focus:outline-none">
                        <spring:message code="profile.tabs.watchedMedia"/>
                    </button>
                </c:otherwise>
            </c:choose>
        </a>
        <a href=<c:url value="/user/${param.username}/toWatchMedia"/>>
            <c:choose>
                <c:when test="${param.path == 'Watchlist'}">
                    <button class="text-gray-600 py-2 px-6 block hover:text-purple-500 focus:outline-none text-purple-500 border-b-2 font-medium border-purple-500">
                        <spring:message code="profile.tabs.toWatchMedia"/>
                    </button>
                </c:when>
                <c:otherwise>
                    <button class="text-gray-600 py-2 px-6 block hover:text-purple-500 focus:outline-none">
                        <spring:message code="profile.tabs.toWatchMedia"/>
                    </button>
                </c:otherwise>
            </c:choose>
        </a>
    </nav>
</div>
</html>
