<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<form class="m-0" action="${param.approvePath}" method="POST">
    <button class="btn btn-success bg-gray-300 hover:bg-green-400 text-gray-700 font-semibold group hover:text-white"
            type="submit" id="approveReport" name="approveReport">
        <i class="far fa-thumbs-up group-hover:text-white pr-2"></i>
        <spring:message code="report.approve"/>
    </button>
</form>
