<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<share>
    <button class="btn btn-default" onclick="copyToClipboard()">
        <i class="fas fa-share"></i>
        Share
    </button>
    <div class="collapse" id="notification-container" style="position: fixed; bottom: 0; left: 1%;">
        <div class="alert alert-secondary d-flex align-items-center " role="alert">
            <i class="far fa-check-circle"></i>Link copied to the clipboard.
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
