<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<c:url value="${param.rejectPath}" var="rejectPath"/>
<c:url value="${param.acceptPath}" var="acceptPath"/>
<c:url value="/user/${param.username}" var="userProfilePath"/>
<c:url value="/lists/${param.listId}" var="listPath"/>
<div class="w-full h-20 bg-white overflow-hidden rounded-lg shadow-md flex justify-between">
    <div class="flex">
        <h4 class="pl-3 py-4 text-xl font-semibold tracking-tight text-gray-800">
            User <a href="${userProfilePath}" class="text-purple-500 hover:text-purple-900"><c:out value="${param.username}"/></a> wants to collaborate in <a href="${listPath}" class="text-purple-500 hover:text-purple-900"><c:out value="${param.listname}"/></a> list
        </h4>
    </div>
    <div class="flex justify-between">
    <form:form cssClass="m-0" action="${acceptPath}" method="POST">
        <button type="submit" name="deleteMedia"><i
                class="fas fa-check text-xl text-gray-800 justify-end p-4 hover:text-green-400 cursor-pointer"
                title="Make collaborator"></i></button>
    </form:form>
    <form:form cssClass="m-0" action="${rejectPath}" method="DELETE">
        <button type="submit" name="deleteMedia"><i
                class="fas fa-times text-xl text-gray-800 justify-end p-4 hover:text-red-400 cursor-pointer"
                title="Reject collaboration"></i></button>
    </form:form>
    </div>
</div>
</html>
