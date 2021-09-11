<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <jsp:include page="/resources/externalResources.jsp"/>
    <!-- favicon -->
    <link rel="shortcut icon" href="<c:url value='/resources/images/favicon.ico'/>" type="image/x-icon">
    <title>Start a new list &#8226; PopCult</title>
</head>
<body>
<div class="flex flex-col h-screen bg-gray-50">
    <jsp:include page="/WEB-INF/jsp/components/navbar.jsp"/>
    <c:url value="/createList" var="postPath"/>
    <div class="flex-grow col-8 offset-2 py-2">
        <h2 class="font-bold text-2xl">New List</h2>
        <br>
        <form:form modelAttribute="createListForm" action="${postPath}" method="post" class="row g-3">
            <div class="col-md-6">
                <div class="form-outline">
                    <form:input path="listTitle" type="text" class="form-control" id="listName" value=""/>
                    <form:label path="listTitle" for="listName" class="form-label">Name of the list</form:label>
                    <form:errors path="listTitle" cssClass="formError" element="p"/>
                </div>
            </div>
            <div class="col-md-6">
                <div class="form-outline">
                    <form:input path="description" type="text" class="form-control resize" id="listDesc" value=""/>
                    <form:label path="description" for="listDesc" class="form-label">Description</form:label>
                    <form:errors path="description" cssClass="formError" element="p"/>
                </div>
            </div>
<%--            <div class="col-md-4">--%>
<%--                <div class="form-check">--%>
<%--                    <form:input--%>
<%--                            path="visible"--%>
<%--                            class="form-check-input"--%>
<%--                            value=""--%>
<%--                            id="invalidCheck2"--%>
<%--                    />--%>
<%--                    <form:label path="visible" class="form-check-label" for="invalidCheck2">--%>
<%--                        Make list public for everyone.--%>
<%--                    </form:label>--%>
<%--                </div>--%>
<%--            </div>--%>
<%--            <div class="col-md-6">--%>
<%--                <div class="form-check">--%>
<%--                    <form:input--%>
<%--                            path="collaborative"--%>
<%--                            class="form-check-input"--%>
<%--                            value=""--%>
<%--                            id="invalidCheck3"--%>
<%--                    />--%>
<%--                    <form:label path="collaborative" class="form-check-label" for="invalidCheck3">--%>
<%--                        Enable others to suggest new movies to add.--%>
<%--                    </form:label>--%>
<%--                </div>--%>
<%--            </div>--%>
            <div class="col-12">
                <button class="btn btn-secondary" type="submit">Save</button>
            </div>
        </form:form>
    </div>
    <jsp:include page="/WEB-INF/jsp/components/footer.jsp"/>
</div>
</body>
</html>
