<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<sec:authorize access="isAuthenticated()">
    <c:set var="currentUsername">
        <sec:authentication property="principal.username"/>
    </c:set>
</sec:authorize>
<jsp:useBean id="now" class="java.util.Date"/>
<fmt:formatDate var="localDate" value="${now}" pattern="yyyy-MM-dd" />
<div>
    <c:if test="${currentUsername == param.listOwner}">
        <div class="flex justify-center py-2 ">
            <button class="bg-gray-300 hover:bg-red-400 text-gray-700 font-semibold hover:text-white py-2 px-4 border border-gray-500 hover:border-transparent btn-rounded"
                    data-bs-toggle="modal"
                    data-bs-target="#editDate${param.id}Modal">
                Watched on <c:out value="${param.lastWatched}"/><i class="fas fa-pencil-alt pl-2"
                                                                   aria-hidden="true"></i>
            </button>
        </div>
    </c:if>
    <div class="modal fade" id="editDate${param.id}Modal" tabindex="-1" aria-labelledby="editDateModalLabel"
         aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title font-bold text-2xl" id="editDateModalLabel">Edit <c:out value="${param.mediaTitle}"/> watched date</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    <c:url value="/editWatchedDate" var="editDatePath"/>
                    <form:form method="POST" action="${editDatePath}">
                        <label for="watchedDate">Watched on: </label>
                        <input type="date" id="watchedDate" name="watchedDate" required min="1990-01-01" max="${localDate}">
                        <span class="validity"></span>
                        <input type="hidden" name="username" value="<c:out value="${param.listOwner}"/>">
                        <div class="row">
                            <div class="col-8"></div>
                            <div class="col col-4">
                                <button class="col-auto btn btn-secondary btn-rounded" type="submit">Set new date</button>
                            </div>
                        </div>
                    </form:form>
                </div>
            </div>
        </div>
    </div>
</div>
