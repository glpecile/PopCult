<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<sec:authorize access="hasRole('MOD')">
  <div
          class="absolute bottom-0 right-0 inline-block bg-yellow-500 shadow-md w-auto h-auto font-bold p-2 text-gray-800 btn-rounded rounded-full">
    <spring:message code="profile.mod"/>
  </div>
</sec:authorize>
<sec:authorize access="hasRole('ADMIN')">
  <div
          class="absolute bottom-0 right-0 inline-block bg-purple-900 shadow-md w-auto h-auto font-bold p-2 text-white btn-rounded rounded-full">
    <spring:message code="profile.admin"/>
  </div>
</sec:authorize>
