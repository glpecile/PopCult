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
    <title><spring:message code="admin.title"/> &#8226; PopCult</title>
</head>

<body class="bg-gray-50">
<div class="min-h-screen flex flex-col">
    <jsp:include page="/WEB-INF/jsp/components/navbar.jsp"/>
    <div class="col-8 offset-2 flex-grow">
        <!-- Admin Panel message -->
        <h1 class="display-5 fw-bolder py-4">
            <spring:message code="admin.title"/>
        </h1>
        <!-- Lists reports -->
        <div class="flex justify-between">
            <h2 class="font-bold text-2xl pt-2">
                <spring:message code="admin.listReports" arguments="${listReportPageContainer.totalCount}"/>
            </h2>
            <a href="<c:url value="/admin/reports/lists"/>">
                <button class="btn btn-link text-purple-500 hover:text-purple-900 btn-rounded" type="button">
                    <spring:message code="admin.viewAll"/>
                </button>
            </a>
        </div>
        <div class="row">
            <c:forEach var="listReport" items="${listReportPageContainer.elements}">
                <div class="py-2">
                    <jsp:include page="/WEB-INF/jsp/components/listReport.jsp">
                        <jsp:param name="listName" value="${listReport.listName}"/>
                        <jsp:param name="description" value="${listReport.description}"/>
                        <jsp:param name="report" value="${listReport.report}"/>
                        <jsp:param name="reportId" value="${listReport.reportId}"/>
                    </jsp:include>
                </div>
            </c:forEach>
        </div>

        <!-- Lists comments reports -->
        <div class="flex justify-between">
            <h2 class="font-bold text-2xl pt-2">
                <spring:message code="admin.listCommentReports" arguments="${listCommentReportPageContainer.totalCount}"/>
            </h2>
            <a href="<c:url value="/admin/reports/lists/comments"/>">
                <button class="btn btn-link text-purple-500 hover:text-purple-900 btn-rounded" type="button">
                    <spring:message code="admin.viewAll"/>
                </button>
            </a>
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

        <!-- Media comments reports -->
        <div class="flex justify-between">
            <h2 class="font-bold text-2xl pt-2">
                <spring:message code="admin.mediaCommentReports" arguments="${mediaCommentReportPageContainer.totalCount}"/>
            </h2>
            <a href="<c:url value="/admin/reports/media/comments"/>">
                <button class="btn btn-link text-purple-500 hover:text-purple-900 btn-rounded" type="button">
                    <spring:message code="admin.viewAll"/>
                </button>
            </a>
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
    <jsp:include page="/WEB-INF/jsp/components/footer.jsp"/>
</div>
</body>
</html>

