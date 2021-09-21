<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<c:url value="/search" var="searchUrl"/>

<form class="m-0 p-0" action="${searchUrl}" method="get" enctype="application/x-www-form-urlencoded">
    <div class="flex flex-col">
        <label class="p-2 text-semibold w-full flex">
            <input class="form-control rounded-full h-8 shadow-sm" type="text" name="term"
                   placeholder="<spring:message code="search.placeholder"/>"/>
            <button class="btn btn-secondary rounded-full h-8 shadow-sm" type="submit">
                <i class="fas fa-search text-white text-center rounded-full mb-2"></i>
            </button>
        </label>
    </div>
</form>

