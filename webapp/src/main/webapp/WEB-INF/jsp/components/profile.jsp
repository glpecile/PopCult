<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<div class="grid grid-cols-3 gap-4">
    <sec:authorize access="isAuthenticated()">
        <c:set var="currentUsername">
            <sec:authentication property="principal.username"/>
        </c:set>
    </sec:authorize>
    <c:choose>
        <c:when test="${currentUsername == param.username}">
            <div class="flex justify-center">
                <img class="z-0 rounded-full h-40 w-40 flex items-center" alt="profile_image"
                     src="<c:url value="/user/image/${param.imageId}">">
                <button type="button" class="z-10 btn btn-link text-gray-400 hover:text-gray-900 btn-rounded"
                        data-bs-toggle="modal"
                        data-bs-target="#uploadModal">
                    <i class="fas fa-pencil-alt text-gray-500 hover:text-gray-900"></i>
                </button>
            </div>
            <div class="col-span-2">
                <div class="row">
                    <div class="col-auto">
                        <h2 class="text-3xl"><c:out value="${param.name}"/></h2>
                    </div>
                    <div class="col-auto">
                        <a class="object-center" href=<c:url value="/settings"/>>
                            <button title="Edit user data"><i class="fas fa-user-edit text-secondary py-2"></i></button>
                                <%--fa-2x TODO si encontramos la forma de alinear el icono sin padding--%>
                        </a>
                    </div>
                </div>
                <h4>Or as we like to call you: <c:out value="${param.username}"/></h4>
                <div class="flex justify-end">
                    <a href=<c:url value="/logout"/>>
                        <button type="button"
                                class="justify-end btn btn-rounded btn-secondary bg-gray-300 hover:bg-gray-400 text-gray-700 font-semibold hover:text-white">
                            Logout
                        </button>
                    </a>
                </div>
            </div>
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
                            <form:form method="POST" action="${uploadPath}" enctype="multipart/form-data">
                                <div class="row">
                                    <input type="file" name="file"/>
                                    <input type="hidden" name="username" value="<c:out value="${param.username}"/>">
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
            <div class="flex justify-center">
                <img class="z-0 rounded-full h-40 w-40 flex items-center" alt="profile_image"
                     src="${param.profilePicture}">
                <div class="col-span-2">
                    <h2 class="text-3xl pl-2 pb-2"><c:out value="${param.name}"/></h2>
                    <h4 class=" pl-2 ">Also known as: <c:out value="${param.username}"/></h4>
                </div>
            </div>
        </c:otherwise>
    </c:choose>
</div>