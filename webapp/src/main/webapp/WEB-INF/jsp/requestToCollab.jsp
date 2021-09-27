<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <jsp:include page="/resources/externalResources.jsp"/>
    <!-- favicon -->
    <link rel="shortcut icon" href="<c:url value='/resources/images/favicon.ico'/>" type="image/x-icon">
    <title>Send a collaboration request &#8226; PopCult</title>
</head>
<c:url value="/lists/${listId}/sendRequest" var="requestPath"/>
<c:url value="/lists/${listId}" var="listPath"/>
<body class="bg-gray-50">
<div class="flex flex-col h-screen bg-gray-50">
    <jsp:include page="/WEB-INF/jsp/components/navbar.jsp"/>
    <div class="flex-grow col-8 offset-2">
        <form:form modelAttribute="requestForm" action="${requestPath}" method="post"
                   class="row g-3 p-2 my-8 bg-white shadow-lg">
            <h2 class="font-bold text-2xl text-center">Send a request to collaborate in this list!</h2>
            <div class="col-md-6">
                <form:label path="message" for="message" class="form-label">Your message</form:label>
                <form:textarea path="message" type="text"
                               class="form-control h-24 resize-y overflow-clip overflow-auto" id="message"
                               value=""/>
                <form:errors path="message" cssClass="formError text-red-500" element="p"/>
            </div>
            <div class="flex justify-center flex-col">
                <form:radiobutton path="type" value="0" label="Add Media"/>
                <form:radiobutton path="type" value="1" label="Add and Remove Media"/>
                <form:radiobutton path="type" value="2" label="Manage Media and Edit Details"/>
            </div>
            <br>
            <div class="flex justify-between px-4">
                <a href=${listPath}>
                    <button type="button"
                            class="btn btn-warning btn btn-danger bg-gray-300 hover:bg-red-400 text-gray-700 font-semibold hover:text-white">
                        Discard changes
                    </button>
                </a>
                <button class="row btn btn-secondary py-2" type="submit">Send request</button>
            </div>
        </form:form>
    </div>
    <jsp:include page="/WEB-INF/jsp/components/footer.jsp"/>
</div>
</body>
</html>
