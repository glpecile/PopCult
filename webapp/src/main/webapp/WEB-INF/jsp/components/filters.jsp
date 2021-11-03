<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<jsp:useBean id="sortTypes" scope="request" type="java.util.ArrayList"/>
<jsp:useBean id="decadesType" scope="request" type="java.util.ArrayList"/>
<jsp:useBean id="genreTypes" scope="request" type="java.util.ArrayList"/>
<%--<jsp:useBean id="activeGenres" scope="request" type="java.util.Collection"/>--%>

<filters>
    <form:form cssClass="m-0 p-0" modelAttribute="filterForm" action="${param.url}" method="GET">
        <div class="flex flex-row text-center justify-between pb-2.5">
            <div class="flex space-x-3">
                <!-- Sort by -->
                <div class="flex flex-col">
                    <p>
                        <spring:message code="search.sortBy"/>
                    </p>
                    <form:select cssClass="form-select block" path="sortType" items="${sortTypes}"/>
                </div>
                <!-- Decades -->
                <div class="flex flex-col">
                    <p><spring:message code="search.decades"/></p>
                    <form:select cssClass="form-select block" path="decade" items="${decadesType}"/>
                </div>
                <!-- Categories -->
                <div class="dropdown pr-4 flex flex-col">
                    <p>
                        <spring:message code="search.categories"/>
                    </p>
                    <button class="form-select block dropdown-toggle" id="dropdownMenuButton"
                            data-bs-toggle="dropdown"
                            aria-expanded="false">
                        <c:choose>
                            <c:when test="${fn:length(param.genres) == 0}">
                                <spring:message code="All"/>
                            </c:when>
                            <c:when test="${fn:length(param.genres) > 1}">
                                <spring:message code="Multiple"/>
                            </c:when>
                            <c:otherwise>
                                <spring:message code="${param.genres}"/>
                            </c:otherwise>
                        </c:choose>
                    </button>
                    <ul class="dropdown-menu rounded-lg shadow-md" aria-labelledby="dropdownMenuButton">
                        <div class="overflow-y-auto h-64">
                            <div class="flex flex-col p-2.5 space-y-2">
                                <form:checkboxes path="genres" items="${genreTypes}"/>
                            </div>
                        </div>
                    </ul>
                </div>
            </div>
            <!-- Apply and clear buttons -->
            <div class="flex space-x-3 justify-center py-3">
                <button class="btn btn-success bg-gray-300 group hover:bg-green-400 text-gray-700 font-semibold hover:text-white"
                        type="submit">
                    <i class="fas fa-filter group-hover:text-white pr-2"></i>
                    <c:out value="APPLY FILTERS"/>
                </button>
                <button class="btn btn-warning bg-gray-300 group hover:bg-red-400 text-gray-700 font-semibold hover:text-white"
                        type="submit" name="clear" id="clear">
                    <i class="far fa-times-circle group-hover:text-white pr-2"></i>
                    <c:out value="CLEAR FILTERS"/>
                </button>
            </div>
        </div>
    </form:form>
</filters>
