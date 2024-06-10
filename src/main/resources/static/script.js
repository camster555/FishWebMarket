//*** for all media smaller screen ***/ 

// Get the element with the id 'bar' and assign it to the variable 'bar'
const bar = document.getElementById('bar'); 
// Get the element with the id 'close' and assign it to the variable 'close'
const close = document.getElementById('close'); 
// Get the element with the id 'navbar' and assign it to the variable 'nav'
const nav = document.getElementById('navbar'); 

// Check if the 'bar' element exists
if (bar) { 
    // Add a click event listener to the 'bar' element
    bar.addEventListener('click', () => { 
        // Add the 'active' class to the 'nav' element when 'bar' is clicked
        nav.classList.add('active'); 
    })
}

// Check if the 'close' element exists
if (close) { 
    // Add a click event listener to the 'close' element
    close.addEventListener('click', () => { 
        // Remove the 'active' class from the 'nav' element when 'close' is clicked
        nav.classList.remove('active'); 
    })
}

//*** sProduct.html ***/ 

// Get the element with the id 'MainImg' and assign it to the variable 'MainImg'
var MainImg = document.getElementById("MainImg");

// Get all elements with the class name 'small-img' and assign them to the variable 'smallimg'
var smallimg = document.getElementsByClassName("small-img");

// Set the onclick function for the first 'smallimg' element
smallimg[0].onclick = function() {
    MainImg.src = smallimg[0].src; // Change the src of 'MainImg' to the src of the first 'smallimg'
}

// Set the onclick function for the second 'smallimg' element
smallimg[1].onclick = function() {
    MainImg.src = smallimg[1].src; // Change the src of 'MainImg' to the src of the second 'smallimg'
}

// Set the onclick function for the third 'smallimg' element
smallimg[2].onclick = function() {
    MainImg.src = smallimg[2].src; // Change the src of 'MainImg' to the src of the third 'smallimg'
}

// Set the onclick function for the fourth 'smallimg' element
smallimg[3].onclick = function() {
    MainImg.src = smallimg[3].src; // Change the src of 'MainImg' to the src of the fourth 'smallimg'
}



