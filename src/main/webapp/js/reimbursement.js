
function renderTable(reimbursements){
    for(const reimbursement of reimbursements){
        const tr = document.createElement("tr");
        const id = document.createElement("td");
        id.addEventListener("click",getReimbursementById);
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
    }
}

    async function asyncFetch(url, expression){
        const response = await fetch(url);
        const json = await response.json();
        expression(json);
    };

    asyncFetch("http://localhost:8081/ReimbursementSystem/all.json", renderTable);
    
    function test(){
    	return console.log("the button is working");
    };
    
    async function addReimbursement(){
    	const reimbursement =  {
    	        reimbAmount:document.getElementById("ammount").value,
    	        reimbResolved: null,
    	        reimbDescription:document.getElementById("description").value,
    	        type: {
    	            typeId:document.getElementById("type").value
    	        }
    	    }
    	const fetched = await fetch("http://localhost:8081/ReimbursementSystem/reimbursement.json", {
    			method:"post",
    			body:JSON.stringify(reimbursement)
    	});
    	const json = await fetched.text();
    	const rows = document.getElementById("reimbursementTableBody").innerHTML='';
    	asyncFetch("http://localhost:8081/ReimbursementSystem/all.json", renderTable);
    }

    document.getElementById("reimbSubmit").addEventListener("click",addReimbursement);
  
    
    function filter(){
    	const filterWord = document.getElementById("filter").value;
    	const tr = document.getElementById("reimbursementTableBody").getElementsByTagName('tr');
    	for(let i = 0; i < tr.length; i++){
    		let td = document.getElementsByTagName("td")[9];
    		if(td){
    			const txtValue = td.textContent;
    			if(txtValue.toLowerCase().indexOf(filterWord)>-1||txtValue === ""){
    				tr[i].style.display= "";
    			}else{
    				tr[i].style.display= "none";
    			}
    		}
    	}   	
    }

//    function getReimbursementById(){
//    	const reimb_id = document.getElementById("reimb_id").value;
//    }
    
    function goToEditPage(){
    	asyncFetch("http://localhost:8081/ReimbursementSystem/veiw.html", getReimbursementById);
    }
    
    document.getElementById("reimbUpdate").addEventListener("click",goToEditPage);

    //const fetched = await fetch("http://localhost:8081//ReimbursementSystem/all.json")
    