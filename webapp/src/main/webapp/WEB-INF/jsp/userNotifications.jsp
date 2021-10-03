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
    <title><spring:message code="profile.requests.title"/> &#8226; PopCult</title>

</head>
<body class="bg-gray-50">
<div class="min-h-screen flex flex-col">
    <jsp:include page="/WEB-INF/jsp/components/navbar.jsp"/>
    <br>
    <div class="col-8 offset-2 flex-grow">
        <h2 class="display-5 fw-bolder"><spring:message code="profile.panel.title" arguments="${username}"/></h2>
        <jsp:include page="/WEB-INF/jsp/components/notificationTabs.jsp">
            <jsp:param name="username" value="${username}"/>
            <jsp:param name="path" value="notifications"/>
        </jsp:include>
        <h1 class="font-bold text-2xl pt-2">
            <spring:message code="profile.notifications.title"/>
        </h1>
        <c:if test="${notifications.totalCount == 0}">
            <h3 class="text-center text-gray-400 pt-3">
                <spring:message code="profile.notifications.none"/>
            </h3>
        </c:if>
        <c:forEach var="notification" items="${notifications.elements}">
            <c:if test="${notification.username != username}">
                <div class="w-full h-30 bg-white overflow-hidden rounded-lg shadow-md flex justify-between mt-2">
                    <div class="flex flex-col">
                        <h4 class="text-base pl-3 py-4 text-xl font-normal tracking-tight">
                            <a href="<c:url value="/user/${notification.username}" />"
                               class="text-purple-500 hover:text-purple-900"><c:out
                                    value="${notification.username}"/></a>
                            <spring:message code="profile.notifications.comment"/> <a
                                href="<c:url value="/lists/${notification.listId}"/> "
                                class="text-purple-500 hover:text-purple-900"><c:out
                                value="${notification.listname}"/></a>.
                        </h4>
                        <h4 class="text-base pl-3 py-4 font-normal tracking-tight">"<c:out
                                value="${notification.commentBody}"/>"</h4>
                    </div>
                </div>
            </c:if>
        </c:forEach>
    </div>
    <jsp:include page="/WEB-INF/jsp/components/footer.jsp"/>
</div>
</body>
</html>