var userdata,visitedTab=[];
function fetchManager(){
    fetch(`http://localhost:8080/RestAPI/webapi/manager/view/${getCookie()}`)
        .then(response => response.json())
        .then(json => userdata = json)
        .then(() => getUser(userdata))
        .catch(err => console.log('Error',err));


}
function getCookie(){
    
    username='mdata=';
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

const tabsName = ['profile', 'update','viewall','pending','solved','rejected','emp','viewemp'];
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
    const request = new Request('http://localhost:8080/RestAPI/webapi/manager/update', {
		method: 'PUT',
		headers: [['Content-Type', 'application/json']],
		body: JSON.stringify(emp)
	});

    fetch(request)
    .then(response=>{if(response.status===200)
        if(response.status === 200) {
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
        
    const request = new Request(`http://localhost:8080/RestAPI/webapi/manager/view/support/rejected`);
    fetch(request)
    .then(response=>response.json())
    .then(json=>rejectedData(json))
    .catch(err=>console.log(err));
    }
}

function viewSolved(){
    
    if(!visitedTab.includes('solved')){
        visitedTab.push('solved');
        const request = new Request(`http://localhost:8080/RestAPI/webapi/manager/view/support/resolved`);
    fetch(request)
    .then(response=>response.json())
    .then(json=>resolvedData(json))
    .catch(err=>console.log(err));
    }
    
}

function viewPending(){
    
    if(!visitedTab.includes('pending')){
        visitedTab.push('pending');
        const request = new Request(`http://localhost:8080/RestAPI/webapi/manager/view/support/pending`);
        fetch(request)
        .then(response=>response.json())
        .then(json=>pendingData(json))
        .catch(err=>console.log(err));
    }
    
}
function viewAll(){
    
    if(!visitedTab.includes('viewall')){
    visitedTab.push('viewall');
    const request = new Request(`http://localhost:8080/RestAPI/webapi/manager/view/support/all`);
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

        let expdate=document.createElement('td');
        expdate.innerHTML=new Date(val.date_of_expense).toDateString();
        trnode.appendChild(expdate);

        let method=document.createElement('td');
        method.innerHTML=val.payment_method;
        trnode.appendChild(method);

        let purpose=document.createElement('td');
        purpose.innerHTML=val.purpose;
        trnode.appendChild(purpose);

        let amt=document.createElement('td');
        amt.innerHTML=val.amount;
        trnode.appendChild(amt);

        let status=document.createElement('td');
        status.innerHTML=val.status;
        trnode.appendChild(status);

        let eid=document.createElement('td');
        eid.innerHTML=val.emp.id;
        trnode.appendChild(eid);
        
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

        let expdate=document.createElement('td');
        expdate.innerHTML=new Date(val.date_of_expense).toDateString();
        trnode.appendChild(expdate);

        let method=document.createElement('td');
        method.innerHTML=val.payment_method;
        trnode.appendChild(method);

        let purpose=document.createElement('td');
        purpose.innerHTML=val.purpose;
        trnode.appendChild(purpose);

        let amt=document.createElement('td');
        amt.innerHTML=val.amount;
        trnode.appendChild(amt);

        let status=document.createElement('td');
        status.innerHTML=val.status;
        trnode.appendChild(status);

        let eid=document.createElement('td');
        eid.innerHTML=val.emp.id;
        trnode.appendChild(eid);
        
        tbody.appendChild(trnode);  
    });
}
function pendingData(response){

    let Id;
    const tbody = document.querySelector('.pending-tab-content table>tbody');
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
        Id=val.sid;
        sid.innerHTML=Id;
        trnode.appendChild(sid);

        let date=document.createElement('td');
        date.innerHTML=new Date(val.date);
        trnode.appendChild(date);

        let expdate=document.createElement('td');
        expdate.innerHTML=new Date(val.date_of_expense).toDateString();
        trnode.appendChild(expdate);

        let method=document.createElement('td');
        method.innerHTML=val.payment_method;
        trnode.appendChild(method);

        let purpose=document.createElement('td');
        purpose.innerHTML=val.purpose;
        trnode.appendChild(purpose);

        let amt=document.createElement('td');
        amt.innerHTML=val.amount;
        trnode.appendChild(amt);

        let eid=document.createElement('td');
        eid.innerHTML=val.emp.id;;
        trnode.appendChild(eid);
        
        let buttons=document.createElement('td');
        let approveBtn = document.createElement('button');
        approveBtn.setAttribute('type', 'button');
        approveBtn.classList.add('btn', 'btn-success');
        var t=document.createTextNode('Accept');
        approveBtn.appendChild(t);
        approveBtn.onclick = updateStatus.bind(this, 'accepted',Id);
        let rejectBtn = document.createElement('button');
        rejectBtn.setAttribute('type', 'button');
        rejectBtn.classList.add('btn', 'btn-danger');
        rejectBtn.onclick = updateStatus.bind(this, 'rejected',Id);
        var txt=document.createTextNode('Reject');
        rejectBtn.appendChild(txt);
        buttons.appendChild(approveBtn);
        buttons.appendChild(rejectBtn);
        trnode.appendChild(buttons);
        tbody.appendChild(trnode);
        
       
    });
}

