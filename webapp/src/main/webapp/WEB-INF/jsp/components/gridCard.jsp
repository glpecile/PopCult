<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="card shadow rounded-lg transition duration-500 ease-in-out hover:bg-gray-50 transform hover:-translate-y-1 hover:scale-110 shadow-inner">
    <div class="card-body">
        <div class="row row-cols-2 mx-0 px-0">
            <div class="col px-0 mx-0 rounded-lg"><img class="img-fluid" src="<c:out value="${param.image1}"/>" alt=""></div>
            <div class="col px-0 mx-0 rounded-lg"><img class="img-fluid" src="<c:out value="${param.image2}"/>" alt=""></div>
            <div class="col px-0 mx-0 rounded-lg"><img class="img-fluid" src="<c:out value="${param.image3}"/>" alt=""></div>
            <div class="col px-0 mx-0 rounded-lg"><img class="img-fluid" src="<c:out value="${param.image4}"/>" alt=""></div>
        </div>
        <%--        <div>--%>
        <%--            <h2 class="font-bold pt-2"><c:out value="${param.title}"/></h2>--%>
        <%--        </div>--%>
        <a href="<c:url value="/lists/${param.listId}"/>" class="stretched-link" title="<c:out value="${param.title}"/>"></a>
    </div>
</div>