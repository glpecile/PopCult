<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<div class="p-2.5 m-2.5 gap-2 bg-white shadow-md rounded-lg flex flex-wrap flex-col">
    <p>
        <strong><spring:message code="comment.title"/></strong>
        <br>
        <c:out value="${param.comment}"/>
    </p>
    <hr>
    <p>
        <strong><spring:message code="report.title"/></strong>
        <br>
        <c:out value="${param.report}"/>
    </p>
    <div class="flex justify-between py-2">
        <c:url value="/admin/reports/${param.type}/comments/${param.reportId}" var="path"/>
        <jsp:include page="/WEB-INF/jsp/components/reject.jsp">
            <jsp:param name="reportId" value="${param.reportId}"/>
            <jsp:param name="rejectPath" value="${path}"/>
        </jsp:include>
        <jsp:include page="/WEB-INF/jsp/components/approve.jsp">
            <jsp:param name="reportId" value="${param.reportId}"/>
            <jsp:param name="approvePath" value="${path}"/>
        </jsp:include>
    </div>
</div>
