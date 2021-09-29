<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<share>
    <div class="flex justify-center py-2">
        <button type="button" class="btn btn-secondary btn-rounded" onclick="copyToClipboard()">
            <i class="fas fa-edit"></i>
            <spring:message code="general.edit"/>
        </button>
    </div>
    <div class="collapse fixed bottom-0 left-2 z-50" id="notification-container">
        <div class="alert alert-secondary d-flex align-items-center shadow-lg" role="alert">
            <i class="far fa-check-circle pr-2"></i>
            <spring:message code="clipboard.copied"/>
        </div>
    </div>
</share>
