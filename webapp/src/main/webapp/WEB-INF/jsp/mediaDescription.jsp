<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
    <title><c:out value="${media.title}"/> &#8226; PopCult</title>
</head>
<body>
<jsp:include page="/WEB-INF/jsp/components/navbar.jsp"/>
<%--<div class="row justify-content-center">--%>
<%--    <div class="col-8 clearfix">--%>
<%--        <img src="${media.image}" class="img-fluid img-thumbnail float-left mr-2" width="20%" alt="Media Image">--%>
<%--        <div class="list-group list-group-horizontal">--%>
<%--            <h1><c:out value="${media.title}"/></h1>--%>
<%--            <h4 class="mx-3 mt-3">&#8226;</h4>--%>
<%--            <h4 class="mt-3">${media.releaseYear}</h4>--%>
<%--        </div>--%>
<%--        <p class="text-justify">${media.description}</p>--%>
<%--    </div>--%>
<%--</div>--%>
<section class="py-0">
    <div class="container px-4 px-lg-5 my-5">
        <div class="row gx-4 gx-lg-5 align-items-center">
            <div class="col-md-6">
                <img class="img-fluid img-thumbnail card-img-top mb-5 mb-md-0" src="${media.image}" alt="Media Image"/>
            </div>
            <div class="col-md-6">
                <%--                <div class="small mb-1">${media.releaseYear}</div>--%>
                <h1 class="display-5 fw-bolder"><c:out value="${media.title}"/></h1>
                <div class="fs-5 mb-5">
                    <span>${media.releaseYear}</span>
                    <span class="mx-3 mt-3">&#8226;</span>
                    <span>Country</span>
                </div>
                <p class="lead">${media.description}</p>
            </div>
        </div>
    </div>
</section>
</body>
</html>