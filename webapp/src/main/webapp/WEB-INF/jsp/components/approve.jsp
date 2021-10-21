<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<div>
    <button class="btn btn-success bg-gray-300 hover:bg-green-400 text-gray-700 font-semibold group hover:text-white"
            data-bs-toggle="modal" data-bs-target="#approve${param.reportId}ReportModal">
        <i class="far fa-thumbs-up group-hover:text-white pr-2"></i>
        <spring:message code="report.approve"/>
    </button>
    <div class="modal fade" id="approve${param.reportId}ReportModal" tabindex="-1" aria-labelledby="approve${param.reportId}ReportModalLabel"
         aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title font-bold text-2xl" id="approve${param.reportId}ReportModalLabel">
                        <spring:message code="modal.approveReport.header"/>
                    </h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    <spring:message code="modal.approveReport.body"/>
                </div>
                <div class="modal-footer">
                    <form class="m-0" action="${param.approvePath}" method="POST">
                        <button class="btn btn-success bg-gray-300 hover:bg-green-400 text-gray-700 font-semibold group hover:text-white"
                                type="submit" id="approveReport" name="approveReport">
                            <i class="far fa-thumbs-up group-hover:text-white pr-2"></i>
                            <spring:message code="report.approve"/>
                        </button>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>
<%----%>

