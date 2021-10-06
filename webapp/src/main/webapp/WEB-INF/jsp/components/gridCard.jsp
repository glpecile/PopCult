<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="flex flex-col h-96 bg-white rounded-lg shadow-md group transition duration-500 ease-in-out hover:bg-gray-50 transform hover:-translate-y-1 hover:scale-105">
    <div class="row row-cols-2 mx-0 px-0">
        <div class="col px-0 mx-0"><img class="img-fluid rounded-tl-lg" src="<c:out value="${param.image1}"/>" alt=""></div>
        <div class="col px-0 mx-0"><img class="img-fluid rounded-tr-lg" src="<c:out value="${param.image2}"/>" alt=""></div>
        <div class="col px-0 mx-0"><img class="img-fluid rounded-bl-lg" src="<c:out value="${param.image3}"/>" alt=""></div>
        <div class="col px-0 mx-0"><img class="img-fluid rounded-br-lg" src="<c:out value="${param.image4}"/>" alt=""></div>
    </div>
    <a href="<c:url value="/lists/${param.listId}"/>"
       class="stretched-link text-left tracking-tight align-text-top whitespace-normal text-gray-800 group-hover:text-purple-900 font-medium text-lg p-1.5 m-2.5">
        <c:out value="${param.title}"/>
    </a>
</div>