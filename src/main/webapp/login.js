async function loginEmployee() {
	var username=document.getElementById('employee').value;
	var password=document.getElementById('empass').value;

	let emp={name: username, password: password};
	const request = new Request('http://localhost:8080/RestAPI/webapi/employee/login', {
		method: 'POST',
		headers: [['Content-Type', 'application/json']],
		body: JSON.stringify(emp)
	});
	const response = await fetch(request);
	const userdata = response.status === 200 && await response.json();
	if (userdata) {
		document.cookie=`user=${userdata[0].id}; expires= ${new Date()+3}; path=employee/`;
		window.location.href = 'http://localhost:8080/RestAPI/employee.html';
	}
	else{
		alert('Incorrect credentials');
	}
	
}
async function loginManager(){
	var username=document.getElementById('manager').value;
var password=document.getElementById('mpass').value;

let m={name: username, password: password};

const request = new Request('http://localhost:8080/RestAPI/webapi/manager/login', {
		method: 'POST',
		headers: [['Content-Type', 'application/json']],
		body: JSON.stringify(m)
	});
	
	const response = await fetch(request);
	const mdata = response.status === 200 && await response.json();
	if (mdata) {
		console.log(mdata);
		document.cookie=`mdata=${mdata[0].id}; expires= ${new Date()+3} ; path=manager/`;
		window.location.href = 'http://localhost:8080/RestAPI/manager.html';
	}

	
}

const pages=['employee','manager'];
let selectedpage=pages[0];
function displayPage(pageindex){

	const prevpage=selectedpage;
	const newpage=pages[pageindex];
	selectedpage=newpage;

	document.getElementsByClassName(`${prevpage}-page-content`)[0].classList.add('d-none');
	document.getElementsByClassName(`${newpage}-page-content`)[0].classList.remove('d-none');

}
function signupManager(){

	const sal=document.getElementById('sal').value;
	const id=document.getElementById('mid').value;

	let empdata={
		id:id,salary:sal
	}
	const request=new Request(`http://localhost:8080/RestAPI/webapi/manager/signup`, {
		method:'POST',
		headers:[['Content-Type', 'application/json']],
		body:JSON.stringify(empdata)
	});

	submitData('manager',empdata);
}

function signupEmployee(){
	const uname=document.getElementById('username').value;
	const fname=document.getElementById('fname').value;
	const lname=document.getElementById('lname').value;
	const mail=document.getElementById('mail').value;
	const sal=document.getElementById('sal').value;
	const pass=document.getElementById('pass').value;
	const ph=document.getElementById('phone').value;
	let data={
		username:uname, password:pass, firstname:fname,
		lastname:lname, email:mail, salary:sal,phonenum:ph
	}
	
	submitData('employee',data);
	
	
}
function submitData(loginas,data){

	
	const request=new Request(`http://localhost:8080/RestAPI/webapi/${loginas}/signup`, {
		method:'POST',
		headers:[['Content-Type', 'application/json']],
		body:JSON.stringify(data)
	});
	
	fetch(request)
	.then(response =>  {
		if(response.status===200) {
		const toastElement = document.getElementById("liveToast");
		const myToast = bootstrap.Toast.getOrCreateInstance(toastElement);
		myToast.show();
	}
})
	.catch(err=>console.log(err));
}