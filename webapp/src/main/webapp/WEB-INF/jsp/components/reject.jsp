<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<form class="m-0" action="<c:url value="${param.rejectPath}"/>" method="POST">
    <button class="btn btn-danger bg-gray-300 hover:bg-red-400 text-gray-700 font-semibold hover:text-white"
            type="submit" id="rejectReport" name="rejectReport" title="rejectReport">
        <i class="far fa-thumbs-down group-hover:text-white pr-2"></i>
        <spring:message code="report.reject"/>
    </button>
</form>
