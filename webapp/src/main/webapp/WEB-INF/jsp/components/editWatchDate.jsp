<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<sec:authorize access="isAuthenticated()">
    <c:set var="currentUsername">
        <sec:authentication property="principal.username"/>
    </c:set>
</sec:authorize>
<c:if test="${currentUsername == param.listOwner}">
    <button
            class="h-10 w-full truncate bg-gray-300 hover:bg-purple-500 text-gray-700 font-semibold hover:text-white my-2.5 px-4 border border-gray-500 hover:border-transparent rounded-lg"
            data-bs-toggle="modal"
            data-bs-target="#editDate${param.id}Modal">
        <i class="fas fa-pencil-alt pr-2" aria-hidden="true"></i><spring:message code="editWatch.watched" arguments="${param.lastWatched}"/>
    </button>
</c:if>
<div class="modal fade" id="editDate${param.id}Modal" tabindex="-1" aria-labelledby="editDateModalLabel"
     aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title font-bold text-2xl" id="editDateModalLabel">
                    <spring:message code="general.edit"/> <c:out value="${param.mediaTitle}"/> <spring:message
                        code="editWatch.watched.date" arguments="${param.lastWatched}"/>
                </h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                <c:url value="/user/${param.listOwner}/watchedMedia" var="editDatePath"/>
                <form:form method="POST" action="${editDatePath}">
                    <label for="watchedDate"><spring:message code="editWatch.watched" arguments="${param.lastWatched}"/> -> </label>
                    <input type="date" id="watchedDate" name="watchedDate" required min="1990-01-01" max="${param.currentDate}">
                    <span class="validity"></span>
                    <input type="hidden" name="username" value="<c:out value="${param.listOwner}"/>">
                    <input type="hidden" name="mediaId" value="<c:out value="${param.id}"/>">
                    <input type="hidden" name="userId" value="<c:out value="${param.listOwnerId}"/>">
                    <div class="flex justify-end">
                        <button class="col-auto btn btn-secondary rounded-lg" type="submit">
                            <spring:message code="editWatch.setNewDate"/>
                        </button>
                    </div>
                </form:form>
            </div>
        </div>
    </div>
</div>