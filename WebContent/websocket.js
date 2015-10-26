/**
 * Websocket.js
 */

function connect() {
	try {
		socket.onopen = function(){
		}
		
		socket.onmessage = function(msg){
			var point = JSON.parse(event.data);
			store.push(point);
			addDataPoint(point);
		}

		socket.onclose = function(){
		}			
	} catch(exception){
	}
	
	function send(toSend) {
		try {
			socket.send(toSend)
		} catch (exception) {
		}
	}
}

