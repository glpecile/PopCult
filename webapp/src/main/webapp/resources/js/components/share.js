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