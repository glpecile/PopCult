<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="card shadow">
    <div class="card-body" style="border-radius: 5%">
        <div class="row row-cols-2 mx-0 px-0">
            <div class="col px-0 mx-0"><img class="img-fluid" src="<c:out value="${param.image1}"/>" alt="media_image"></div>
            <div class="col px-0 mx-0"><img class="img-fluid" src="<c:out value="${param.image2}"/>" alt="" onerror="this.onerror=null; this.src='https://upload.wikimedia.org/wikipedia/en/thumb/3/3b/Pokemon_Trading_Card_Game_cardback.jpg/220px-Pokemon_Trading_Card_Game_cardback.jpg'"></div>
            <div class="col px-0 mx-0"><img class="img-fluid" src="<c:out value="${param.image3}"/>" alt="media_image"></div>
            <div class="col px-0 mx-0"><img class="img-fluid" src="<c:out value="${param.image4}"/>" alt="media_image"></div>
        </div>
        <a href="<c:url value="/lists/${param.listId}"/>" class="stretched-link" title="<c:out value="${param.title}"/>"></a>
    </div>
</div>