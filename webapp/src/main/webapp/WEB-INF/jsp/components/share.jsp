<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<share>
    <div class="flex justify-center py-2">
        <button type="button" class="btn btn-link text-purple-500 hover:text-purple-900 btn-rounded" data-bs-toggle="modal" data-bs-target="#shareModal">
            <i class="fas fa-share-alt pr-2 text-purple-500 hover:text-purple-900"></i>Share
        </button>
    </div>
    <div class="modal fade" id="shareModal" tabindex="-1" aria-labelledby="shareModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title font-bold text-2xl" id="shareModalLabel">Share</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    <div class="row justify-center items-center">
                        <div class="col">
                            <jsp:include page="/WEB-INF/jsp/components/twitter.jsp"/>
                        </div>
                        <div class="col">
                            <jsp:include page="/WEB-INF/jsp/components/whatsapp.jsp"/>
                        </div>
                        <div class="col">
                            <jsp:include page="/WEB-INF/jsp/components/copyClipboard.jsp"/>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</share>