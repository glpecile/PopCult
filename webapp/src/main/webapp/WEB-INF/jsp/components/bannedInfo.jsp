<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!doctype html>
<html lang="en">
<c:url value="/user/${param.username}" var="userProfilePath"/>
<spring:message code="date.format" var="format"/>
<fmt:parseDate value="${param.unbanDate}" pattern="yyyy-MM-dd" var="parsedDate"/>
<fmt:formatDate value="${parsedDate}" pattern="${format}" var="formattedDate"/>
<div class="w-full h-20 bg-white overflow-hidden rounded-lg shadow-md flex justify-between mt-2">
    <div class="flex">
        <img class="inline-block object-cover rounded-full h-12 w-12 mt-3.5 ml-5" alt="profile_image"
             src="<c:url value="/user/image/${param.imageId}"/>">
        <h4 class="pl-3 text-xl font-normal tracking-tight mt-3.5">
            <a href="${userProfilePath}" class="text-purple-500 hover:text-purple-900"><strong><c:out value="${param.name}"/></strong></a>.
            <spring:message code="profile.otherDescription" arguments="${param.username}"/>
            &#8226; <spring:message code="profile.strikes" arguments="${param.strikes}"/>
            <p class="text-sm text-red-400">
                <spring:message code="profile.unbanDate" arguments="${formattedDate}"/>
            </p>
        </h4>
    </div>
    <div class="flex justify-between p-3 text-center justify-center items-center">
        <jsp:include page="/WEB-INF/jsp/components/unbanUser.jsp">
            <jsp:param name="id" value="${param.userId}"/>
            <jsp:param name="unbanUserPath" value="/admin/bans/${param.userId}"/>
        </jsp:include>
    </div>
</div>
</html>
