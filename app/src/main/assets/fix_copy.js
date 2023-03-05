//var buttons = document.getElementsByClassName("flex ml-auto gap-2")
//Array.from(buttons).forEach(element => {
//	const bParent = element.parentNode.parentNode
//	const codeBlock = bParent.childNodes[1]
//
//	element.addEventListener("click", e => {
//        Bridge.copyToClipboard(codeBlock.innerText);
//	}, false)
//})

// why navigator.clipboard.writeText doesnt work on android webview https://stackoverflow.com/questions/61243646/clipboard-api-call-throws-notallowederror-without-invoking-onpermissionrequest
navigator.clipboard.writeText = function(a) {
    Bridge.copyToClipboard(a);
}