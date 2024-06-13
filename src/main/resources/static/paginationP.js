function updatePaginationControls() {
    const totalPages = Math.ceil(products.length / itemsPerPage); // Calculate total pages
    const pagination = document.getElementById('pagination'); // Select pagination container
    pagination.innerHTML = ''; // Clear existing pagination buttons

    // Create and append the left arrow button
    const leftArrow = document.createElement('a');
    leftArrow.href = "#";
    leftArrow.innerHTML = '<i class="fal fa-long-arrow-alt-left"></i>';
    leftArrow.addEventListener('click', (event) => {
        event.preventDefault();
        if (currentPage > 1) {
            currentPage--;
            renderProducts();
            updatePaginationControls();
        }
    });
    pagination.appendChild(leftArrow);

    for (let i = 1; i <= totalPages; i++) { // Loop to create page buttons
        //these 3 lines will create all the pages needed for the pagination.
        const pageButton = document.createElement('a'); // Create a new anchor element
        pageButton.textContent = i; // Set text content to page number
        pageButton.href = "#"; // Set href to '#'

        // Add 'active' class to current page but not needed already done.
        //pageButton.className = (i === currentPage) ? 'active' : ''; // Add 'active' class to current page

        // Add click event listener to update currentPage
        pageButton.addEventListener('click', (event) => {
            event.preventDefault(); // Prevent default anchor behavior
            currentPage = i; // Update currentPage to clicked page number
            renderProducts(); // Re-render products for new currentPage
            updatePaginationControls(); // Update pagination controls to reflect the current page
        });


        pagination.appendChild(pageButton); // Append page button to pagination container
    }

    // Create and append the right arrow button
    const rightArrow = document.createElement('a');
    rightArrow.href = "#";
    rightArrow.innerHTML = '<i class="fal fa-long-arrow-alt-right"></i>';
    rightArrow.addEventListener('click', (event) => {
        event.preventDefault();
        if (currentPage < totalPages) {
            currentPage++;
            renderProducts();
            updatePaginationControls();
        }
    });
    pagination.appendChild(rightArrow); // Append right arrow button to pagination container

}