<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<share>
    <div class="flex flex-col justify-center items-center">
        <div class="rounded-full bg-purple-400 hover:bg-purple-300 w-20 h-20 flex items-center justify-center transition duration-500 ease-in-out transform hover:-translate-y-1 hover:scale-105">
            <a class="stretched-link" href="javascript:copyToClipboard()">
                <p class="fas fa-link text-center text-4xl text-white"></p>
            </a>
        </div>
        <p class="text-center py-2">
            <spring:message code="clipboard.copy"/>
        </p>
    </div>
    <div class="collapse fixed bottom-0 left-2 z-50" id="notification-container">
        <div class="alert alert-secondary d-flex align-items-center shadow-lg" role="alert">
            <i class="far fa-check-circle pr-2"></i><spring:message code="clipboard.copied"/>
        </div>
    </div>
</share>
<script type="text/javascript" src="<c:url value="/resources/js/components/share.js"/>"></script>
