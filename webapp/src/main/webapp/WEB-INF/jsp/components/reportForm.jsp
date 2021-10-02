<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>


<c:url value="${param.reportPath}" var="reportPath"/>
<form:form method="POST" modelAttribute="reportForm" action="${reportPath}">
    <h2 class="text-2xl font-bold py-2"><spring:message code="report.message"/></h2>
    <div>
        <form:textarea path="report" type="text" class="form-control h-24 resize-y overflow-clip overflow-auto" id="report"
                       value=""/>
        <form:errors path="report" cssClass="formError text-red-500" element="p"/>
    </div>
    <br>
    <div class="flex justify-end">
            <%--discard changes--%>
            <%--                <a href=--%>
            <%--                        <c:url value="/"/>>--%>
            <%--                    <button class="btn btn-light my-2" type="button">Discard</button>--%>
            <%--                </a>--%>
            <%--save changes--%>
        <button class="btn btn-secondary my-2" id="addReport" name="addReport" type="submit">
            <i class="far fa-flag pr-2 text-white"></i>
            <spring:message code="report"/>
        </button>
    </div>
</form:form>
