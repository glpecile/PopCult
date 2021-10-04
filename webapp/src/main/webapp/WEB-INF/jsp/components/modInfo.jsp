<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<c:url value="/admin/mods/${param.userId}" var="removePath"/>
<c:url value="/user/${param.username}" var="userProfilePath"/>

<div class="w-full h-20 bg-white overflow-hidden rounded-lg shadow-md flex justify-between mt-2">
<%--    TODO centrar--%>
    <div class="flex">
        <img class="inline-block object-cover rounded-full h-10 w-10" alt="profile_image"
             src="<c:url value="/user/image/${param.imageId}"/>">
        <h4 class="pl-3 py-4 text-xl font-normal tracking-tight">
            <a href="${userProfilePath}" class="text-purple-500 hover:text-purple-900"><strong><c:out value="${param.name}"/></strong><br><c:out value="${param.username}"/></a>
        </h4>
    </div>
    <div class="flex justify-between p-3 text-center justify-center items-center">
        <form:form cssClass="m-0" action="${removePath}" method="DELETE">
            <button type="submit" name="removeMod"><i
                    class="fas fa-times text-xl text-gray-600 hover:text-red-400 cursor-pointer pl-3"
                    title="<spring:message code="mods.remove"/>"></i></button>
        </form:form>
    </div>
</div>
</html>
