function tweetCurrentPage() {
    let url = removeCookies(encodeURIComponent(window.location.href))
    window.open("https://twitter.com/share?url=" + url + "&text=" + document.title, '_blank');
    // window.location.href = "https://twitter.com/intent/tweet?url=" + encodeURIComponent(window.location.href) + "&text=" + document.title;
    // return false;
}

function whatsappCurrentPage() {
    let url = removeCookies(encodeURIComponent(window.location.href))
    window.open("https://api.whatsapp.com/send/?phone&text=Hey!%20check%20this%20content%20out!%20" + document.title + "%20in%20" + url + "&app_absent=0")
}

function removeCookies(originalURL) {
    let trimmedURL = originalURL.substring(0, originalURL.indexOf("%3B"))
    return trimmedURL ? trimmedURL : originalURL;
}