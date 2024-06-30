//*** login.html ***/ 

// Get the element with the ID "LoginForm" and assign it to the variable LoginForm
var LoginForm = document.getElementById("LoginForm");

// Get the element with the ID "RegForm" and assign it to the variable RegForm
var RegForm = document.getElementById("RegForm");

// Get the element with the ID "Indicator" and assign it to the variable Indicator
var Indicator = document.getElementById("Indicator");

//to clear message when switching between login and register
function clearErrorMessages() {
       // Check if the loginErrorMessage element exists
    if (loginErrorMessage) {
         // Clear the inner text of the loginErrorMessage element
        loginErrorMessage.innerText = "";
        loginErrorMessage.style.display = "none";
    }
    if (regErrorMessage) {
        regErrorMessage.innerText = "";
        regErrorMessage.style.display = "none";
    }
}

function clearInputFields(form) {
    console.log('Clearing input fields for form:', form.id); // Debug log
    var inputFields = form.getElementsByTagName("input");
    for (var i = 0; i < inputFields.length; i++) {
        console.log('Clearing field:', inputFields[i].name); // Debug log
        inputFields[i].value = "";
    }
}

// Define the function register
function register() {
    // Set the transform property of RegForm to translate it to the original position
    RegForm.style.transform = "translateX(0px)";
    // Set the transform property of LoginForm to translate it to the original position
    LoginForm.style.transform = "translateX(0px)";
    // Set the transform property of Indicator to translate it 100 pixels to the right
    Indicator.style.transform = "translateX(100px)";
    // clearInput needs to be called before clearErrorMessages
    clearInputFields(LoginForm);
    clearErrorMessages();
}

// Define the function login
function login() {
    // Set the transform property of RegForm to translate it 300 pixels to the right
    RegForm.style.transform = "translateX(300px)";
    // Set the transform property of LoginForm to translate it 300 pixels to the right
    LoginForm.style.transform = "translateX(300px)";
    // Set the transform property of Indicator to translate it to the original position
    Indicator.style.transform = "translateX(0px)";
    clearInputFields(RegForm);
    clearErrorMessages();
}

// Add an event listener to the element with the ID "Indicator" that listens for a click event and calls the function login
document.getElementById("RegForm").addEventListener("submit", function(event){
    // Prevent the default behavior of the form which is to reload page when pressing submit button
    event.preventDefault();
    // Get the value of the input field with the ID "username" and assign it to the variable username
    const username = event.target[0].value;
    // Get the value of the input field with the ID "password" and assign it to the variable password
    const email = event.target[1].value;
    const password = event.target[2].value;

    // Log the values of the variables username and password to the console
    sendRegisterData({username, email, password});
    clearErrorMessages();
});

document.getElementById("LoginForm").addEventListener("submit", function(event){
    // Prevent the default behavior of the form which is to reload page when pressing submit button
    event.preventDefault();
    console.log("Form submit event triggered"); // Debugging log

    // Get the value of the input field with the ID "username" and assign it to the variable username
    const username = event.target[0].value;
    // Get the value of the input field with the ID "password" and assign it to the variable password
    const password = event.target[1].value;

     console.log("Username:", username); // Debugging log
     console.log("Password:", password); // Debugging log

    sendLoginData({username, password});
    clearErrorMessages();
});
