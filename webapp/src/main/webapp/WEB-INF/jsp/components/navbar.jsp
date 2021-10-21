<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<nav class="relative navbar navbar-expand-lg w-full navbar-dark bg-dark text-white shadow-md bg-gradient-to-r from-yellow-500 to-purple-900">
    <div class="container-fluid flex sm:px-12 px-16">
        <a class="navbar-brand m-0 p-0" href="<c:url value="/"/>">
            <img class="transition duration-500 ease-in-out transform hover:-translate-y-1 hover:rotate-2 w-52"
                 src="<c:url value='/resources/images/PopCultCompleteLogo.png'/>"
                 alt="popcult_text_logo">
        </a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarScroll"
                aria-controls="navbarScroll"
                aria-expanded="false" aria-label="Toggle navigation">
            <i class="fas fa-bars transition duration-500 ease-in-out transform focus:-translate-y-1 focus:scale-105"></i>
        </button>
        <div class="collapse navbar-collapse flex space-x-8 justify-center items-center text-center sm:justify-end"
             id="navbarScroll">
            <ul class="navbar-nav ms-auto my-2 my-lg-0">
                <%--            <ul class="navbar-nav ms-auto my-2 my-lg-0 navbar-nav-scroll overflow-hidden">--%>
                <li class="nav-item transition duration-500 ease-in-out transform hover:-translate-y-1 hover:scale-105">
                    <a class="nav-link active text-lg lg:text-right" aria-current="page"
                       href="<c:url value="/media/films"/>">
                        <spring:message code="nav.films"/>
                    </a>
                </li>
                <li class="nav-item transition duration-500 ease-in-out transform hover:-translate-y-1 hover:scale-105">
                    <a class="nav-link active text-lg lg:text-right" aria-current="page"
                       href="<c:url value="/media/series"/>">
                        <spring:message code="nav.series"/>
                    </a>
                </li>
                <li class="nav-item transition duration-500 ease-in-out transform hover:-translate-y-1 hover:scale-105">
                    <a class="nav-link active text-lg lg:text-right" aria-current="page" href="<c:url value="/lists"/>">
                        <spring:message code="nav.lists"/>
                    </a>
                </li>
                <sec:authorize access="!isAuthenticated()">
                    <li class="nav-item transition duration-500 ease-in-out transform hover:-translate-y-1 hover:scale-105">
                        <a class="nav-link active text-lg lg:text-right" aria-current="page"
                           href="<c:url value="/login"/>">
                            <spring:message code="nav.SignIn"/>
                        </a>
                    </li>
                </sec:authorize>
                <sec:authorize access="isAuthenticated()">
                    <c:set var="username">
                        <sec:authentication property="principal.username"/>
                    </c:set>
                    <li class="z-50 nav-item dropdown transition duration-500 ease-in-out transform hover:-translate-y-1 hover:scale-105">
                        <a class="nav-link dropdown-toggle active text-lg lg:text-right" id="navbarDropdownMenuLink"
                           role="button"
                           data-bs-toggle="dropdown" aria-expanded="false">
                            <c:out value="${username}"/>
                        </a>
                        <ul class="dropdown-menu dropdown-menu-light" aria-labelledby="navbarDropdownMenuLink">
                            <li><a class="dropdown-item" href="<c:url value="/"/>">
                                <spring:message code="nav.drop.home"/> 
                            </a></li>
                            <li><a class="dropdown-item" href="<c:url value="/user/${username}"/>">
                                <spring:message code="nav.drop.profile"/>
                            </a></li>
                            <li><a class="dropdown-item" href="<c:url value="/user/${username}/lists"/>">
                                <spring:message code="nav.drop.lists"/>
                            </a></li>
                            <li><a class="dropdown-item" href="<c:url value="/user/${username}/requests"/>">
                                <spring:message code="nav.drop.notifications"/>
                            </a></li>
                            <sec:authorize access="hasRole('MOD')">
                                <li><a class="dropdown-item" href="<c:url value="/admin"/>">
                                    <spring:message code="nav.drop.admin"/>
                                </a></li>
                            </sec:authorize>
                            <li><a class="dropdown-item" href="<c:url value="/logout"/>">
                                <spring:message code="profile.signOut"/>
                            </a></li>
                        </ul>
                    </li>
                </sec:authorize>
                <li class="nav-item">
                    <jsp:include page="/WEB-INF/jsp/components/searchInput.jsp"/>
                </li>
            </ul>
        </div>
    </div>
</nav>