// Call this function on initial page load to trigger CSRF token generation
document.addEventListener('DOMContentLoaded', (event) => {
    console.log('Document cookies on initial load:', document.cookie); // Log cookies on initial load
    fetchCsrfToken();
    console.log('Document cookies on after fetchCsrfToken function:', document.cookie); // Log cookies on initial load
});

// Function to fetch the CSRF token on initial page load
function fetchCsrfToken() {
    fetch('https://localhost:8443/api/csrf-token', {
        method: 'GET',
        credentials: 'include'
    })
    .then(response => response.text())
    .then(token => {
        console.log('Fetched CSRF Token:', token);
        // Optionally, manually set the token in a cookie if needed
        document.cookie = `XSRF-TOKEN=${token}; Path=/; Secure; SameSite=None`;
        console.log('Document cookies after setting CSRF token:', document.cookie);
    })
    .catch(error => {
        console.error('Error during initial request:', error);
    });
}

//now it will prevent any XSS attacks like inputting script tags in the input field because the special characters will be transformed into HTML entities
//for example <script> will be transformed into &lt;script&gt;
function sanitizeInput(input) {
    const div = document.createElement('div'); // Create a new div element
    div.textContent = input; // Set the text content of the div to the input
    return div.innerHTML; // Return the sanitized content from the div's innerHTML
}

// split(";") method in JavaScript is used to break a string into an array of substrings and returns the new array.
function getCookie(name) {
    //Log the entire document.cookie to debug
    console.log('Document cookies:', document.cookie);

    // Split document.cookie string into individual cookies, each separated by ';'
    let cookieArr = document.cookie.split(";");

    // Loop through each cookie in the array
    for(let i = 0; i < cookieArr.length; i++) {
        // Split the cookie into a name and value pair, separated by '='
        let cookiePair = cookieArr[i].split("=");

        //after splitting 0 is the name of the cookie and 1 is the value
        // Trim any leading or trailing spaces from the cookie name
        if(name === cookiePair[0].trim()) {
            // Decode the cookie value and return it
            return decodeURIComponent(cookiePair[1]);
        }
    }
    // If no cookie with the specified name is found, return null
    return null;
}
