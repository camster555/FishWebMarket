function showErrorMessage(elementId, message) {
    const errorElement = document.getElementById(elementId);
    errorElement.innerText = message;
    errorElement.style.display = 'block';
}

console.log('authAPI.js loaded');

function sendLoginData(data) {
      // Get the CSRF token from the cookie
      const csrfToken = getCookie('XSRF-TOKEN');
      console.log('CSRF Token:', csrfToken); // Debugging

    // Send a POST request
    fetch(/*'https://qi-fuxing.com/api/auth/login'*/ /*'http://localhost:8080/api/auth/login' */ 'https://localhost:8443/api/auth/login', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'X-XSRF-TOKEN': csrfToken
        },
        //ensures that all credentials, including cookies, are included in the request, enabling the server to store the session information in the browser.
        credentials: 'include',
        // Convert the data object to a JSON string and send it as the body of the request
        body: JSON.stringify(data),
        //this will allow redirect since we are using AJAX, so we need to handle the redirect in the frontend
        redirect: 'follow'
    })
    // Response is from backend server
    //so this is how javascript works, when 'response.json()' is called, it will return a promise and next 'then' will be called when the promise is resolved
    .then(response => {
        //here the error is to handle HTTP errors like exceptions from backend server like is username duplicate and backend sents exception like 4** or 5** status code.
        //checking if the response status code is not in the range 200-299, indicating an error response.
        if (!response.ok) {
            //returns a promise that resolves to the parsed JSON object of the response body. This JSON object typically contains error details.
            return response.json().then(error => { throw new Error(error.message) });
        }

        if (response.redirected) {
            console.log('Redirecting to:', response.url);
            window.location.href = response.url;
            return; // Exit the function as we are redirecting
        }

        return response.json();
    })
    // so data here here resolves the promise returned by 'response.json()'
    .then(data => {
        console.log('Login success',data);
    })
    .catch((error) => {
        // if there is error with the promise, this will be called, this '.catch' is used to handle errors that occur during the fetch process itself such as network errors,
        // or issues parsing the response as JSON
        console.error('Error:', error);
        showErrorMessage('loginErrorMessage', error.message);
    });
}

function sendRegisterData(data){
    fetch(/*'https://qi-fuxing.com/api/auth/register'*/ /*'http://localhost:8080/api/auth/register'*/ 'https://localhost:8443/api/auth/register', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(data),
    })
    .then(response => {
        if (!response.ok) {
            return response.json().then(error => { throw new Error(error.message) });
        }
        return response.json();
    })
    .then(data => {
        console.log('Register success',data);
    })
    .catch((error) => {
        console.error('Error:', error);
        showErrorMessage('regErrorMessage', error.message);
    });
}

