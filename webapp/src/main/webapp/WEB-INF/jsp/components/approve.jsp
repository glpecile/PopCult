<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<form class="m-0" action="<c:url value="${param.URL}"/>" method="POST">
    <button class="btn btn-success bg-gray-300 hover:bg-green-400 text-gray-700 font-semibold hover:text-white"
            type="submit" id="approveReport" name="approveReport" title="Approve report">
        Approve report
    </button>
</form>
