<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<c:url value="${param.deletePath}" var="deletePath"/>
<%--<c:url value="${param.addPath}" var="addPath"/>--%>
<div class="flex flex-col h-96 bg-white rounded-lg shadow-md group transition duration-500 ease-in-out hover:bg-gray-50 transform hover:-translate-y-1 hover:scale-105">
    <img class="rounded-t-lg" src="<c:out value="${param.image}"/>" alt="media_image">
    <c:choose>
        <c:when test="${param.deleteFromListId != null}">
            <div class="flex justify-center py-2 ">
                <form:form cssClass="m-0" action="${deletePath}" method="DELETE">
                    <button type="submit" name="deleteMedia"
                            class="bg-gray-300 hover:bg-red-400 text-gray-700 font-semibold hover:text-white py-2 px-4 border border-gray-500 hover:border-transparent btn-rounded">
                        <i class="fa fa-trash pr-2" aria-hidden="true"></i>
                        <spring:message code="general.delete"/>
                    </button>
                    <input id="mediaListId" type="hidden" name="mediaListId" value="${param.deleteFromListId}">
                    <input id="mediaId" type="hidden" name="mediaId" value="${param.mediaId}">
                </form:form>
            </div>
        </c:when>
        <c:otherwise>
            <a href="<c:url value="/media/${param.mediaId}"/>"
               class="stretched-link text-left tracking-tight align-text-top whitespace-normal text-gray-800 group-hover:text-purple-900 font-medium text-lg p-1.5 m-2.5">
                <b><c:out value="${param.title}"/></b> (<c:out value="${param.releaseDate}"/>)
            </a>
        </c:otherwise>
    </c:choose>
</div>
</html>
