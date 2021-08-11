var userdata,visitedTab=[];
function fetchEmployee(){
    fetch(`http://localhost:8080/RestAPI/webapi/employee/view/${getCookie()}`)
        .then(response => response.json())
        .then(json => userdata = json)
        .then(() => getUser(userdata))
        .catch(err => console.log('Error',err));

}
function getCookie(){
    
    username='user=';
    let decodedCookie=decodeURIComponent(document.cookie);
    let ca=decodedCookie.split(';');
      
    for(let i=0;i<ca.length;i++){
        let c=ca[i];
        
        while(c.charAt(0) == ' '){
            c=c.substring(1);
            
        }
        if(c.indexOf(username)==0)
            return c.substring(username.length,c.length);
    }
    return '';
}

function getUser(response){

    
    
	document.getElementById('current_user').innerHTML=response[0].username;
    var textnode=document.createTextNode(response[0].id);
    var tdnode=document.getElementById('id');
    tdnode.innerHTML='';
    tdnode.appendChild(textnode);
    var textnode=document.createTextNode(response[0].username);
    var tdnode=document.getElementById('user');
    tdnode.innerHTML='';
    tdnode.appendChild(textnode);
    var textnode=document.createTextNode(response[0].first_name);
    var tdnode=document.getElementById('firstname');
    tdnode.innerHTML='';
    tdnode.appendChild(textnode);
    var textnode=document.createTextNode(response[0].last_name);
    var tdnode=document.getElementById('lastname');
    tdnode.innerHTML='';
    tdnode.appendChild(textnode);
    var textnode=document.createTextNode(response[0].email);
    var tdnode=document.getElementById('email');
    tdnode.innerHTML='';
    tdnode.appendChild(textnode);
    var textnode=document.createTextNode(response[0].phonenum);
    var tdnode=document.getElementById('phone');
    tdnode.innerHTML='';
    tdnode.appendChild(textnode);
}



function updateUser(){
    
   document.getElementById('current_user').innerHTML=userdata[0].username;
    var textnode=document.createTextNode(userdata[0].id);
    var tdnode=document.querySelector('.update-tab-content #id');
    tdnode.innerHTML='';
    tdnode.appendChild(textnode);
    document.querySelector('.update-tab-content #uname').value = userdata[0].username;
    document.querySelector('.update-tab-content #fname').value = userdata[0].first_name;
    document.querySelector('.update-tab-content #lname').value = userdata[0].last_name;
    document.querySelector('.update-tab-content #mail').value = userdata[0].email;
    document.querySelector('.update-tab-content #pass').value = userdata[0].password;
    document.querySelector('.update-tab-content #ph').value = userdata[0].phonenum;
    
}

const tabsName = ['profile', 'update','viewall','pending','solved','rejected','new'];
let selectedTab = tabsName[0];

function changeTab(tabIdx) {
    if (tabIdx >= tabsName.length) {
        return;
    }

    const prevTab = selectedTab;
    const newTab = tabsName[tabIdx];
    selectedTab = newTab;

    document.getElementsByClassName(`${prevTab}-tab`)[0].classList.remove('active');
    document.getElementsByClassName(`${prevTab}-tab-content`)[0].classList.add('d-none');

    document.getElementsByClassName(`${newTab}-tab`)[0].classList.add('active');
    document.getElementsByClassName(`${newTab}-tab-content`)[0].classList.remove('d-none');
}

function updateDB(){
    const user=document.querySelector('.update-tab-content #uname').value;
    const first=document.querySelector('.update-tab-content #fname').value;
    const last=document.querySelector('.update-tab-content #lname').value;
    const mail=document.querySelector('.update-tab-content #mail').value;
    const pass=document.querySelector('.update-tab-content #pass').value;
    const phoneno=document.querySelector('.update-tab-content #ph').value;

    let emp={id:userdata[0].id,uname:user,fname:first,lname:last,email:mail,password:pass,phone:phoneno};
    const request = new Request('http://localhost:8080/RestAPI/webapi/employee/update', {
		method: 'PUT',
		headers: [['Content-Type', 'application/json']],
		body: JSON.stringify(emp)
	});

    fetch(request)
    .then(response=>{
        if(response.status === 200) {
            const toastElement = document.getElementById("liveToast");
            const myToast = bootstrap.Toast.getOrCreateInstance(toastElement);
            myToast.show();
        }
    })
    .catch(err=>console.log('Error',err))

}

