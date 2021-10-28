<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="j" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!doctype html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width,initial-scale=1">
    <meta property="og:image" content="<c:url value="/resources/images/PopCultCompleteLogo.png"/>">
    <jsp:include page="/resources/externalResources.jsp"/>
    <!-- favicon -->
    <link rel="shortcut icon" href="<c:url value='/resources/images/favicon.ico'/>" type="image/x-icon">
    <title><spring:message code="profile.notifications.title"/> &#8226; PopCult</title>
</head>
<c:url value="/user/${username}/notifications" var="postPath"/>
<body class="bg-gray-50">
<div class="min-h-screen flex flex-col">
    <jsp:include page="/WEB-INF/jsp/components/navbar.jsp"/>
    <br>
    <div class="col-8 offset-2 flex-grow">
        <h2 class="display-5 fw-bolder text-center">
            <spring:message code="profile.panel.title" arguments="${username}"/>
        </h2>
        <jsp:include page="/WEB-INF/jsp/components/notificationTabs.jsp">
            <jsp:param name="username" value="${username}"/>
            <jsp:param name="path" value="notifications"/>
        </jsp:include>
        <div class="flex justify-between">
            <h1 class="font-bold text-2xl pt-2 text-start">
                <spring:message code="profile.notifications.title"/>
            </h1>
            <div class="flex flex-wrap justify-end">
                <form action="${postPath}" method="POST" class="space-x-2">
                    <input type="hidden" name="setOpen" value="">
                    <button type="submit"
                            class="btn btn-link text-purple-500 group hover:text-purple-900 btn-rounded">
                        <i class="fas fa-check-double pr-2 text-purple-500 group-hover:text-purple-900"></i>
                        <spring:message code="profile.notifications.button.read"/>
                    </button>
                </form>
                <form action="${postPath}" method="POST">
                    <input type="hidden" name="deleteNotifications" value="">
                    <button type="submit"
                            class="btn btn-link text-purple-500 group hover:text-purple-900 btn-rounded">
                        <i class="far fa-trash-alt pr-2 text-purple-500 group-hover:text-purple-900"></i>
                        <spring:message code="profile.notifications.button.delete"/>
                    </button>
                </form>
            </div>

        </div>
        <c:if test="${notifications.totalCount == 0}">
            <h3 class="text-center text-gray-400 pt-3">
                <spring:message code="profile.notifications.none"/>
            </h3>
        </c:if>

        <c:forEach var="notification" items="${notifications.elements}">
            <c:if test="${notification.listComment.user.username != username}">
                <div class="w-full h-30 bg-white overflow-hidden rounded-lg shadow-md flex justify-between mt-2 relative">
                    <div class="flex flex-col inline-flex">
                        <h4 class="text-base pl-3 py-4 text-xl font-normal tracking-tight">
                            <a href="<c:url value="/user/${notification.listComment.user.username}" />"
                               class="text-purple-500 hover:text-purple-900"><c:out
                                    value="${notification.listComment.user.username}"/></a>
                            <spring:message code="profile.notifications.comment"/> <a
                                href="<c:url value="/lists/${notification.listComment.mediaList.mediaListId}"/> "
                                class="text-purple-500 hover:text-purple-900"><c:out
                                value="${notification.listComment.mediaList.listName}"/></a>.
                        </h4>
                        <h4 class="text-base pl-3 pb-4 font-normal tracking-tight">"<c:out
                                value="${notification.listComment.commentBody}"/>"</h4>
                        <!-- Notification bubble -->
                        <c:if test="${!notification.opened}">
                        <span class="absolute h-3 w-3 top-0 right-0 mt-2 mr-3">
                            <span class="animate-ping absolute inline-flex h-full w-full rounded-full bg-purple-400 opacity-75 mt-1"></span>
                            <span class="relative inline-flex rounded-full h-3 w-3 bg-purple-500 mb-0.5"></span>
                        </span>
                        </c:if>
                    </div>
                </div>
            </c:if>
        </c:forEach>
    </div>
    <c:url var="baseURL" value=""/>
    <jsp:include page="/WEB-INF/jsp/components/pageNavigation.jsp">
        <jsp:param name="mediaPages" value="${notifications.totalPages}"/>
        <jsp:param name="currentPage" value="${notifications.currentPage + 1}"/>
        <jsp:param name="url" value="${baseURL}"/>
    </jsp:include>
    <jsp:include page="/WEB-INF/jsp/components/footer.jsp"/>
</div>
</body>
</html>