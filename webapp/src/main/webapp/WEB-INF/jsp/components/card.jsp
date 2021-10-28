<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<c:url value="${param.deletePath}" var="deletePath"/>
<div
        class="flex flex-col h-full bg-white rounded-lg shadow-md group transition duration-500 ease-in-out hover:bg-gray-50 transform hover:-translate-y-1 hover:scale-105">
    <!-- Media Image -->
    <img class="img-fluid rounded-t-lg" src="<c:out value="${param.image}"/>" alt="media_image">
    <!-- Media Title -->
    <a href="<c:url value="/media/${param.mediaId}"/>"
       class="stretched-link text-left tracking-tight align-text-top whitespace-normal text-gray-800 group-hover:text-purple-900 font-medium text-lg p-1.5 m-2.5"
       title="<c:out value="${param.title}"/> (<c:out value="${param.releaseDate}"/>)">
        <b><c:out value="${param.title}"/></b> (<c:out value="${param.releaseDate}"/>)
    </a>
</div>
</html>
