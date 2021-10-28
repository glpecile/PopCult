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
    <title><spring:message code="admin.title.listReports" arguments="${listReportPageContainer.totalCount}"/> &#8226; PopCult</title>
</head>

<body class="bg-gray-50">
<div class="min-h-screen flex flex-col">
    <jsp:include page="/WEB-INF/jsp/components/navbar.jsp"/>
    <div class="col-8 offset-2 py-2 flex-grow">
        <!-- Reports Panel message -->
        <h1 class="text-center display-5 fw-bolder py-4">
            <spring:message code="report.title.plural"/>
        </h1>
        <%--    tabs     --%>
        <jsp:include page="/WEB-INF/jsp/components/reportTabs.jsp">
            <jsp:param name="path" value="listReports"/>
            <jsp:param name="listReports" value="${listReportPageContainer.totalCount}"/>
            <jsp:param name="listCommentsReports" value="${listCommentsReports}"/>
            <jsp:param name="mediaCommentsReports" value="${mediaCommentsReports}"/>
        </jsp:include>
        <!-- Media comments reports -->
        <div class="row">
            <c:forEach var="listReport" items="${listReportPageContainer.elements}">
                <div class="py-2">
                    <jsp:include page="/WEB-INF/jsp/components/listReport.jsp">
                        <jsp:param name="listName" value="${listReport.mediaList.listName}"/>
                        <jsp:param name="description" value="${listReport.mediaList.description}"/>
                        <jsp:param name="report" value="${listReport.report}"/>
                        <jsp:param name="reportId" value="${listReport.reportId}"/>
                    </jsp:include>
                </div>
            </c:forEach>
        </div>
    </div>
    <jsp:include page="/WEB-INF/jsp/components/pageNavigation.jsp">
        <jsp:param name="mediaPages" value="${listReportPageContainer.totalPages}"/>
        <jsp:param name="currentPage" value="${listReportPageContainer.currentPage + 1}"/>
        <jsp:param name="url" value="/admin/reports/lists"/>
    </jsp:include>
    <jsp:include page="/WEB-INF/jsp/components/footer.jsp"/>
</div>
</body>
</html>



