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

function sendLogoutRequest() {
    const csrfToken = getCookie('XSRF-TOKEN');
    console.log('CSRF Token logout:', csrfToken);

    fetch('https://localhost:8443/api/auth/admin-logout', {
        method: 'POST',
        headers: {
            'X-XSRF-TOKEN': csrfToken
        },
        credentials: 'include',
        redirect: 'follow'
    })
    .then(response => {
        if (response.ok){
            console.log('Logout success, redirecting');
            alert('Logout successful!');
            window.location.href = '/adminLogin.html';
        } else {
            console.error('Logout error');
            throw new Error('Logout failed');
        }
    })
    .catch(error => {
        console.error('Error:', error);
    });
}

document.getElementById('logoutButton').addEventListener('click', function(event){
    event.preventDefault();
    console.log('Logout button clicked');
    sendLogoutRequest();
});

function showErrorMessage(elementId, message) {
    const errorElement = document.getElementById(elementId);
    if (errorElement) {
        errorElement.innerText = message;
        errorElement.style.display = 'block';
    } else {
        console.error('Error element not found:', elementId);
        alert(message); 
    }
}