<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<nav class="navbar navbar-expand-lg navbar-dark bg-purple-900 shadow-lg backdrop-filter">
    <div class="container-fluid group focus:bg-yellow-600">
        <a class="navbar-brand" href="<c:url value="/"/>">
            <img src="<c:url value='/resources/images/PopCultTextLogo.png'/>"
                 alt="popcult_text_logo" width="15%">
        </a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarScroll" aria-controls="navbarScroll"
                aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarScroll">
            <ul class="navbar-nav ms-auto my-2 my-lg-0 navbar-nav-scroll" style="--bs-scroll-height: 100px;">
                <li class="nav-item">
                    <a class="nav-link active" aria-current="page" href="<c:url value="/media/films"/>">Films</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link active" aria-current="page" href="<c:url value="/media/series"/>">Series</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link active" aria-current="page" href="<c:url value="/lists"/>">Lists</a>
                </li>
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
<%--<p><c:url value="/resources/images/PopCultTextLogo.png"/></p>--%>