<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!doctype html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width,initial-scale=1">
    <meta property="og:image" content="<c:url value="/resources/images/PopCultCompleteLogo.png"/>">
    <jsp:include page="/resources/externalResources.jsp"/>
    <!-- favicon -->
    <link rel="shortcut icon" href="<c:url value='/resources/images/favicon.ico'/>" type="image/x-icon">
    <title><spring:message code="lists.create.title"/> &#8226; PopCult</title>
</head>
<c:url value="/lists/new" var="createListPath"/>
<c:url value="/lists" var="listsPath"/>
<body class="bg-gray-50">
<div class="flex flex-col h-screen bg-gray-50">
    <jsp:include page="/WEB-INF/jsp/components/navbar.jsp"/>
    <div class="flex-grow col-8 offset-2">
        <form:form modelAttribute="createListForm" action="${createListPath}" method="post"
                   class="row g-3 p-2 my-8 bg-white shadow-lg">
            <h2 class="font-bold text-3xl">
                <spring:message code="lists.create.title"/>
            </h2>
            <div class="col-md-6">
                <div>
                    <form:label path="listTitle" for="listName" class="form-label">
                        <spring:message code="lists.create.name"/>
                    </form:label>
                    <form:input path="listTitle" type="text"
                                class="form-control focus:outline-none focus:ring focus:border-purple-300"
                                id="listName"/>
                    <form:errors path="listTitle" cssClass="formError text-red-500" element="p"/>
                </div>
            </div>
            <div>
                <div>
                    <form:label path="description" for="listDesc" class="form-label">
                        <spring:message code="lists.create.desc"/>
                    </form:label>
                    <form:textarea path="description" type="text"
                                   class="form-control h-24 resize-y overflow-clip overflow-auto" id="listDesc"
                                   value=""/>
                    <form:errors path="description" cssClass="formError text-red-500" element="p"/>
                </div>
            </div>
            <div class="col-md-4">
                <div class="form-check">
                    <form:checkbox path="visible" class="form-check-label" for="invalidCheck2"/>
                    <spring:message code="lists.create.public"/>
                </div>
            </div>
            <div class="col-md-6">
                <div class="form-check p-0">
                    <form:checkbox path="collaborative" class="form-check-label" for="invalidCheck3"/>
                    <spring:message code="lists.create.collab"/>
                </div>
            </div>
            <div class="flex justify-between px-4">
                <a href=${listsPath}>
                    <button type="button"
                            class="btn btn-warning bg-gray-300 hover:bg-yellow-400 text-gray-700 font-semibold hover:text-white">
                        <i class="fas fa-undo group-hover:text-white pr-2"></i>
                        <spring:message code="profile.passwordChange.discard"/>
                    </button>
                </a>
                <c:choose>
                    <c:when test="${mediaId == null || mediaId <= 0}">
                        <button class="row btn btn-secondary py-2" type="submit" name="post">
                            <spring:message code="lists.create.confirm"/>
                        </button>
                    </c:when>
                    <c:otherwise>
                        <input type="hidden" name="mediaId" value="<c:out value="${mediaId}"/>">
                        <button class="row btn btn-secondary py-2" type="submit">
                            <spring:message code="lists.create.confirm"/>
                        </button>
                    </c:otherwise>
                </c:choose>
            </div>
        </form:form>
    </div>
    <jsp:include page="/WEB-INF/jsp/components/footer.jsp"/>
</div>
</body>
</html>
