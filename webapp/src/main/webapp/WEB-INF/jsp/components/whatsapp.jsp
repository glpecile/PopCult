<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div
        class="rounded-full bg-green-400 hover:bg-green-300 h-20 w-auto flex items-center justify-center transition duration-500 ease-in-out transform hover:-translate-y-1 hover:scale-105">
    <a class="stretched-link"
       href="javascript:whatsappCurrentPage()"><p class="fab fa-whatsapp text-4xl text-white"></p></a>
</div>
<p class="text-center py-2">Whatsapp</p>
<script type="text/javascript" src="<c:url value="/resources/js/components/share.js"/>"></script>