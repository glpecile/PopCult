<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<div>
    <button data-bs-toggle="modal" data-bs-target="#approve${param.id}ModModal"><i
                class="fas fa-check text-xl text-gray-600 hover:text-green-400 cursor-pointer pl-3"
                title="<spring:message code="mods.promote"/>"></i>
    </button>
    <div class="modal fade" id="approve${param.id}ModModal" tabindex="-1" aria-labelledby="approve${param.id}ModModalLabel"
         aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title font-bold text-2xl" id="approve${param.id}ModModalLabel">
                        <spring:message code="mods.promote"/>
                    </h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    <spring:message code="modal.modsPromote.body"/>
                </div>
                <div class="modal-footer">
                    <c:url value="${param.promoteModPath}" var="promoteModPath"/>
                    <form:form cssClass="m-0" action="${promoteModPath}" method="POST">
                        <button type="submit" name="addMod" class="btn btn-success bg-gray-300 group hover:bg-green-400 text-gray-700 font-semibold hover:text-white">
                            <i class="fas fa-check group-hover:text-white pr-2" aria-hidden="true"></i>
                            <spring:message code="mods.promote"/>
                        </button>
                    </form:form>
                </div>
            </div>
        </div>
    </div>
</div>