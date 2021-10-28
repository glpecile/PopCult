<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<div>
    <button data-bs-toggle="modal" data-bs-target="#reject${param.id}ModModal">
        <i class="fas fa-times text-xl text-gray-600 hover:text-red-400 cursor-pointer pl-3"
                title="<spring:message code="mods.remove"/>"></i>
    </button>
    <div class="modal fade" id="reject${param.id}ModModal" tabindex="-1" aria-labelledby="reject${param.id}ModModalLabel"
         aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title font-bold text-2xl" id="reject${param.id}ModModalLabel">
                        <spring:message code="mods.remove"/>
                    </h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    <spring:message code="modal.modsReject.body"/>
                </div>
                <div class="modal-footer">
                    <c:url value="${param.removeRequestPath}" var="removeRequestPath"/>
                    <form:form cssClass="m-0" action="${removeRequestPath}" method="DELETE">
                        <input type="hidden" name="removeMod">
                        <button type="submit" class="btn btn-danger bg-gray-300 group hover:bg-red-400 text-gray-700 font-semibold hover:text-white"  name="rejectRequest">
                            <i class="fas fa-times group-hover:text-white pr-2" aria-hidden="true"></i>
                            <spring:message code="mods.remove"/>
                        </button>
                    </form:form>
                </div>
            </div>
        </div>
    </div>
</div>
