<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <div class="bg-transparent py-4">
        <nav class="flex flex-col sm:flex-row">
            <a href=<c:url value="/${param.username}"/>>
                <c:choose>
                    <c:when test="${param.path == 'myLists'}">
                        <button class="text-gray-600 py-2 px-6 block hover:text-purple-500 focus:outline-none text-purple-500 border-b-2 font-medium border-purple-500">My Lists</button>
                    </c:when>
                    <c:otherwise>
                        <button class="text-gray-600 py-2 px-6 block hover:text-purple-500 focus:outline-none">My Lists</button>
                    </c:otherwise>
                </c:choose>
            </a>
            <a href=<c:url value="/${param.username}/favoriteMedia"/>>
                <c:choose>
                    <c:when test="${param.path == 'favMedia'}">
                        <button class="text-gray-600 py-2 px-6 block hover:text-purple-500 focus:outline-none text-purple-500 border-b-2 font-medium border-purple-500">Favorite Media</button>
                    </c:when>
                    <c:otherwise>
                        <button class="text-gray-600 py-2 px-6 block hover:text-purple-500 focus:outline-none">Favorite Media</button>
                    </c:otherwise>
                </c:choose>
            </a>
            <a href=<c:url value="/${param.username}/favoriteLists"/>>
                <c:choose>
                    <c:when test="${param.path == 'favLists'}">
                        <button class="text-gray-600 py-2 px-6 block hover:text-purple-500 focus:outline-none text-purple-500 border-b-2 font-medium border-purple-500">Favorite Lists</button>
                    </c:when>
                    <c:otherwise>
                        <button class="text-gray-600 py-2 px-6 block hover:text-purple-500 focus:outline-none">Favorite Lists</button>
                    </c:otherwise>
                </c:choose>
            </a>
            <a href=<c:url value="/${param.username}/watchedMedia"/>>
                <c:choose>
                    <c:when test="${param.path == 'WatchedMedia'}">
                        <button class="text-gray-600 py-2 px-6 block hover:text-purple-500 focus:outline-none text-purple-500 border-b-2 font-medium border-purple-500">Watched Media</button>
                    </c:when>
                    <c:otherwise>
                        <button class="text-gray-600 py-2 px-6 block hover:text-purple-500 focus:outline-none">Watched Media</button>
                    </c:otherwise>
                </c:choose>
            </a>
            <a href=<c:url value="/${param.username}/toWatchMedia"/>>
                <c:choose>
                    <c:when test="${param.path == 'Watchlist'}">
                        <button class="text-gray-600 py-2 px-6 block hover:text-purple-500 focus:outline-none text-purple-500 border-b-2 font-medium border-purple-500">To Watch Media</button>
                    </c:when>
                    <c:otherwise>
                        <button class="text-gray-600 py-2 px-6 block hover:text-purple-500 focus:outline-none">To Watch Media</button>
                    </c:otherwise>
                </c:choose>
            </a>
        </nav>
    </div>
</html>
