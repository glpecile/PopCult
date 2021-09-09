<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="flex justify-center py-2">
    <a href="javascript:tweetCurrentPage()"
       class="btn btn-outline-info btn-rounded"><i class="fab fa-twitter pr-2"></i>Tweet</a>
</div>
<%--<script async src="https://platform.twitter.com/widgets.js" charset="utf-8"></script>--%>
<script type="text/javascript" src="<c:url value="/resources/js/components/twitter.js"/>"></script>