function raiseCase(){
    const p=document.querySelector('.new-tab-content #purpose').value;
    const method=document.querySelector('.new-tab-content #method').value;
    const d=document.querySelector('.new-tab-content #date').value;
    const amt=document.querySelector('.new-tab-content #amount').value;
    const id=userdata[0].id;
    let req={purpose:p,paymentMethod:method,date: d,amount:amt,id:id};

    const request = new Request('http://localhost:8080/RestAPI/webapi/employee/new/support/request', {
		method: 'POST',
		headers: [['Content-Type', 'application/json']],
		body: JSON.stringify(req)
	});

    fetch(request)
    .then(response=>{
        if(response.status===200) {
            const toastElement = document.getElementById("liveToast");
        const myToast = bootstrap.Toast.getOrCreateInstance(toastElement);
        myToast.show();
        }
    })
    .catch(err=>console.log('Error',err))
}

function viewRejected(){
    
    if(!visitedTab.includes('rejected')){
        visitedTab.push('rejected');
        let eid=userdata[0].id;
        const request = new Request(`http://localhost:8080/RestAPI/webapi/employee/view/support/rejected/${eid}`);
        fetch(request)
        .then(response=>response.json())
        .then(json=>rejectedData(json))
        .catch(err=>console.log(err));
    }
}

function viewSolved(){
    
    if(!visitedTab.includes('solved')){
        visitedTab.push('solved');
        let eid=userdata[0].id;
        const request = new Request(`http://localhost:8080/RestAPI/webapi/employee/view/support/resolved/${eid}`);
        fetch(request)
        .then(response=>response.json())
        .then(json=>resolvedData(json))
        .catch(err=>console.log(err));
    }
}

function viewPending(){
    
    if(!visitedTab.includes('pending')){
        visitedTab.push('pending');
        let eid=userdata[0].id;
        const request = new Request(`http://localhost:8080/RestAPI/webapi/employee/view/support/pending/${eid}`);
        fetch(request)
        .then(response=>response.json())
        .then(json=>pendingData(json))
        .catch(err=>console.log(err));
    }
}
function viewAll(){
    
    if(!visitedTab.includes('viewall')){
        visitedTab.push('viewall');
        let eid=userdata[0].id;
        const request = new Request(`http://localhost:8080/RestAPI/webapi/employee/view/support/all/${eid}`);
        fetch(request)
        .then(response=>response.json())
        .then(json=>viewallData(json))
        .catch(err=>console.log(err));
    }
    
}

