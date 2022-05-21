function changeLanguagePL() {
    const urlParams = new URLSearchParams(window.location.search);
    urlParams.set('lang', 'pl');
    window.location.search = urlParams;
}

function changeLanguageEN() {
    const urlParams = new URLSearchParams(window.location.search);
    urlParams.set('lang', 'en');
    window.location.search = urlParams;
}