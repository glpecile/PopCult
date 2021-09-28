<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="flex flex-col justify-center items-center">
    <div class="rounded-full bg-blue-400 hover:bg-blue-300 w-20 h-20 flex items-center justify-center transition duration-500 ease-in-out transform hover:-translate-y-1 hover:scale-105">
        <a class="stretched-link" href="javascript:tweetCurrentPage()"><p class="fab fa-twitter text-4xl text-white"></p>
        </a>
    </div>
    <p class="text-center py-2">Tweet</p>
</div>
<%--<script async src="https://platform.twitter.com/widgets.js" charset="utf-8"></script>--%>
<script type="text/javascript" src="<c:url value="/resources/js/components/share.js"/>"></script>
