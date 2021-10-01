<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<c:url value="${param.deletePath}" var="deletePath"/>
<div class="p-2.5 m-2.5 mb-0 ring-2 ring-gray-200 rounded-lg flex flex-wrap flex-col">
    <h4>
        <c:out value="${param.listName}"/>
    </h4>
    <p>
        <c:out value="${param.description}"/>
    </p>
    <hr>
    <p>
        <c:out value="${param.report}"/>
    </p>
    <div class="flex justify-around pt-2">
        <jsp:include page="/WEB-INF/jsp/components/reject.jsp">
            <jsp:param name="rejectPath" value="/admin/reports/lists/${param.reportId}/reject"/>
        </jsp:include>
        <jsp:include page="/WEB-INF/jsp/components/approve.jsp">
            <jsp:param name="approvePath" value="/admin/reports/lists/${param.reportId}/approve"/>
        </jsp:include>
    </div>

</div>
