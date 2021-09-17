<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<div class="grid grid-cols-3 gap-4">
    <div class="flex justify-center">
        <img class="rounded-full h-40 w-40 flex items-center" alt="profile_image" src="${param.profilePicture}">
    </div>
    <div class="col-span-2">
        <div class="row">
            <div class="col-auto">
                <h2 class="text-3xl"><c:out value="${param.name}"/></h2>
            </div>
            <div class="col-auto">
                <a class="object-center" href=<c:url value="${pageContext.request.contextPath}/settings"/>>
                    <button title="Edit user data"><i class="fas fa-user-edit text-secondary py-2"></i></button>
                    <%--fa-2x TODO si encontramos la forma de alinear el icono sin padding--%>
                </a>
            </div>
        </div>
        <h4>Or as we like to call you: <c:out value="${param.username}"/></h4>
    </div>
</div>