//quitar accion defecto boton derecho
function disableIE() {
	    if (document.all) {
	        return false;
	    }
	}
	function disableNS(e) {
	    if (document.layers || (document.getElementById && !document.all)) {
	        if (e.which==2 || e.which==3) {
	            return false;
	        }
	    }
	}
	if (document.layers) {
	    document.captureEvents(Event.MOUSEDOWN);
	    document.onmousedown = disableNS;
	} 
	else {
	    document.onmouseup = disableNS;
	    document.oncontextmenu = disableIE;
	}
	document.oncontextmenu=new Function("return false");
//fin quitar accion defecto boton derecho

function abrir(fila,columna){
   let form = document.getElementById("myForm");
   form.fila.value=fila;
   form.columna.value=columna;
   console.log(form.fila.value);
   console.log(form.columna.value);
   form.submit();
}
function bandera(fila,columna){
   let form = document.getElementById("myForm");
   form.fila.value=fila;
   form.columna.value=columna;
   let text = form.action
   form.action=text.replace("/a", "/b");
   form.submit();
}