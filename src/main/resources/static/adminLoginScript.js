function showErrorMessage(elementId, message) {
    const errorElement = document.getElementById(elementId);
    errorElement.innerText = message;
    errorElement.style.display = 'block';
}

// Add an event listener to the element with the ID "LoginAdminForm" that listens for a submit event and calls the function sendAdminLoginData
document.getElementById("LoginFormAdmin").addEventListener("submit", function(event){
    // Prevent the default behavior of the form which is to reload page when pressing submit button
    event.preventDefault();
    console.log("Admin form submit event triggered"); // Debugging log

    // Get the value of the input field with the ID "username" and assign it to the variable username
    const username = event.target[0].value;
    // Get the value of the input field with the ID "password" and assign it to the variable password
    const password = event.target[1].value;

    console.log("Admin Username:", username); // Debugging log
    console.log("Admin Password:", password); // Debugging log

    sendAdminLoginData({username, password});
});

function sendAdminLoginData(data) {
    const csrfToken = getCookie('XSRF-TOKEN');
    console.log('CSRF Token:', csrfToken); // Debugging

    fetch('https://localhost:8443/api/auth/admin-login', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'X-XSRF-TOKEN': csrfToken
        },
        credentials: 'include',
        body: JSON.stringify(data),

    })
    .then(response => {
      if (response.redirected) {
           window.location.href = response.url; // Handle redirect
           return;
      }
      if (!response.ok) {
           return response.json().then(error => { throw new Error(error.message) });
      }

      return response.json();
    })
    .then(data => {
        console.log('Admin login success', data);
        alert('Admin login successful!');
    })
    .catch((error) => {
        console.error('Error:', error);
        showErrorMessage('adminLoginErrorMessage', error.message || 'An error occurred during login');
    });
}
