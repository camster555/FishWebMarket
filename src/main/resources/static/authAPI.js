function showErrorMessage(elementId, message) {
    const errorElement = document.getElementById(elementId);
    errorElement.innerText = message;
    errorElement.style.display = 'block';
}

function sendLoginData(data) {
    // Send a POST request
    fetch('http://ec2-44-223-24-122.compute-1.amazonaws.com:8080/api/auth/login', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        // Convert the data object to a JSON string and send it as the body of the request
        body: JSON.stringify(data),
    })
    // Response is from backend server
    //so this is how javascript works, when 'reponse.json()' is called, it will return a promise and next 'then' will be called when the promise is resolved
    .then(reponse => {
        //here the error is to handle HTTP errors like exceptions from backend server like is username duplicate and backend sents exception like 4** or 5** status code.
        //checking if the response status code is not in the range 200-299, indicating an error response.
        if (!reponse.ok) {
            //returns a promise that resolves to the parsed JSON object of the response body. This JSON object typically contains error details.
            return reponse.json().then(error => { throw new Error(error.message) });
        }
        return reponse.json();
    })
    // so data here here resolves the promise returned by 'reponse.json()'
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
    fetch('http://ec2-44-223-24-122.compute-1.amazonaws.com:8080/api/auth/login', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(data),
    })
    .then(reponse => {
        if (!reponse.ok) {
            return reponse.json().then(error => { throw new Error(error.message) });
        }
        return reponse.json();
    })
    .then(data => {
        console.log('Register success',data);
    })
    .catch((error) => {
        console.error('Error:', error);
        showErrorMessage('regErrorMessage', error.message);
    });
}

