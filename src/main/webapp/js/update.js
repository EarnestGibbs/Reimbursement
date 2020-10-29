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
        submitDate.innerHTML = reimbursement.reimbSubmitted;
        resolveDate.innerHTML = reimbursement.reimbResolved;
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
    	
    	document.getElementById("filter").append(filter_id);
    	
    	i++;
    }
}

async function asyncFetch(url, expression){
    const response = await fetch(url);
    const json = await response.json();
    expression(json);
};

asyncFetch("http://localhost:8081/ReimbursementSystem/all.json", getReimbursement);

function filter(){
	const filterNumber = document.getElementById("filter").value;
	const tr = document.getElementById("reimbursementTableBody").getElementsByTagName('tr');
	for(let i = 0; i < tr.length; i++){
		let td = document.getElementById([i]);
		if(td){
			console.log(filterNumber);
			const noFilter = document.getElementById("none")
			if(filterNumber === "none"){
				tr[i].style.display = "";
			}else if(td.id===filterNumber){
				tr[i].style.display= "";
			}else{
				tr[i].style.display= "none";
			}
		}
	}   	
}
filter();
function updateReimb(){
	
}