function rejectedData(response){

    const tbody = document.querySelector('.rejected-tab-content table>tbody');
    if(response=='Failed to load'){
        let trnode=document.createElement('tr');
        let showmsg=document.createElement('td');
        showmsg.setAttribute('colspan',8);
        showmsg.setAttribute('style','text-align: center;')
        showmsg.innerHTML='No records found';
        trnode.appendChild(showmsg);
        return tbody.appendChild(trnode);


    }
    response.forEach(val => {
        let trnode=document.createElement('tr');
        
        let sid=document.createElement('td');
        sid.innerHTML=val.sid;
        trnode.appendChild(sid);

        let date=document.createElement('td');
        date.innerHTML=new Date(val.date);
        trnode.appendChild(date);

        let method=document.createElement('td');
        method.innerHTML=val.payment_method;
        trnode.appendChild(method);

        let purpose=document.createElement('td');
        purpose.innerHTML=val.purpose;
        trnode.appendChild(purpose);
        
        let amt=document.createElement('td');
        amt.innerHTML=val.amount;
        trnode.appendChild(amt);

        let exp=document.createElement('td');
        exp.innerHTML=new Date(val.date_of_expense).toDateString();
        trnode.appendChild(exp)


        let status=document.createElement('td');
        status.innerHTML=val.status;
        trnode.appendChild(status);

        let approvedBy=document.createElement('td');
        approvedBy.innerHTML=val.manager.username;
        trnode.appendChild(approvedBy)

        tbody.appendChild(trnode);
    });
}
function resolvedData(response){

    const tbody = document.querySelector('.solved-tab-content table>tbody');
    if(response=='Failed to load'){
        let trnode=document.createElement('tr');
        let showmsg=document.createElement('td');
        showmsg.setAttribute('colspan',8);
        showmsg.setAttribute('style','text-align: center;')
        showmsg.innerHTML='No records found';
        trnode.appendChild(showmsg);
        return tbody.appendChild(trnode);
        


    }
    response.forEach(val => {
        let trnode=document.createElement('tr');
        
        let sid=document.createElement('td');
        sid.innerHTML=val.sid;
        trnode.appendChild(sid);

        let date=document.createElement('td');
        date.innerHTML=new Date(val.date);
        trnode.appendChild(date);

        let method=document.createElement('td');
        method.innerHTML=val.payment_method;
        trnode.appendChild(method);

        let purpose=document.createElement('td');
        purpose.innerHTML=val.purpose;
        trnode.appendChild(purpose);
        
        let amt=document.createElement('td');
        amt.innerHTML=val.amount;
        trnode.appendChild(amt);

        let exp=document.createElement('td');
        exp.innerHTML=new Date(val.date_of_expense).toDateString();
        trnode.appendChild(exp)


        let status=document.createElement('td');
        status.innerHTML=val.status;
        trnode.appendChild(status);

        let approvedBy=document.createElement('td');
        approvedBy.innerHTML=val.manager.username;
        trnode.appendChild(approvedBy);

        tbody.appendChild(trnode);
    });
}
function pendingData(response){

    const tbody = document.querySelector('.pending-tab-content table>tbody');
    if(response=='Failed to load'){
        let trnode=document.createElement('tr');
        let showmsg=document.createElement('td');
        showmsg.setAttribute('colspan',7);
        showmsg.innerHTML='No records found';
        return trnode.appendChild(showmsg);


    }
    response.forEach(val => {
        let trnode=document.createElement('tr');
        
        let sid=document.createElement('td');
        sid.innerHTML=val.sid;
        trnode.appendChild(sid);

        let date=document.createElement('td');
        date.innerHTML=new Date(val.date);
        trnode.appendChild(date);

        let method=document.createElement('td');
        method.innerHTML=val.payment_method;
        trnode.appendChild(method);

        let purpose=document.createElement('td');
        purpose.innerHTML=val.purpose;
        trnode.appendChild(purpose);
        
        let amt=document.createElement('td');
        amt.innerHTML=val.amount;
        trnode.appendChild(amt);

        let exp=document.createElement('td');
        exp.innerHTML=new Date(val.date_of_expense).toDateString();
        trnode.appendChild(exp)


        let status=document.createElement('td');
        status.innerHTML=val.status;
        trnode.appendChild(status);

        tbody.appendChild(trnode);
    });
}
function viewallData(response) {
    const tbody = document.querySelector('.viewall-tab-content table>tbody');
    response.forEach(val => {
        let trnode=document.createElement('tr');
        
        let sid=document.createElement('td');
        sid.innerHTML=val.sid;
        trnode.appendChild(sid);

        let date=document.createElement('td');
        date.innerHTML=new Date(val.date);
        trnode.appendChild(date);

        let method=document.createElement('td');
        method.innerHTML=val.payment_method;
        trnode.appendChild(method);

        let purpose=document.createElement('td');
        purpose.innerHTML=val.purpose;
        trnode.appendChild(purpose);
        
        let amt=document.createElement('td');
        amt.innerHTML=val.amount;
        trnode.appendChild(amt);

        let exp=document.createElement('td');
        exp.innerHTML=new Date(val.date_of_expense).toDateString();
        trnode.appendChild(exp)


        let status=document.createElement('td');
        status.innerHTML=val.status;
        trnode.appendChild(status);

        tbody.appendChild(trnode);
    });
}