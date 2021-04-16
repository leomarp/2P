//haciendo la referencia al workers
this.addEventListener('message', function(e) {
    //CREATE WEBSOCKET
    let webSocket = new WebSocket("ws://" + location.hostname + ":" + location.port + "/sincronizarForms");
    //WEBSOCKET CONFIG
    webSocket.onopen  = function(e){ console.log("Conectado - status "+this.readyState); };
    webSocket.onclose = function(e) {
        console.log("Desconectado - status " + this.readyState);
    }
    //DATA TO SEND
    var forms = e.data.stg;

    switch (e.data.cmd) {
        case 'sincronizar':
            console.log("Inicio de sincronización . . .");

            try {
                //SEND EACH FORM INDIVIDUALY
                forms.forEach(myFunction)

                function myFunction(item, index, arr) {
                    sendMessage(JSON.stringify(item));
                }

                function sendMessage(msg){
                    // Wait until the state of the socket is not ready and send the message when it is...
                    waitForSocketConnection(webSocket, function(){
                        console.log("message sent!!!");
                        webSocket.send(msg);
                    });
                }

                // Make the function wait until the connection is made...
                function waitForSocketConnection(socket, callback){
                    setTimeout(
                        function () {
                            if (socket.readyState === 1) {
                                console.log("Connection is made")
                                if (callback != null){
                                    callback();
                                }
                            } else {
                                console.log("wait for connection...")
                                waitForSocketConnection(socket, callback);
                            }

                        }, 5); // wait 5 milisecond for the connection...
                }

                console.log("Sincronizado con exito!");
            }
            catch(err) {
                console.log("Error al sincronizar"+err);
                webSocket.close();
            }

            //CLOSE WHEN DONE
            setTimeout(function () {
                webSocket.close();
            },500);

            this.close(); // Termina el worker.
            break;
        default:

    };


    }
    ,false)

