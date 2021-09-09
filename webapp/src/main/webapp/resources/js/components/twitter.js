function tweetCurrentPage() {
    window.location.href = "https://twitter.com/intent/tweet?url=" + encodeURIComponent(window.location.href) + "&text=" + document.title;
    return false;
}