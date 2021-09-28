<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<c:url value="${param.deletePath}" var="deletePath"/>
<div class="p-2.5 m-2.5 mb-0 ring-2 ring-gray-200 rounded-lg flex flex-wrap flex-col">
    <div class="flex flex-row justify-between">
        <a class="font-bold text-purple-500 hover:text-purple-900" href="<c:url value="/user/${param.username}"/>">
            <c:out value="${param.username}"/>
        </a>
        <div class="flex justify-right space-x-2">
            <c:url var="reportPath" value="/report/${param.type}/${param.id}/comment/${param.commentId}"/>
            <a href="${reportPath}">
                <button type="button">
                    <i class="fas fa-exclamation-circle text-right text-gray-400 justify-end hover:text-yellow-400 cursor-pointer"
                       title="Report"></i>
                </button>
            </a>
            <c:if test="${param.commenterId == param.currentUserId}">
                <form:form action="${deletePath}" method="DELETE">
                    <input type="hidden" name="commentId" value="<c:out value="${param.commentId}"/>">
                    <button type="submit">
                        <i class="fas fa-times text-right text-gray-400 justify-end hover:text-red-400 cursor-pointer"
                           title="Delete comment"></i>
                    </button>
                </form:form>
            </c:if>
        </div>
    </div>
    <p>
        <c:out value="${param.comment}"/>
    </p>

</div>
