document.addEventListener("click", function(event) {
  if (event.target.tagName === "P") {
    var text = event.target.innerText;
    console.log(text);
    Bridge.copyToClipboard(text);
  }
});