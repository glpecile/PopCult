<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<c:url value="/user/${param.username}" var="userProfilePath"/>
<div class="w-full h-20 bg-white overflow-hidden rounded-lg shadow-md flex justify-between">
    <div class="flex">
        <h4 class="pl-3 py-4 text-xl font-normal tracking-tight">
            <a href="${userProfilePath}" class="text-purple-500 hover:text-purple-900">
                <c:out value="${param.username}"/></a>
            <spring:message code="lists.collab"/>.
        </h4>
    </div>
    <div class="flex justify-between">
        <form:form cssClass="m-0" action="${param.deleteCollabPath}" method="DELETE">
            <input type="hidden" name="collabId" value="<c:out value="${param.collabId}"/>">
            <input type="hidden" name="returnURL" value="<c:out value="${param.returnURL}"/>">
            <button type="submit"><i
                    class="fas fa-times text-xl text-gray-800 justify-end p-4 hover:text-red-400 cursor-pointer"
                    title="<spring:message code="lists.collab.cancel"/>"></i></button>
        </form:form>
    </div>
</div>
</html>
