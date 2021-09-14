<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<nav class="navbar navbar-expand-lg navbar-dark bg-dark text-white shadow-md bg-gradient-to-r from-yellow-500 to-purple-900">
    <div class="container-fluid">
        <a class="navbar-brand" href="<c:url value="/"/>">
            <img class="transition duration-500 ease-in-out transform hover:-translate-y-1 hover:rotate-2 w-44"
                 src="<c:url value='/resources/images/PopCultTextLogo.png'/>"
                 alt="popcult_text_logo" >
<%--            width="15%"--%>
        </a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarScroll"
                aria-controls="navbarScroll"
                aria-expanded="false" aria-label="Toggle navigation">
            <%--            <span class="navbar-toggler-icon"></span>--%>
            <i class="fas fa-bars"></i>
        </button>
        <div class="no-scrollbar collapse navbar-collapse md:flex md:justify-start sm:flex sm:justify-end" id="navbarScroll">
            <ul
                    class="navbar-nav ms-auto my-2 my-lg-0 navbar-nav-scroll" style="--bs-scroll-height: 100px;">
                <li class="nav-item  transition duration-500 ease-in-out transform hover:-translate-y-1 hover:scale-105">
                    <a class="nav-link active" aria-current="page" href="<c:url value="/media/films"/>">Films</a>
                </li>
                <li class="nav-item  transition duration-500 ease-in-out transform hover:-translate-y-1 hover:scale-105">
                    <a class="nav-link active" aria-current="page" href="<c:url value="/media/series"/>">Series</a>
                </li>
                <li class="nav-item  transition duration-500 ease-in-out transform hover:-translate-y-1 hover:scale-105">
                    <a class="nav-link active" aria-current="page" href="<c:url value="/lists"/>">Lists</a>
                </li>
                <c:choose>
                    <c:when test="${param.user != null}">
                        <li class="nav-item  transition duration-500 ease-in-out transform hover:-translate-y-1 hover:scale-105">
                            <a class="nav-link active" aria-current="page" href="<c:url value="/profile"/>">${param.user}</a>
                        </li>
                    </c:when>
                    <c:otherwise>
                        <li class="nav-item  transition duration-500 ease-in-out transform hover:-translate-y-1 hover:scale-105">
                            <a class="nav-link active" aria-current="page" href="<c:url value="/login"/>">Log-In</a>
                        </li>
                    </c:otherwise>
                </c:choose>

                <%-- TODO Dropdown when user profiles added.--%>
                <%--                <li class="nav-item dropdown">--%>
                <%--                    <a class="nav-link dropdown-toggle" href="#" id="navbarScrollingDropdown" role="button" data-bs-toggle="dropdown"--%>
                <%--                       aria-expanded="false">--%>
                <%--                        User--%>
                <%--                    </a>--%>
                <%--                    <ul class="dropdown-menu" aria-labelledby="navbarScrollingDropdown">--%>
                <%--                        <li><a class="dropdown-item" href="#">Action</a></li>--%>
                <%--                        <li><a class="dropdown-item" href="#">Another action</a></li>--%>
                <%--                        <li>--%>
                <%--                            <hr class="dropdown-divider">--%>
                <%--                        </li>--%>
                <%--                        <li><a class="dropdown-item" href="#">Something else here</a></li>--%>
                <%--                    </ul>--%>
                <%--                </li>--%>
            </ul>
        </div>
    </div>
</nav>