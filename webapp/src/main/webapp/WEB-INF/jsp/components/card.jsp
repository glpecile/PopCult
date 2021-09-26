<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<c:url value="${param.deletePath}" var="deletePath"/>
<c:url value="${param.addPath}" var="addPath"/>
<div class="card shadow rounded-lg transition duration-500 ease-in-out hover:bg-gray-50 transform hover:-translate-y-1 hover:scale-110 shadow-inner">
    <div class="card-body">
        <img class="card-img-top rounded-lg" src="<c:out value="${param.image}"/>" alt="media_image">
        <c:choose>
            <c:when test="${param.deleteFromListId != null}">
                <div class="flex justify-center py-2 ">
                    <form:form cssClass="m-0" action="${deletePath}" method="DELETE">
                        <button type="submit" name="deleteMedia"
                                class="bg-gray-300 hover:bg-red-400 text-gray-700 font-semibold hover:text-white py-2 px-4 border border-gray-500 hover:border-transparent btn-rounded">
                            <i class="fa fa-trash pr-2" aria-hidden="true"></i>Delete Media
                        </button>
                        <input id="mediaListId" type="hidden" name="mediaListId" value="${param.deleteFromListId}">
                        <input id="mediaId" type="hidden" name="mediaId" value="${param.mediaId}">
                    </form:form>
                </div>
            </c:when>
            <c:when test="${param.addToListId != null}">
                <div class="flex justify-center py-2 ">
                    <form:form cssClass="m-0" action="${addPath}" method="POST">
                        <button type="submit" name="add"
                                class="bg-gray-300 hover:bg-green-400 text-gray-700 font-semibold hover:text-white py-2 px-4 border border-gray-500 hover:border-transparent btn-rounded">
                            <i class="fa fa-trash pr-2" aria-hidden="true"></i>Add Media
                        </button>
                        <input id="mediaListId" type="hidden" name="mediaListId" value="${param.addToListId}">
                        <input id="mediaId" type="hidden" name="mediaId" value="${param.mediaId}">
                    </form:form>
                </div>
            </c:when>
            <c:otherwise>
                <a href="<c:url value="/media/${param.mediaId}"/>" class="stretched-link" title="<c:out
        value="${param.title}"/> (<c:out value="${param.releaseDate}"/>)"></a>
            </c:otherwise>
        </c:choose>
    </div>
</div>
</html>
