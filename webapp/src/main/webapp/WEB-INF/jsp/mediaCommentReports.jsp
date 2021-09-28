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
    <title><spring:message code="admin.title.mediaCommentReports"/> &#8226; PopCult</title>
</head>

<body class="bg-gray-50">
<jsp:include page="/WEB-INF/jsp/components/navbar.jsp"/>
<div class="col-8 offset-2 py-2">
    <!-- Admin Panel message -->
    <div class="flex flex-col justify-center items-center py-4 mx-auto">
        <h1 class="text-center text-3xl">
            <spring:message code="admin.title.mediaCommentReports"/>
        </h1>
    </div>

    <!-- Media comments reports -->
    <div class="flex justify-between">
        <h2 class="font-bold text-2xl pt-2">
            <spring:message code="admin.mediaCommentReports" arguments="${mediaCommentReportPageContainer.totalCount}"/>
        </h2>
    </div>
    <div class="row">
        <c:forEach var="mediaCommentReport" items="${mediaCommentReportPageContainer.elements}">
            <div class="py-2">
                <jsp:include page="/WEB-INF/jsp/components/commentReport.jsp">
                    <jsp:param name="comment" value="${mediaCommentReport.commentBody}"/>
                    <jsp:param name="report" value="${mediaCommentReport.report}"/>
                    <jsp:param name="type" value="media"/>
                    <jsp:param name="reportId" value="${mediaCommentReport.reportId}"/>
                </jsp:include>
            </div>
        </c:forEach>
    </div>
</div>
<jsp:include page="/WEB-INF/jsp/components/pageNavigation.jsp">
    <jsp:param name="mediaPages" value="${mediaCommentReportPageContainer.totalPages}"/>
    <jsp:param name="currentPage" value="${mediaCommentReportPageContainer.currentPage + 1}"/>
    <jsp:param name="url" value="/admin/reports/media/comments"/>
</jsp:include>
<jsp:include page="/WEB-INF/jsp/components/footer.jsp"/>
</body>
</html>

