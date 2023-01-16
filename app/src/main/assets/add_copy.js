document.addEventListener("click", function(event) {
  var text = event.target.innerText;
  var target = event.target

  if (target.tagName === "P") {
    console.log(text);
    Bridge.copyToClipboard(text);
  }

  if (target.tagName === "LI") {
      let listText = "";
      target.parentElement.childNodes.forEach((item, index, arr) => {
          listText += (index+1)+". "+item.textContent+"\n";
      });
      Bridge.copyToClipboard(listText.trim());
  }
});