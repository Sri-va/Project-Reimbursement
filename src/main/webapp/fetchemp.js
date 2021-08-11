
    fetch("http://localhost:8080/RestAPI/webapi/controller/employee")
    .then(response => response.json())
    .then(name=>console.log('Name:',name[0]))
    .catch(err=> console.log('Failed',err));
