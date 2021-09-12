<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<share>
    <div class="flex justify-center py-2">
        <button type="button" class="btn btn-secondary btn-rounded" onclick="copyToClipboard()">
            <i class="fas fa-edit"></i>Edit
        </button>
    </div>
    <div class="collapse fixed bottom-0 left-2 z-50" id="notification-container">
        <div class="alert alert-secondary d-flex align-items-center shadow-lg" role="alert">
            <i class="far fa-check-circle pr-2"></i>Link copied to the clipboard.
        </div>
    </div>
</share>
