<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="j" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<head>
    <jsp:include page="/resources/externalResources.jsp"/>
    <!-- favicon -->
    <link rel="shortcut icon" href="<c:url value='/resources/images/favicon.ico'/>" type="image/x-icon">
    <title><spring:message code="report.comment.title"/> &#8226; PopCult</title>
</head>
<body class="bg-gray-50">
<div class="min-h-screen flex flex-col">
    <jsp:include page="/WEB-INF/jsp/components/navbar.jsp"/>
    <div class="col-8 offset-2 flex-grow">
        <div class="row g-3 p-2 my-8 bg-white shadow-lg rounded-lg">
            <h2 class="text-2xl mb-3"><c:out value="${comment.commentBody}"/></h2>
            <jsp:include page="/WEB-INF/jsp/components/reportForm.jsp">
                <jsp:param name="reportPath" value="/report/${type}/${id}/comment/${comment.commentId}"/>
            </jsp:include>
        </div>
    </div>
    <jsp:include page="/WEB-INF/jsp/components/footer.jsp"/>
</div>
</body>
</html>