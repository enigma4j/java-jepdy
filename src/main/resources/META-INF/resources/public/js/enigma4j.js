fetch('/jepdy/public/api/userinfo')
   .then(response => response.json())
   .then(data => {
     console.log(data)
      const div = document.getElementById('register_option');
      if(div) {
     if (data.user ) {
         div.innerHTML=data.user
        console.log("logged in");
     } else {
        div.innerHTML='<a href="/api/login">Login</a>'
         console.log("logged out");
     }
     }
     })


