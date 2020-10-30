function getReimbursement(reimbursements){
	let i = 0;
    for(const reimbursement of reimbursements){
        const tr = document.createElement("tr");
        tr.setAttribute("id", i);
        const id = document.createElement("td");
        const ammount = document.createElement("td");
        const submitDate = document.createElement("td");
        const resolveDate = document.createElement("td");
        const description = document.createElement("td");
        const receipt = document.createElement("td");
        const author = document.createElement("td");
        const resolver = document.createElement("td");
        const status = document.createElement("td");
        const type = document.createElement("td");
        id.innerHTML = reimbursement.reimbID;
        ammount.innerHTML = reimbursement.reimbAmount;
        submitDate.innerHTML = new Date(reimbursement.reimbSubmitted);
        resolveDate.innerHTML = new Date(reimbursement.reimbResolved);
        description.innerHTML = reimbursement.reimbDescription;
        receipt.innerHTML = reimbursement.reimbReceipt;
        author.innerHTML = reimbursement.authorUser.userId;
        resolver.innerHTML = reimbursement.resolverUser.userId;
        status.innerHTML = reimbursement.status.status;
        type.innerHTML = reimbursement.type.type;
        tr.append(id,ammount,submitDate,resolveDate,description,receipt,author,resolver,status,type);
        
        document.getElementById("reimbursementTableBody").append(tr);
        
        //creating options
        const filter_id = document.createElement("option");
    	filter_id.setAttribute("value", i);
    	filter_id.innerHTML = reimbursement.reimbID;
    	
    	document.getElementById("filterId").append(filter_id);
    	
    	i++;
    }
}

let reimb_value;

async function asyncFetch(url, expression){
    const response = await fetch(url);
    const json = await response.json();
    expression(json);
};

asyncFetch("http://localhost:8081/ReimbursementSystem/allreimb.json", getReimbursement);



async function updateReimb(){
	const reimbursement =  {
	        reimbId:reimb_value,
	        status: {
	            statusId:document.getElementById("status").value
	        }
	    }
	const fetched = await fetch("http://localhost:8081/ReimbursementSystem/update.json", {
			method:"post",
			body:JSON.stringify(reimbursement)
	});
	const json = await fetched.text();
	const rows = document.getElementById("reimbursementTableBody").innerHTML='';
	asyncFetch("http://localhost:8081/ReimbursementSystem/allreimb.json", getReimbursement);
}

document.getElementById("updateReimb").addEventListener('click',updateReimb)

function filterId(){
	const filterNumber = document.getElementById("filterId").value;
	const tr = document.getElementById("reimbursementTableBody").getElementsByTagName('tr');
	for(let i = 0; i < tr.length; i++){
		let td = document.getElementById([i]);
		if(td){
			const noFilter = document.getElementById("none")
			if(filterNumber === "none"){
				tr[i].style.display = "";
			}else if(td.id===filterNumber){
				reimb_value= tr[i].firstChild.innerHTML
				console.log(reimb_value)
				tr[i].style.display= "";
			}else{
				tr[i].style.display= "none";
			}
		}
	}   	
}

function filterName(){
    	const filterWord = document.getElementById("filterName").value;
    	const tr = document.getElementById("reimbursementTableBody").getElementsByTagName('tr');
    	for(let i = 0; i < tr.length; i++){
    		if(tr){
    			const testWord = tr[i].lastElementChild.innerHTML.toLocaleLowerCase()
    			console.log(testWord)
    			console.log(filterWord)
    			if(testWord===filterWord||testWord === ""){
    				tr[i].style.display= "";
    			}else{
    				tr[i].style.display= "none";
    			}
    		}
    	}   	
    }
