<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<c:url value="/search" var="searchUrl"/>

<form class="space-y-4" action="${searchUrl}" method="get" enctype="application/x-www-form-urlencoded">
        <div class="flex flex-col">
            <label class="py-2 text-semibold w-full flex">
                <input class="form-control shadow-sm" type="text" name="term" placeholder="<spring:message code="search.placeholder"/>"/>
                <%--            TODO Replace with icon--%>
                <button class="btn btn-secondary my-2 w-full" type="submit">Search</button>
            </label>
    </div>
</form>

