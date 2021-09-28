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
        <form:form cssClass="m-0" action="${acceptPath}" method="POST">
            <input type="hidden" name="collabId" value="${param.collabId}">
            <button type="submit" name="deleteMedia"><i
                    class="fas fa-check text-xl text-gray-600 hover:text-green-400 cursor-pointer"
                    title="Make collaborator"></i></button>
        </form:form>
        <form:form cssClass="m-0" action="${rejectPath}" method="DELETE">
            <input type="hidden" name="collabId" value="${param.collabId}">
            <button type="submit" name="deleteMedia"><i
                    class="fas fa-times text-xl text-gray-600 hover:text-red-400 cursor-pointer pl-3"
                    title="Reject collaboration"></i></button>
        </form:form>
    </div>
</div>
</html>
