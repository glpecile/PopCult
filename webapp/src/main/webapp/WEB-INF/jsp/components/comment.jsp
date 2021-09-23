<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="pb-2">
    <div class="justify-content-start pb-2">
    <p>by: <c:out value="${param.username}"/></p>
    </div>
    <p><c:out value="${param.comment}"/></p>
</div>
