<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<share>
    <button class="btn btn-default" onclick="copyToClipboard()">
        <i class="fas fa-share"></i>
        Share
    </button>
</share>
<script>
    function copyToClipboard(text) {
        const inputc = document.body.appendChild(document.createElement("input"));
        inputc.value = window.location.href;
        inputc.focus();
        inputc.select();
        document.execCommand('copy');
        inputc.parentNode.removeChild(inputc);
        let aux = document.querySelector('share')
        aux.innerHTML += '<div class="alert alert-success d-flex align-items-center" role="alert">\n' +
            '        <i class="far fa-check-circle"></i>\n' +
            '        Link copied to the clipboard\n' +
            '        <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>\n' +
            '    </div>'
    }

</script>
