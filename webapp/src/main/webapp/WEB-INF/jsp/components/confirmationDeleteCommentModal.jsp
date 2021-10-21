<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<div>
    <button data-bs-toggle="modal" data-bs-target="#deleteModal">
        <i class="fas fa-times text-right text-gray-400 justify-end hover:text-red-400 cursor-pointer"
           title="<spring:message code="comments.delete"/>"></i>
    </button>
    <div class="modal fade" id="deleteModal" tabindex="-1" aria-labelledby="deleteModalLabel"
         aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title font-bold text-2xl" id="deleteModalLabel">
                        <spring:message code="modal.deleteComment.header"/>
                    </h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    <spring:message code="modal.deleteComment.body"/>
                </div>
                <div class="modal-footer">
                    <c:url value="${param.deletePath}" var="deletePath"/>
                    <form:form action="${deletePath}" method="DELETE">
                        <input type="hidden" name="currentURL" value="${param.currentURL}">
                        <button type="submit" class="btn btn-danger bg-gray-300 group hover:bg-red-400 text-gray-700 font-semibold hover:text-white">
                            <i class="fa fa-trash group-hover:text-white pr-2" aria-hidden="true"></i>
                            <spring:message code="comments.delete"/>
                        </button>
                    </form:form>
                </div>
            </div>
        </div>
    </div>
</div>