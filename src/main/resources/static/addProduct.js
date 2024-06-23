// Wait until the DOM is fully loaded
document.addEventListener('DOMContentLoaded', () => {
    //console.log("DOM fully loaded and parsed");

    // Add an event listener to the form with the id 'AddForm'
    //This ensures that the form element exists in the DOM when you try to attach the event listener to it.
    const form = document.querySelector('#AddForm');
    if (form) {
        form.addEventListener('submit', addProduct);
    }

    // Fetch products from the backend and update the HTML when the page loads
    fetchAndUpdateProducts();
    //console.log("Checking popup and buttons on DOMContentLoaded:");
    //console.log(document.getElementById('popup'));
    //console.log(document.getElementById('confirmDelete'));
    //console.log(document.getElementById('cancelDelete'));

});

const itemsPerPage = 10;
let currentPage = 1;
let products = []; // Initialize an empty array to store products

function fetchAndUpdateProducts() {
    console.log("Initiating fetchAndUpdateProducts");
    const csrfToken = getCookie('XSRF-TOKEN');
    console.log("CSRF Token:", csrfToken);

    fetch('https://localhost:8443/api/product', {
        method: 'GET', // Explicitly stating the method for clarity
        credentials: 'include', // Ensures cookies are sent with the request
        headers: {
            'X-XSRF-TOKEN': csrfToken,
            'Content-Type': 'application/json'
        }
    })
    .then(response => {
        console.log("Response status:", response.status);
        console.log("Response headers:", response.headers);
        if (!response.ok) {
            throw new Error(`HTTP error! status: ${response.status}`);
        }
        return response.text();
    })
    .then(text => {
        try {
            return JSON.parse(text);
        } catch (e) {
            console.error('Failed to parse JSON:', text);
            throw new Error('The server response was not valid JSON');
        }
    })
    .then(data => {
         console.log("Parsed data:", data);
        products = data; // Save the product data to a global variable
        renderProducts(); // Render the products to the HTML
        updatePaginationControls(); // Update the pagination controls
    })
    .catch(error => {
        console.error('Error fetching products:', error);
        // Optionally, update the UI to show the error to the user
    });
}

function renderProducts(){
    //example if current page is 1 and products per page is 5, then start is 0 and end is 5, so from 0 to 4 products will be displayed.
    const start = (currentPage - 1) * itemsPerPage;
    // Calculate the end index of the products to display on the current page.
    const end = start + itemsPerPage;
    // Slice the products array to display only the products for the current page, inclusive of the start index and exclusive of the end index.
    const productsToDisplay = products.slice(start, end);

    const tbody = document.querySelector('.recent_order tbody'); // Select the tbody element
            tbody.innerHTML = ''; // Clear any existing content

            productsToDisplay.forEach(product => { // Loop through each product
                const tr = document.createElement('tr'); // Create a new table row
                tr.innerHTML = `
                    <td>${product.id}</td>
                    <td>${product.name}</td>
                    <td>${product.description}</td>
                    <td>${product.price}</td>
                    <td>${product.stockQuantity}</td>
                    <td><button class="white" data-id="${product.id}">X</button></td>
                `; // Set the inner HTML of the table row
                   // Append the table row to the tbody
                tbody.appendChild(tr); 
            });

             // selecting all elements with class white which are the delete buttons.
             const deleteButtons = document.querySelectorAll('.white');
             deleteButtons.forEach(button => {
                //when a delete button is clicked, the showPopup function is called.
                //Placing the event listener setup inside fetchAndUpdateProducts guarantees that the event listeners are correctly attached to the dynamically created delete buttons
                button.addEventListener('click', showPopUp);
             });
}

// Function to handle form submission
function addProduct(event) {
    // Prevent the default form submission behavior
    event.preventDefault();
    // Next step: Capture form data

     //FormData is an object that captures form data as key-value pairs
     //event.target refers to the form element that triggered the event (in this case, the form with the id AddForm).
    const formData = new FormData(event.target);

    console.log("Before sanitization:", {
        name: formData.get('name'),
        description: formData.get('description'),
        price: formData.get('price'),
        stockQuantity: formData.get('stockQuantity')
    });

    //This object 'const productData{}' is created to structure the form data into a format that can be sent to the backend.
    const productData = {
        //names has to match the names of the form fields in the HTML
         name: sanitizeInput(formData.get('name')), // Get the name from the form data
         description: sanitizeInput(formData.get('description')), // Get the description from the form data
         price: sanitizeInput(formData.get('price')), // Get the price from the form data
         stockQuantity: sanitizeInput(formData.get('stockQuantity')) // Get the stock quantity from the form data
    };

     // Log inputs after sanitization
     console.log("After sanitization:", productData);

    // Validation for price and stockQuantity since it wont reach the backend for this type of error, it will be stopped in the front end.
    if (isNaN(productData.price) || productData.price <= 0) {
        document.getElementById('addErrorMessage').textContent = 'Invalid input: price must be a number or number greater than 0';
        return;
    }
    if (isNaN(productData.stockQuantity) || productData.stockQuantity <= 0) {
        document.getElementById('addErrorMessage').textContent = 'Invalid input: stock quantity must be an integer or number greater than 0';
        return;
    }

    const csrfToken = getCookie('XSRF-TOKEN');
          console.log('CSRF Token:', csrfToken); // Debugging

      // Send the new product data to the backend
    fetch(/*'https://qi-fuxing.com/api/productadmin/product'*/ /*'http://localhost:8080/api/productadmin/product'*/ 'https://localhost:8443/api/admin/product', {
        method: 'POST', // Set the request method to POST
        headers: {
            'Content-Type': 'application/json',
            'X-XSRF-TOKEN' : csrfToken
        },
        credentials: 'include',
        body: JSON.stringify(productData) // Convert the product data to a JSON string
    })
    .then(response => {
        if (!response.ok) { // Check if the response status is not OK (error)
            return response.json().then(errorData => { // Parse the JSON error response
                throw new Error(errorData.message || 'Failed to add product'); // Throw an error with the backend message or a default message
            });
        }
        return response.json(); // Parse the JSON response
    })
    .then(() => {
        fetchAndUpdateProducts(); // Fetch and update the product list
        document.getElementById('addErrorMessage').textContent = ''; // Clear any previous error messages
    })
    .catch(error => {
        document.getElementById('addErrorMessage').textContent = error.message; // Display the error message
        console.error('Error adding product:', error); // Log any errors
    });
}

function deleteProduct(event) {
    const productId = event.target.getAttribute('data-id'); // Get the product ID from the button's data attribute

    fetch(/*`https://qi-fuxing.com/api/productadmin/product/${productId}`*/ /*'http://localhost:8080/api/productadmin/product/${productId}'*/
        'https://localhost:8443/api/productadmin/product/${productId}', { // Use the DELETE method to delete the product
        method: 'DELETE',
    })
    .then(response => {
        if (!response.ok) {
            return response.json().then(error => { throw new Error(error.message); });
        }
        // No need to return response.json() because DELETE not returning a JSON body
    })
    .then(() => {
        fetchAndUpdateProducts(); // Refresh the product list after successful deletion
        hidePopup(); // Hide the confirmation popup
    })
    .catch(error => {
        console.error('Error deleting product:', error); // Log any errors
        // Optionally, you can show an error message to the user here
    });
}




