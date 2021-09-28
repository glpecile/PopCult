<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<c:url value="${param.deletePath}" var="deletePath"/>
<div
        class="w-full h-20 bg-white overflow-hidden rounded-lg shadow-md flex justify-between transition duration-500 ease-in-out hover:bg-gray-50 transform hover:-translate-y-1 hover:scale-107">
    <div class="flex">
        <img class="object-cover w-15 h-20" src="<c:out value="${param.image}"/>" alt="media_image"/>
        <h4 class="pl-3 py-4 text-xl font-semibold tracking-tight text-gray-800">
            <c:out value="${param.title}"/> (<c:out value="${param.releaseDate}"/>)
        </h4>
    </div>
    <c:if test="${param.listOwner == param.currUser}">
    <form:form cssClass="m-0" action="${deletePath}" method="DELETE">
        <button type="submit" name="deleteMedia"><i class="fas fa-times text-xl text-gray-800 justify-end p-4 hover:text-red-400 cursor-pointer"
           title="<spring:message code="general.delete"/>"></i></button>
        <input id="mediaListId" type="hidden" name="mediaListId" value="${param.deleteFromListId}">
        <input id="mediaId" type="hidden" name="mediaId" value="${param.mediaId}">
    </form:form>
    </c:if>
</div>
</html>