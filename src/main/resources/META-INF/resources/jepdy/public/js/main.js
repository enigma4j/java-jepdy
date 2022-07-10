 const div = document.getElementById('register_option');
 if(div!=null)
    fetch('/jepdy/public/api/userinfo')
   .then(response => response.json())
   .then(data => {
     console.log(data)

     if (data.user ) {
         div.innerHTML=data.user
        console.log("logged in");
     } else {
        div.innerHTML='<a href="/api/login">Login</a>'
         console.log("logged out");
     }
     })

const users=  document.getElementById('users');

if(users)
 fetch('/jepdy/secure/api/admin/users')
   .then(response => response.json())
   .then(data => {
       if(!data || data.length==0) {
        users.innerHTML="<tr><td><p>No users</p></td></tr>";
       } else {
       users.innerHTML=""
        for(var i=0;i<data.length;i++) {
         users.innerHTML+="<tr><td><h6>"+data[i].login+"</h6></td></tr>"
        }
       }

     })


const categories=  document.getElementById('categories');

if(categories)
 fetch('/jepdy/secure/api/admin/categories')
   .then(response => response.json())
   .then(data => {
       if(!data || data.length==0) {
        categories.innerHTML="<tr><td><p>No Categories</p></td></tr>";
       } else {
       console.log(data)
       categories.innerHTML=""
        for(var i=0;i<data.length;i++) {
         categories.innerHTML+="<tr><td><a href='/jepdy/secure/admin/editcategory/"+data[i].id+"'>view</a></td><td><h6>"+data[i].title+"</h6></td><td><h6>"+data[i].clueCount+"</h6></td></tr>"
        }
       }

     })


const clues=  document.getElementById('clues');
const catid=  document.getElementById('catid');
if(clues)
 fetch('/jepdy/secure/api/admin/clues/'+catid.textContent)
   .then(response => response.json())
   .then(data => {
       if(!data || data.length==0) {
        clues.innerHTML="<tr><td><h6>No Clues</h6></td></tr>";
       } else {
       console.log(data)
       clues.innerHTML=""
        for(var i=0;i<data.length;i++) {
         clues.innerHTML+="<tr><td><h6>"+data[i].clue+"<h6></td><td><h6>"+data[i].answer+"<h6></td><td><h6>"+data[i].value+"<h6></td></tr>"
        }
       }

     })






