function upadateSugarNumber(product, price, isIncreasing){
    const sugarInput = document.getElementById(product + '-number');
    let sugarNumber = sugarInput.value;
            if(isIncreasing){
                sugarNumber = parseInt(sugarNumber) + 10;
            }
            
    else if(sugarNumber > 0){
           sugarNumber = parseInt(sugarNumber) - 10;
         }
        
        sugarInput.value = sugarNumber;
    // update sugar total 
    const sugarTotal = document.getElementById(product + '-total');
    sugarTotal.innerText = sugarNumber * price;
    calculateTotal();
    }


    function getInputvalue(product){
        const productInput = document.getElementById(product + '-number');
        const productNumber = parseInt(productInput.value);
        return productNumber;
    }
    function calculateTotal(){
        const creamTotal = getInputvalue('cream') * 1;
        const sugarTotal = getInputvalue('sugar') * 1;

 }

document.getElementById('sugar-plus').addEventListener('click',function(){
        // const sugarInput = document.getElementById('sugar-number');
        // const sugarNumber = sugarInput.value;
        // sugarInput.value = parseInt(sugarNumber) + 1;
   upadateSugarNumber('sugar', 1, true);
});

document.getElementById('sugar-minus').addEventListener('click',function(){
    // const sugarInput = document.getElementById('sugar-number');
//     const sugarNumber = sugarInput.value;
//    if(sugarnput.value > 1){
//         sugarInput.value = parseInt(sugarNumber) - 1;
//    }
upadateSugarNumber('sugar', 1, false);
});

// cream prcie update using add event linstner 
document.getElementById('cream-plus').addEventListener('click',function(){
    upadateSugarNumber('cream', 1, true);
});


document.getElementById('cream-minus').addEventListener('click',function(){
    upadateSugarNumber('cream', 1, false);
});