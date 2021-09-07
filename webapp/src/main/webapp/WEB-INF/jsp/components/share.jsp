<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<share>
    <div class="flex justify-center py-2">
        <button type="button" class="btn btn-secondary btn-rounded" onclick="copyToClipboard()">
            <i class="fas fa-share-alt pr-2"></i>Share
        </button>
    </div>
    <div class="collapse fixed bottom-0 left-2 z-50" id="notification-container">
        <div class="alert alert-secondary d-flex align-items-center shadow-lg" role="alert">
            <i class="far fa-check-circle pr-2"></i>Link copied to the clipboard.
        </div>
    </div>
</share>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
<script>
    function copyToClipboard() {
        const inputc = document.body.appendChild(document.createElement("input"));
        inputc.value = window.location.href;
        inputc.focus();
        inputc.select();
        document.execCommand('copy');
        inputc.parentNode.removeChild(inputc);
        $('#notification-container').show();
        setTimeout(() => {
            $('#notification-container').hide();
        }, 3000);
    }
</script>
