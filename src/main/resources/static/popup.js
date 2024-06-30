window.addEventListener('beforeunload', function (e) {
    console.log('Page is reloading');
});

document.addEventListener('DOMContentLoaded', () => {
    // Event listeners for the popup buttons
    document.getElementById('confirmDelete').addEventListener('click', deleteProduct);
    document.getElementById('cancelDelete').addEventListener('click', hidePopup);
});

//event is automatically passed to the showPopup function by the event listener. It represents the event that occurred (in this case, a click).
function showPopUp(event) {
    console.log('XSRF Token before popup:', getCookie('XSRF-TOKEN'));
    //console.log("showPopup called");

    //when using getElementById, do not use '.popup' as it is an ID, not a class so it should be 'getElementById('popup')'.
    const popUp = document.getElementById('popup');
    const confirmDeleteButton = document.getElementById('confirmDelete'); // Select the confirm delete button

    //console.log(popUp); // Log the popup element
    //console.log(confirmDeleteButton); // Log the button element

    //storing the ID of the product to be deleted in the confirm delete button so that it can be accessed later when the confirmation button is clicked.
    //('data-id', ...) sets the data-id attribute of the button to the product ID.
    //event.target refers to the element that triggered the event (in this case, the delete button) and getting the id from data-id attribute.
    confirmDeleteButton.setAttribute('data-id', event.target.getAttribute('data-id'));

    // Show the popup
    popUp.style.visibility = 'visible';
    console.log('XSRF Token after popup:', getCookie('XSRF-TOKEN'));
}

// Function to hide the confirmation popup
function hidePopup() {
    const popUp = document.getElementById('popup');
    popUp.style.visibility = 'hidden';
}
