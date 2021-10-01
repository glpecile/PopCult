<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<c:url value="/search" var="searchUrl"/>

<form class="m-0 p-0" action="${searchUrl}" method="get" enctype="application/x-www-form-urlencoded">
    <div class="relative">
        <label class="p-2 text-semibold w-full flex">
            <input class="form-control text-base rounded-full h-8 shadow-sm pl-3 pr-8" type="text" name="term"
                   placeholder="<spring:message code="search.placeholder"/>"/>
            <button class="btn btn-link bg-transparent rounded-full h-8 w-8 p-2 absolute inset-y-3 right-2 flex items-center"
                    type="submit">
                <i class="fas fa-search text-gray-500 hover:text-gray-800 text-center rounded-full mb-2"></i>
            </button>
        </label>
    </div>
</form>

