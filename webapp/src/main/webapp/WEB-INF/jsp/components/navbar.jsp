<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<nav class="navbar navbar-expand-lg w-full navbar-dark bg-dark text-white shadow-md bg-gradient-to-r from-yellow-500 to-purple-900">
    <div class="container-fluid">
        <a class="navbar-brand" href="<c:url value="/"/>">
            <img class="transition duration-500 ease-in-out transform hover:-translate-y-1 hover:rotate-2 w-44"
                 src="<c:url value='/resources/images/PopCultTextLogo.png'/>"
                 alt="popcult_text_logo">
        </a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarScroll"
                aria-controls="navbarScroll"
                aria-expanded="false" aria-label="Toggle navigation">
            <i class="fas fa-bars"></i>
        </button>
        <div class="no-scrollbar collapse navbar-collapse flex space-x-4 sm:justify-center" id="navbarScroll">
            <ul class="navbar-nav ms-auto my-2 my-lg-0 navbar-nav-scroll overflow-hidden" style="--bs-scroll-height: 100px;">
                <li class="nav-item transition duration-500 ease-in-out transform hover:-translate-y-1 hover:scale-105">
                    <a class="nav-link active text-lg" aria-current="page" href="<c:url value="/media/films"/>">Films</a>
                </li>
                <li class="nav-item transition duration-500 ease-in-out transform hover:-translate-y-1 hover:scale-105">
                    <a class="nav-link active text-lg" aria-current="page" href="<c:url value="/media/series"/>">Series</a>
                </li>
                <li class="nav-item transition duration-500 ease-in-out transform hover:-translate-y-1 hover:scale-105">
                    <a class="nav-link active text-lg" aria-current="page" href="<c:url value="/lists"/>">Lists</a>
                </li>
                <sec:authorize access="!isAuthenticated()">
                    <li class="nav-item transition duration-500 ease-in-out transform hover:-translate-y-1 hover:scale-105">
                        <a class="nav-link active text-lg" aria-current="page" href="<c:url value="/login"/>">Log-In</a>
                    </li>
                </sec:authorize>
                <sec:authorize access="isAuthenticated()">
                    <c:set var="username">
                        <sec:authentication property="principal.username"/>
                    </c:set>
                    <li class="nav-item transition duration-500 ease-in-out transform hover:-translate-y-1 hover:scale-105">
                        <a class="nav-link active text-lg" aria-current="page" href="<c:url value="/user/${username}"/>">${username}</a>
                    </li>
                </sec:authorize>
            </ul>
        </div>
    </div>
</nav>