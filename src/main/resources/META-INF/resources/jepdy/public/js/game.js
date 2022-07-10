console.log("watch game")
const code=  document.getElementById('gameid').textContent;
const game=  document.getElementById('game');
const panel=  document.getElementById('panel');

console.log("game code",code)

 // Create WebSocket connection.
        const socket = new WebSocket("ws://localhost:8080//jepdy/public/api/websocket/"+code);
        // Connection opened
        socket.addEventListener('open', function (event) {
            socket.send('Hello Server!');
            console.log("said hello");
        });

        // Listen for messages
        socket.addEventListener('message', function (event) {
            console.log('Message from server', event.data,code);
            if(event.data==code) {
               fetch('/jepdy/public/game/viewstate?code='+code)
               .then(response => response.text())
               .then(data => {
                console.log("got data",data)
                panel.innerHTML=data;
               })
            }
        });
        /*
if(game)

   .then(response => response.json())
   .then(data => {
       if(!data || data.length==0) {
        game.innerHTML="<h6>No Game for this ID</h6>";
       } else {
        game.innerHTML="<h6>there is a game</h6>";
   console.log(data)

       }

     })
*/