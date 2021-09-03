<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="card shadow">
  <div class="card-body">
    <img class="card-img-top" src="<c:out value="${param.image}"/>" alt="Card image cap">
    <h4 class="card-title"><c:out value="${param.title}"/></h4>
    <h5><c:out value="${param.releaseDate}"/></h5>
    <a href="<c:url value="/media/${param.mediaId}"/>" class="stretched-link"></a>
  </div>
</div>