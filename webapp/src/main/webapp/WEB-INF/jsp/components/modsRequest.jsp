<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sptring" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<c:url value="/admin/mods/requests/${param.userId}" var="promoteModPath"/>
<c:url value="/admin/mods/requests/${param.userId}" var="removeRequestPath"/>
<c:url value="/user/${param.username}" var="userProfilePath"/>

<div class="w-full h-20 bg-white overflow-hidden rounded-lg shadow-md flex justify-between mt-2">
    <div class="flex">
        <img class="inline-block object-cover rounded-full h-12 w-12 mt-3.5 ml-5" alt="profile_image"
             src="<c:url value="/user/image/${param.imageId}"/>">
        <h4 class="pl-3 py-4 text-xl font-normal tracking-tight">
            <a href="${userProfilePath}" class="text-purple-500 hover:text-purple-900"><strong><c:out value="${param.name}"/></strong></a>.
            <spring:message code="profile.otherDescription"/>
            <a href="${userProfilePath}" class="text-purple-500 hover:text-purple-900">
                <c:out value="${param.username}"/>
            </a>
        </h4>
    </div>
    <div class="flex justify-between p-3 text-center justify-center items-center">
        <form:form cssClass="m-0" action="${promoteModPath}" method="POST">
            <button type="submit" name="addMod"><i
                    class="fas fa-check text-xl text-gray-600 hover:text-green-400 cursor-pointer pl-3"
                    title="<spring:message code="mods.promote"/>"></i></button>
        </form:form>
        <form:form cssClass="m-0" action="${removeRequestPath}" method="DELETE">
            <button type="submit" name="rejectRequest"><i
                    class="fas fa-times text-xl text-gray-600 hover:text-red-400 cursor-pointer pl-3"
                    title="<spring:message code="mods.request.reject"/>"></i></button>
        </form:form>
    </div>
</div>
</html>
