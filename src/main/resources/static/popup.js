document.addEventListener('DOMContentLoaded', () => {
    // Event listeners for the popup buttons
    document.getElementById('confirmDelete').addEventListener('click', deleteProduct);
    document.getElementById('cancelDelete').addEventListener('click', hidePopup);
});

//event is automatically passed to the showPopup function by the event listener. It represents the event that occurred (in this case, a click).
function showPopUp(event) {
    //console.log("showPopup called"); // Debug log to confirm the function is called

    //when using getElementById, do not use '.popup' as it is an ID, not a class so it should be 'getElementById('popup')'.
    const popUp = document.getElementById('popup'); // Select the popup element
    const confirmDeleteButton = document.getElementById('confirmDelete'); // Select the confirm delete button

    //console.log(popUp); // Log the popup element
    //console.log(confirmDeleteButton); // Log the button element

    //if (!confirmDeleteButton) {
    //    console.error('confirmDeleteButton not found');
    //   return;
    //}

    //storing the ID of the product to be deleted in the confirm delete button so that it can be accessed later when the confirmation button is clicked.
    //('data-id', ...) sets the data-id attribute of the button to the product ID.
    //event.target refers to the element that triggered the event (in this case, the delete button) and getting the id from data-id attribute.
    confirmDeleteButton.setAttribute('data-id', event.target.getAttribute('data-id'));

    // Show the popup
    popUp.style.visibility = 'visible';
}

// Function to hide the confirmation popup
function hidePopup() {
    const popUp = document.getElementById('popup');
    popUp.style.visibility = 'hidden';
}
