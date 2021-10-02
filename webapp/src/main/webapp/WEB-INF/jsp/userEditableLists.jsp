<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="j" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<head>
    <jsp:include page="/resources/externalResources.jsp"/>
    <!-- favicon -->
    <link rel="shortcut icon" href="<c:url value='/resources/images/favicon.ico'/>" type="image/x-icon">
    <title>
        <spring:message code="profile.title"/> &#8226; PopCult
    </title>
</head>
<c:url value="/lists/new" var="createListPath"/>
<body class="bg-gray-50">
<jsp:include page="/WEB-INF/jsp/components/navbar.jsp"/>
<br>
<div class="col-8 offset-2">
    <div class="row">
        <div class="flex justify-between">
            <%-- Title --%>
            <h2 class="font-bold text-2xl py-2">
                <spring:message code="editable.title"/>
            </h2>
            <%-- Create List Button --%>
            <a href=${createListPath}>
                <button class="btn btn-link my-2.5 text-purple-500 hover:text-purple-900 btn-rounded">
                    <spring:message code="lists.create"/>
                </button>
            </a>
        </div>
        <div class="row py-2">
        <c:if test="${listContainer.totalCount == 0}">
            <h3 class="text-center text-gray-400">
                <spring:message code="profile.noLists"/>
            </h3>
        </c:if>
            <c:forEach var="cover" items="${covers}">
                <div class="col-12 col-sm-12 col-md-6 col-lg-4 col-xl-3 py-2">
                    <jsp:include page="/WEB-INF/jsp/components/gridCard.jsp">
                        <jsp:param name="title" value="${cover.name}"/>
                        <jsp:param name="listId" value="${cover.listId}"/>
                        <jsp:param name="image1" value="${cover.image1}"/>
                        <jsp:param name="image2" value="${cover.image2}"/>
                        <jsp:param name="image3" value="${cover.image3}"/>
                        <jsp:param name="image4" value="${cover.image4}"/>
                    </jsp:include>
                </div>
            </c:forEach>
        </div>
    </div>
</div>
</body>
</html>