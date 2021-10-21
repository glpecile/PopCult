<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<div class="p-2.5 m-2.5 mb-0 ring-2 ring-gray-200 bg-white rounded-lg flex flex-wrap flex-col">
    <div class="flex flex-row justify-between">
        <a class="font-bold text-purple-500 hover:text-purple-900" href="<c:url value="/user/${param.username}"/>">
            <c:out value="${param.username}"/>
        </a>
        <div class="flex justify-right space-x-2">
            <c:url var="reportPath" value="/report/${param.type}/${param.id}/comment/${param.commentId}"/>
            <a href="${reportPath}">
                <button type="button">
                    <i class="fas fa-exclamation-circle text-right text-gray-400 justify-end hover:text-yellow-400 cursor-pointer"
                                              title="<spring:message code="report"/>"></i>

                </button>
            </a>
            <c:if test="${param.commenterId == param.currentUserId}">
                <jsp:include page="/WEB-INF/jsp/components/confirmationDeleteCommentModal.jsp">
                    <jsp:param name="currentURL" value="${param.currentURL}"/>
                    <jsp:param name="deleteCommentPath" value="${param.deletePath}"/>
                </jsp:include>
            </c:if>
        </div>
    </div>
    <p>
        <c:out value="${param.comment}"/>
    </p>

</div>
