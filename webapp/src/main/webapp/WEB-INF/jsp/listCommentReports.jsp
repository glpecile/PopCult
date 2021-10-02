<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!doctype html>
<html lang="en">
<head>
    <jsp:include page="/resources/externalResources.jsp"/>
    <!-- favicon -->
    <link rel="shortcut icon" href="<c:url value='/resources/images/favicon.ico'/>" type="image/x-icon">
    <!-- Local CSS -->
    <link rel="stylesheet" href="<c:url value="/resources/css/overflow.css"/>"/>
    <!-- Local Scripts -->
    <script type="text/javascript" src="<c:url value="/resources/js/components/slider.js"/>"></script>
    <title><spring:message code="admin.title.listCommentReports"/> &#8226; PopCult</title>
</head>

<body class="bg-gray-50">
<div class="min-h-screen flex flex-col">
    <jsp:include page="/WEB-INF/jsp/components/navbar.jsp"/>
    <div class="col-8 offset-2 py-2 flex-grow">
        <!-- Admin Panel message -->
        <h1 class="text-start display-5 fw-bolder py-4">
            <spring:message code="admin.title.listCommentReports"/>
        </h1>
        <!-- Media comments reports -->
        <div class="flex justify-between">
            <h2 class="font-bold text-2xl pt-2">
                <spring:message code="admin.listCommentReports" arguments="${listCommentReportPageContainer.totalCount}"/>
            </h2>
        </div>
        <div class="row">
            <c:forEach var="listCommentReport" items="${listCommentReportPageContainer.elements}">
                <div class="py-2">
                    <jsp:include page="/WEB-INF/jsp/components/commentReport.jsp">
                        <jsp:param name="comment" value="${listCommentReport.commentBody}"/>
                        <jsp:param name="report" value="${listCommentReport.report}"/>
                        <jsp:param name="type" value="lists"/>
                        <jsp:param name="reportId" value="${listCommentReport.reportId}"/>
                    </jsp:include>
                </div>
            </c:forEach>
        </div>
        <jsp:include page="/WEB-INF/jsp/components/pageNavigation.jsp">
            <jsp:param name="mediaPages" value="${listCommentReportPageContainer.totalPages}"/>
            <jsp:param name="currentPage" value="${listCommentReportPageContainer.currentPage + 1}"/>
            <jsp:param name="url" value="/admin/reports/lists/comments"/>
        </jsp:include>
    </div>
    <jsp:include page="/WEB-INF/jsp/components/footer.jsp"/>
</div>
</body>
</html>


