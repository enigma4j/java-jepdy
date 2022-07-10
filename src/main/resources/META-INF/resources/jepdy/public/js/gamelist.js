fetch('/jepdy/public/api/active')
   .then(response => response.json())
   .then(data => {
     console.log(data)
      const tbody= document.getElementById('games');
      console.log(tbody);
      if(tbody && data) {
        if(data.length==0) {
            tbody.innerHTML='<tr><td colspan="4">No active games</td></tr>'
        }
        else {
            tbody.innerHTML=''
            for(var i=0;i<data.length;i++) {
            }
        }
      }
     })


