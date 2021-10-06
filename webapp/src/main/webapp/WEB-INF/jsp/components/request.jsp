<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<c:url value="/user/${param.currentUsername}/requests/reject" var="rejectPath"/>
<c:url value="/user/${param.currentUsername}/requests/accept" var="acceptPath"/>
<c:url value="/user/${param.currentUsername}" var="userProfilePath"/>
<c:url value="/lists/${param.listId}" var="listPath"/>
<div class="w-full h-20 bg-white overflow-hidden rounded-lg shadow-md flex justify-between mt-2">
    <div class="flex">
        <h4 class="text-base pl-3 py-4 text-xl font-normal tracking-tight">
            <a href="${userProfilePath}" class="text-purple-500 hover:text-purple-900"><c:out value="${param.username}"/></a>
            <spring:message code="profile.requests.collaborate"/> <a href="${listPath}" class="text-purple-500 hover:text-purple-900"><c:out
                value="${param.listname}"/></a>.
        </h4>
    </div>
    <div class="flex justify-between p-3 text-center justify-center items-center">
        <button data-bs-toggle="modal" data-bs-target="#acceptCollabModal"><i class="fas fa-check text-xl text-gray-600 hover:text-green-400 cursor-pointer"
                title="Make collaborator"></i></button>
        <button data-bs-toggle="modal" data-bs-target="#rejectCollabModal"><i class="fas fa-times text-xl text-gray-600 hover:text-red-400 cursor-pointer pl-3"
                   title="Reject collaboration"></i></button>
    </div>
</div>
<%--Modal to decline collaboration--%>
<div>
    <div class="modal fade" id="rejectCollabModal" tabindex="-1" aria-labelledby="rejectCollabModalLabel"
         aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title font-bold text-2xl" id="rejectCollabModalLabel">
                        <spring:message code="modal.rejectCollab.header"/>
                    </h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    <spring:message code="modal.rejectCollab.body"/>
                </div>
                <div class="modal-footer">
                    <form:form cssClass="m-0" action="${rejectPath}" method="DELETE">
                        <input type="hidden" name="collabId" value="${param.collabId}">
                        <button class="btn btn-danger bg-gray-300 hover:bg-red-400 text-gray-700 font-semibold hover:text-white"
                                type="submit">
                            <i class="fas fa-times group-hover:text-white pr-2"></i>
                            <spring:message code="modal.rejectCollab"/>
                        </button>
                    </form:form>
                </div>
            </div>
        </div>
    </div>
</div>
<%--Modal to accept collaboration--%>
<div>
    <div class="modal fade" id="acceptCollabModal" tabindex="-1" aria-labelledby="acceptCollabModalLabel"
         aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title font-bold text-2xl" id="acceptCollabModalLabel">
                        <spring:message code="modal.acceptCollab.header"/>
                    </h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    <spring:message code="modal.acceptCollab.body"/>
                </div>
                <div class="modal-footer">
                    <form:form cssClass="m-0" action="${acceptPath}" method="POST">
                        <input type="hidden" name="collabId" value="${param.collabId}">
                        <button class="btn btn-success bg-gray-300 hover:bg-green-400 text-gray-700 font-semibold hover:text-white"
                                type="submit">
                            <i class="fas fa-check group-hover:text-white pr-2"></i>
                            <spring:message code="modal.acceptCollab"/>
                        </button>
                    </form:form>
                </div>
            </div>
        </div>
    </div>
</div>
</html>
