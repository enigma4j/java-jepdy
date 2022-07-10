
const games=  document.getElementById('games');

if(games)
 fetch('/jepdy/public/api/active')
   .then(response => response.json())
   .then(data => {
       if(!data || data.length==0) {
        games.innerHTML="<tr><td><h6>No Active Games </h6></td></tr>";
       } else {
       console.log(data)
       games.innerHTML=""
        for(var i=0;i<data.length;i++) {
         games.innerHTML+="<tr><td><a class='btn btn-primary' href='/jepdy/public/game/watch/?code="+
                          data[i].code+
                          "'>Watch</a><a class='btn btn-primary' href='/jepdy/secure/game/host?code="+
                          data[i].code+
                          "'>Host</a></td><td><h6>"+
                          data[i].code+
                          "</h6></td><td><h6>"+
                          data[i].name+
                          "<h6></td><td><h6>"
                          +data[i].started+
                          "<h6></td></tr>"
        }
       }

     })
