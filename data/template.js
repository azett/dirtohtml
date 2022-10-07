function toggle(obj) {
	for (i = 0; i < obj.childNodes.length; i++) {
		if (obj.childNodes[i].nodeName == "UL") {
			if (obj.childNodes[i].style.display == "none") {
				show(obj);
			}
			else { 
				hide(obj);
			} 
			break;
		} 
	} 
} 
function hide(obj) { 
	for (i = 0; i < obj.childNodes.length; i++) { 
		if (obj.childNodes[i].nodeName == "UL") { 
			obj.childNodes[i].style.display = "none"; 
        } 
	} 
} 

function show(obj) { 
	for (i = 0; i < obj.childNodes.length; i++) { 
		if (obj.childNodes[i].nodeName == "UL") { 
			obj.childNodes[i].style.display = "block"; 
        } 
	} 
}