function tweetCurrentPage() {
    window.open("https://twitter.com/share?url=" + encodeURIComponent(window.location.href) + "&text=" + document.title, '_blank');
    // window.location.href = "https://twitter.com/intent/tweet?url=" + encodeURIComponent(window.location.href) + "&text=" + document.title;
    // return false;
}