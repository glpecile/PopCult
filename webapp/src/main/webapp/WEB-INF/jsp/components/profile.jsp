<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<div class="flex flex-col gap-4 justify-center items-center">
    <sec:authorize access="isAuthenticated()">
        <c:set var="currentUsername">
            <sec:authentication property="principal.username"/>
        </c:set>
    </sec:authorize>
    <c:choose>
        <c:when test="${currentUsername == param.username}">
            <!-- Profile pic row -->
            <div class="relative inline-block">
                <img class="inline-block object-cover rounded-full h-40 w-40" alt="profile_image"
                     src="<c:url value="/user/image/${param.imageId}"/>">
                <button type="button"
                        class="absolute top-0 right-0 inline-block btn btn-white w-10 h-10 p-0 text-gray-400 hover:text-gray-900 btn-rounded rounded-full"
                        data-bs-toggle="modal"
                        data-bs-target="#uploadModal">
                    <i class="fas fa-pencil-alt text-gray-500"></i>
                </button>
            </div>
            <!-- Username and edit -->
            <div class="flex justify-center items-center space-x-3">
                <h2 class="text-3xl font-bold"><c:out value="${param.name}"/></h2>
                <a class="object-center" href=<c:url value="/settings"/>>
                    <button title="Edit user data">
                        <i class="fas fa-user-edit text-purple-500 hover:text-purple-900 my-2"></i>
                    </button>
                </a>
            </div>
            <h4>Or as we like to call you: <b><c:out value="${param.username}"/></b></h4>
            <a href=<c:url value="/logout"/>>
                <button type="button"
                        class="justify-end btn btn-rounded btn-secondary bg-gray-300 hover:bg-red-400 text-gray-700 font-semibold hover:text-white">
                    Logout
                </button>
            </a>
            <div class="modal fade" id="uploadModal" tabindex="-1" aria-labelledby="uploadModalLabel"
                 aria-hidden="true">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h5 class="modal-title font-bold text-2xl" id="uploadModalLabel">File Upload</h5>
                            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                        </div>
                        <div class="modal-body">
                            <c:url value="/uploadImage" var="uploadPath"/>
                            <form:form method="POST" modelAttribute="imageForm" action="${uploadPath}" enctype="multipart/form-data">
                                <div class="row px-2 pb-2">
                                        <%--                                    <input type="file" name="file"/>--%>
                                        <%--                                    <input type="hidden" name="username" value="<c:out value="${param.username}"/>">--%>
                                    <form:input path="image" cssClass="form-control" type="file" accept="image/*"/>
                                    <form:errors path="image" cssClass="form-control" element="p"/>
                                </div>
                                <div class="row">
                                    <div class="col-8"></div>
                                    <div class="col col-4">
                                        <button class="col-auto btn btn-secondary btn-rounded" type="submit">Upload file
                                        </button>
                                    </div>
                                </div>
                            </form:form>
                        </div>
                    </div>
                </div>
            </div>
        </c:when>
        <c:otherwise>
            <img class="rounded-full h-40 w-40 flex items-center" alt="profile_image"
                 src="<c:url value="/user/image/${param.imageId}"/>">
            <h2 class="text-3xl font-bold"><c:out value="${param.name}"/></h2>
            <h4>Also known as: <b><c:out value="${param.username}"/></b></h4>
        </c:otherwise>
    </c:choose>
</div>