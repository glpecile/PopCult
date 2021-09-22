<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<nav class="navbar navbar-expand-lg w-full navbar-dark bg-dark text-white shadow-md bg-gradient-to-r from-yellow-500 to-purple-900">
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
        <div class="collapse navbar-collapse flex space-x-8 justify-center items-center text-center sm:justify-end" id="navbarScroll">
            <ul class="navbar-nav ms-auto my-2 my-lg-0 navbar-nav-scroll overflow-hidden">
                <li class="nav-item transition duration-500 ease-in-out transform hover:-translate-y-1 hover:scale-105">
                    <a class="nav-link active text-lg lg:text-right" aria-current="page" href="<c:url value="/media/films"/>">Films</a>
                </li>
                <li class="nav-item transition duration-500 ease-in-out transform hover:-translate-y-1 hover:scale-105">
                    <a class="nav-link active text-lg lg:text-right" aria-current="page" href="<c:url value="/media/series"/>">Series</a>
                </li>
                <li class="nav-item transition duration-500 ease-in-out transform hover:-translate-y-1 hover:scale-105">
                    <a class="nav-link active text-lg lg:text-right" aria-current="page" href="<c:url value="/lists"/>">Lists</a>
                </li>
                <sec:authorize access="!isAuthenticated()">
                    <li class="nav-item transition duration-500 ease-in-out transform hover:-translate-y-1 hover:scale-105">
                        <a class="nav-link active text-lg lg:text-right" aria-current="page" href="<c:url value="/login"/>">Log-In</a>
                    </li>
                </sec:authorize>
                <sec:authorize access="isAuthenticated()">
                    <c:set var="username">
                        <sec:authentication property="principal.username"/>
                    </c:set>
                    <li class="nav-item transition duration-500 ease-in-out transform hover:-translate-y-1 hover:scale-105">
                        <a class="nav-link active text-lg lg:text-right" aria-current="page"
                           href="<c:url value="/user/${username}"/>">${username}</a>
                    </li>
                    <!-- TODO: Fix dropdown. -->
                    <%--                    <li class="nav-item dropdown transition duration-500 ease-in-out transform hover:-translate-y-1 hover:scale-105">--%>
                    <%--                        <a class="text-white text-lg lg:text-right nav-link dropdown-toggle"--%>
                    <%--                           role="button"--%>
                    <%--                           id="dropdownMenuButton"--%>
                    <%--                           data-mdb-toggle="dropdown"--%>
                    <%--                           aria-expanded="false"--%>
                    <%--                        >--%>
                    <%--                                ${username}--%>
                    <%--                        </a>--%>
                    <%--                        <ul class="dropdown-menu" aria-labelledby="dropdownMenuLink">--%>
                    <%--                            <li><a class="dropdown-item" href="<c:url value="/user/${username}"/>">--%>
                    <%--                                Profile--%>
                    <%--                            </a></li>--%>
                    <%--                            <li><a class="dropdown-item" href="<c:url value="/createList"/>">--%>
                    <%--                                Create a List--%>
                    <%--                            </a></li>--%>
                    <%--                            <li><a class="dropdown-item" href="<c:url value="/logout"/>">--%>
                    <%--                                Log-Out--%>
                    <%--                            </a></li>--%>
                    <%--                        </ul>--%>
                    <%--                    </li>--%>
                </sec:authorize>
                <li class="nav-item">
                    <jsp:include page="/WEB-INF/jsp/components/searchInput.jsp"/>
                </li>
            </ul>
        </div>
    </div>
</nav>