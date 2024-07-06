// Store a flag in sessionStorage to track if this is the initial load
if (!sessionStorage.getItem('pageLoaded')) {
    // Set the flag
    sessionStorage.setItem('pageLoaded', 'true');

    // Log for debugging
    console.log('Initial page load, reloading...');

    // Force a reload
    window.location.reload();
} else {
    // This code will run after the reload
    document.addEventListener('DOMContentLoaded', (event) => {
        const csrfToken = getCookie('XSRF-TOKEN');
        console.log('CSRF Token after reload:', csrfToken);

        // Your other initialization code here
    });
}
