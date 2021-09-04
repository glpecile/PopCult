<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<nav class="navbar navbar-expand-lg navbar-dark bg-dark">
    <div class="container-fluid">
        <a class="navbar-brand" href="#">
            <img src="<c:url value='/resources/images/PopCultTextLogo.png'/>"
                 alt="popcult_text_logo" width="15%">
        </a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarScroll" aria-controls="navbarScroll"
                aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarScroll">
            <ul class="navbar-nav me-auto my-2 my-lg-0 navbar-nav-scroll" style="--bs-scroll-height: 100px;">
                <li class="nav-item">
                    <a class="nav-link active" aria-current="page" href="#">Films</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link active" aria-current="page" href="#">Series</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link active" aria-current="page" href="#">Lists</a>
                </li>
            </ul>
        </div>
    </div>
</nav>
<%--<p><c:url value="/resources/images/PopCultTextLogo.png"/></p>--%>