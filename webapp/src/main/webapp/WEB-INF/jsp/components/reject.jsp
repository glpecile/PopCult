<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<div>
    <button class="btn btn-danger bg-gray-300 hover:bg-red-400 text-gray-700 font-semibold hover:text-white"
            data-bs-toggle="modal" data-bs-target="#rejectReportModal">
        <i class="far fa-thumbs-down group-hover:text-white pr-2"></i>
        <spring:message code="report.reject"/>
    </button>
    <div class="modal fade" id="rejectReportModal" tabindex="-1" aria-labelledby="rejectReportModalLabel"
         aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title font-bold text-2xl" id="rejectReportModalLabel">
                        <spring:message code="modal.rejectReport.header"/>
                    </h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    <spring:message code="modal.rejectReport.body"/>
                </div>
                <div class="modal-footer">
                    <form class="m-0" action="${param.rejectPath}" method="POST">
                        <button class="btn btn-danger bg-gray-300 hover:bg-red-400 text-gray-700 font-semibold hover:text-white"
                                type="submit" id="rejectReport" name="rejectReport">
                            <i class="far fa-thumbs-down group-hover:text-white pr-2"></i>
                            <spring:message code="report.reject"/>
                        </button>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>
    
