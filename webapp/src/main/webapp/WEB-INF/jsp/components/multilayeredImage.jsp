<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="card shadow">
    <div class="card-body" style="border-radius: 5%">
        <div class="row row-cols-2 mx-0 px-0">
            <div class="col px-0 mx-0"><img class="img-fluid" src="<c:out value="${param.image}"/>" alt="media_image"></div>
            <div class="col px-0 mx-0"><img class="img-fluid" src="<c:out value="${param.image}"/>" alt="media_image"></div>
            <div class="col px-0 mx-0"><img class="img-fluid" src="<c:out value="${param.image}"/>" alt="media_image"></div>
            <div class="col px-0 mx-0"><img class="img-fluid" src="<c:out value="${param.image}"/>" alt="media_image"></div>
        </div>
    </div>
</div>