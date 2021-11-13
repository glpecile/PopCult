<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<div class="flex-col flex-wrap p-4 space-x-4">
    <img class="w-36 object-center mx-auto" src="<c:url value="/resources/images/PopCultLogoX.png"/>"
         alt="no_results_image">
    <h1 class="text-xl text-gray-400 py-2 mt-3 text-center">
        <spring:message code="search.sorry" arguments="${param.term}"/>
    </h1>
</div>
