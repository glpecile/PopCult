<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<share>
    <div
            class="rounded-full bg-purple-400 hover:bg-purple-300 h-20 w-auto flex items-center justify-center transition duration-500 ease-in-out transform hover:-translate-y-1 hover:scale-105">
        <a class="stretched-link" href="javascript:copyToClipboard()">
            <p class="fas fa-link text-center text-4xl text-white"></p>
        </a>
    </div>
    <p class="text-center py-2">Copy Link</p>
    <div class="collapse fixed bottom-0 left-2 z-50" id="notification-container">
        <div class="alert alert-secondary d-flex align-items-center shadow-lg" role="alert">
            <i class="far fa-check-circle pr-2"></i>Link copied to the clipboard.
        </div>
    </div>
</share>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
<script type="text/javascript" src="<c:url value="/resources/js/components/share.js"/>"></script>