function updateStatus(status,Id) {
    
    
    let m={mid:getCookie(),id:Id,status:status};
    const request = new Request('http://localhost:8080/RestAPI/webapi/manager/update/support/case', {
    method: 'PUT',
    headers: [['Content-Type', 'application/json']],
    body: JSON.stringify(m)
    });
    fetch(request)
    .then(response=>{if(response.status===200)
        if(response.status === 200) {
            const toastElement = document.getElementById("liveToast");
            const myToast = bootstrap.Toast.getOrCreateInstance(toastElement);
            myToast.show();
        }
    })
      .catch(err=>console.log(err))
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

        let expdate=document.createElement('td');
        expdate.innerHTML=new Date(val.date_of_expense).toDateString();
        trnode.appendChild(expdate);

        let method=document.createElement('td');
        method.innerHTML=val.payment_method;
        trnode.appendChild(method);

        let purpose=document.createElement('td');
        purpose.innerHTML=val.purpose;
        trnode.appendChild(purpose);

        let amt=document.createElement('td');
        amt.innerHTML=val.amount;
        trnode.appendChild(amt);

        let status=document.createElement('td');
        status.innerHTML=val.status;
        trnode.appendChild(status);

        let eid=document.createElement('td');
        eid.innerHTML=val.emp.id;
        trnode.appendChild(eid);
        
        tbody.appendChild(trnode);    
});
}
async function viewAllEmployees() {
    if (visitedTab.includes('viewallemp')) {
        return;
    }
    visitedTab.push('viewallemp');
    const data=await fetch(`http://localhost:8080/RestAPI/webapi/manager/view/employees`);
    const emp=await data.json();
    const tbody = document.querySelector('.emp-tab-content table>tbody');
    emp.forEach(val => {
        let trnode=document.createElement('tr');
        
        let eid=document.createElement('td');
        eid.innerHTML=val.id;
        trnode.appendChild(eid);

        let uname=document.createElement('td');
        uname.innerHTML=val.username;
        trnode.appendChild(uname);

        let fname=document.createElement('td');
        fname.innerHTML=val.first_name;
        trnode.appendChild(fname);

        let lname=document.createElement('td');
        lname.innerHTML=val.last_name;
        trnode.appendChild(lname);

        let mail=document.createElement('td');
        mail.innerHTML=val.email;
        trnode.appendChild(mail);

        let ph=document.createElement('td');
        ph.innerHTML=val.phonenum;
        trnode.appendChild(ph);

        let s=document.createElement('td');
        s.innerHTML=val.salary;
        trnode.appendChild(s);
        tbody.appendChild(trnode);
    });
}
function viewEmployee(){
    const id=document.querySelector('.viewemp-tab-content #id').value;
    fetch(`http://localhost:8080/RestAPI/webapi/employee/view/${id}`)
        .then(response => response.json())
        .then(json => {
            var textnode=document.createTextNode(json[0].id);
            var tdnode=document.querySelector('.viewemp-tab-content #eid');
            tdnode.innerHTML='';
            tdnode.appendChild(textnode);
            document.querySelector('.viewemp-tab-content #uname').innerHTML = json[0].username;
            document.querySelector('.viewemp-tab-content #fname').innerHTML = json[0].first_name;
            document.querySelector('.viewemp-tab-content #lname').innerHTML = json[0].last_name;
            document.querySelector('.viewemp-tab-content #mail').innerHTML = json[0].email;
            document.querySelector('.viewemp-tab-content #ph').innerHTML = json[0].phonenum;
            document.querySelector('.viewemp-tab-content #sal').innerHTML = json[0].salary;
        })
        .catch(err => console.log('Error',err));
}