<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="p-2.5 m-2.5 mb-0 ring-2 ring-gray-200 rounded-lg flex flex-wrap flex-col">
    <a class="font-bold text-purple-500 hover:text-purple-900" href="<c:url value="/user/${param.username}"/>">
        <c:out value="${param.username}"/>
    </a>
    <p>
        <c:out value="${param.comment}"/>
    </p>
</div>
