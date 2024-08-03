let themeChanger = document.querySelector("#theme_changer");
let baseHtml = document.querySelector("html");

function changeThemeButtonText() {
  let theme = getTheme();
  themeChange(theme, theme);

  themeChanger.addEventListener("click", () => {
    const text = themeChanger.innerText;
    themeChange(text, theme);
  });
}

function themeChange(text, theme) {
  if (text.toLowerCase() === "light") {
    themeChanger.innerText = "Dark";
    baseHtml.className = "light";
    theme = "light";
  } else {
    themeChanger.innerText = "Light";
    baseHtml.className = "dark";
    theme = "dark";
  }
  setTheme(theme);
}

function setTheme(theme) {
  localStorage.setItem("theme", theme);
}

function getTheme() {
  let theme = localStorage.getItem("theme");
  return theme ?? "light";
}

changeThemeButtonText();
