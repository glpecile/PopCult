<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<nav>
    <ul class="pagination pagination-circle justify-content-center">
        <c:if test="${param.mediaPages > 1}">
            <%--    Previous button--%>
            <c:choose>
                <c:when test="${param.currentPage == 1}">
                    <li class="page-item disabled">
                        <a class="page-link" href="#">
                            &laquo;
                        </a>
                    </li>
                </c:when>
                <c:otherwise>
                    <li class="page-item ">
                        <a class="page-link" href="<c:url value="${param.url}page=${param.currentPage - 1}"/>">
                            &laquo;
                        </a>
                    </li>
                </c:otherwise>
            </c:choose>

            <%--First page--%>
            <c:if test="${param.currentPage > 2}">
                <li class="page-item">
                    <a class="page-link" href="<c:url value="${param.url}page=1"/>">1</a>
                </li>
            </c:if>
            <c:if test="${param.currentPage > 3}">
                <li class="page-item disabled">
                    <a class="page-link" href="#">...</a>
                </li>
            </c:if>

            <%--    Current page--%>
            <c:forEach var="i" begin="${param.currentPage - 1}" end="${param.currentPage + 1}">
                <c:if test="${i >= 1 && i <= param.mediaPages}">
                    <c:choose>
                        <c:when test="${param.currentPage == i}">
                            <li class="page-item active">
                                <a class="page-link" href="<c:url value="${param.url}">
                                    <c:param name="page" value="${i}"/>
                                </c:url>">
                                    <c:out value="${i}"/>
                                </a>
                            </li>
                        </c:when>
                        <c:otherwise>
                            <li class="page-item">
                                <a class="page-link" href="<c:url value="${param.url}">
                                    <c:param name="page" value="${i}"/>
                                </c:url>">
                                    <c:out value="${i}"/>
                                </a>
                            </li>
                        </c:otherwise>
                    </c:choose>
                </c:if>
            </c:forEach>


            <%--    Last page--%>
            <c:if test="${param.mediaPages > 2 && param.currentPage < param.mediaPages - 2}">
                <li class="page-item disabled">
                    <a class="page-link" href="#">...</a>
                </li>
            </c:if>
            <c:if test="${param.mediaPages > 1 && param.currentPage < param.mediaPages - 1}">
                <li class="page-item">
                    <a class="page-link" href="<c:url value="${param.url}">
                        <c:param name="page" value="${param.mediaPage}"/>
                    </c:url>">
                        <c:out value="${param.mediaPages}"/>
                    </a>
                </li>
            </c:if>

            <%--    Next button--%>
            <c:choose>
                <c:when test="${param.currentPage == param.mediaPages}">
                    <li class="page-item disabled">
                        <a class="page-link" href="#">
                            &raquo;
                        </a>
                    </li>
                </c:when>
                <c:otherwise>
                    <li class="page-item ">
                        <a class="page-link" href="<c:url value="${param.url}page=${param.currentPage + 1}"/>">
                            &raquo;
                        </a>
                    </li>
                </c:otherwise>
            </c:choose>
        </c:if>
    </ul>
</nav>