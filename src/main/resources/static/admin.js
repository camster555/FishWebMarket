const sideMenu = document.querySelector('aside'); // Selects the aside element
const menuBtn = document.querySelector('#menu_bar'); // Selects the menu button
const closeBtn = document.querySelector('#close_btn'); // Selects the close button

const themeToggler = document.querySelector('.theme-toggler'); // Selects the theme toggler

menuBtn.addEventListener('click', () => {
  sideMenu.style.display = "block"; // Shows the side menu on menu button click
});

closeBtn.addEventListener('click', () => {
  sideMenu.style.display = "none"; // Hides the side menu on close button click
});

themeToggler.addEventListener('click', () => {
    document.body.classList.toggle('dark-theme-variables'); // Toggles dark theme class on body
  
    themeToggler.querySelector('span:nth-child(1)').classList.toggle('active'); // Toggles active class on first span
    themeToggler.querySelector('span:nth-child(2)').classList.toggle('active'); // Toggles active class on second span
  });
  