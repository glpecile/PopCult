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

function tweetCurrentPage() {
    window.open("https://twitter.com/share?url=" + encodeURIComponent(window.location.href) + "&text=" + document.title, '_blank');
    // window.location.href = "https://twitter.com/intent/tweet?url=" + encodeURIComponent(window.location.href) + "&text=" + document.title;
    // return false;
}

function whatsappCurrentPage() {
    window.open("https://api.whatsapp.com/send/?phone&text=Hey!%20check%20this%20content%20out!%20" + document.title + "%20in%20" + encodeURIComponent(window.location.href) + "&app_absent=0")
}