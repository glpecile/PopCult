<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="j" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
    <jsp:include page="/resources/externalResources.jsp"/>
    <!-- favicon -->
    <link rel="shortcut icon" href="<c:url value='/resources/images/favicon.ico'/>" type="image/x-icon">
    <title>Update your Settings &#8226; PopCult</title>
    <c:url value="/${user.username}" var="postPath"/>
</head>
<body>
<jsp:include page="/WEB-INF/jsp/components/navbar.jsp"/>
<br>
<div class="col-8 offset-2">
    <form:form modelAttribute="userSettings" action="${postPath}" method="post"
               class="g-3 p-4 my-8 bg-white shadow-lg rounded-lg">
        <div class="flex flex-col justify-center items-center">

            <div class="py-1 text-semibold w-full">
                <form:label path="name" cssClass="form-label">Name: </form:label>
                <form:input type="text" cssClass="form-control" path="name" value="${user.name}"/>
                <form:errors path="name" cssClass="formError text-red-500" element="p"/>
            </div>

            <div class="py-1 text-semibold w-full">
                <form:label path="username" cssClass="form-label">Username: </form:label>
                <form:input type="text" cssClass="form-control" path="username" value="${user.username}"/>
                <form:errors path="username" cssClass="formError text-red-500" element="p"/>
            </div>

            <div class="py-1 text-semibold w-full">
                <form:label path="email" cssClass="form-label">Email: </form:label>
                <form:input type="text" cssClass="form-control" path="email" value="${user.email}"/>
                <form:errors path="email" cssClass="formError text-red-500" element="p"/>
            </div>
        </div>
        <br>
        <div class="row">
            <div class="col-md-8">
                <button class="btn btn-dark my-2">Change Password</button>
            </div>
            <div class="col-6 col-lg-2">
                <a href="${pageContext.request.contextPath}/${user.username}">
                    <button class="btn btn-light my-2">Discard Changes</button>
                </a>
            </div>
            <div class="col-6 col-lg-2">

                <button class="btn btn-secondary my-2" type="submit">Save Changes</button>
            </div>
        </div>
    </form:form>
</div>
<jsp:include page="/WEB-INF/jsp/components/footer.jsp"/>
</body>
</html>
