const currencyIntroduced = document.getElementById('currency-introduced');
const amountIntroduced = document.getElementById('amount-introduced');
const amountIntroducedWrong = document.getElementById('small');

const currencyWanted = document.getElementById('currency-wanted');
const amountWanted = document.getElementById('amount-wanted');

const loaderElement = document.getElementById('loader');
const rateElement = document.getElementById('rate');
const swap = document.getElementById('swap');

const errorClass = document.getElementById('error-message');

//Show error if the fetch request fails.
function showError(error){
    const container = loaderElement.parentElement.parentElement;
    container.className = 'container error';
    rateElement.className = 'rate loading';
    errorClass.querySelector('h3').innerText= `There has been a problem with the fetch request: ${error.message}.`;
    errorClass.className = 'error-message error'
}

//Fetch exchange rates and update the DOM
function calculate(){
    fetch (`https://api.exchangerate-api.com/v4/latest/${currencyIntroduced.value}`)
    .then(
        loaderElement.className = 'loader loading',
        rateElement.className = 'rate loading')
    .then(res => res.json())
    .then(data => {
        loaderElement.className = 'loader loaded';
        rateElement.className = 'rate loaded';
        const rate = data.rates[currencyWanted.value];
        positiveAmount();
        rateElement.innerText = `1 ${currencyIntroduced.value} = ${rate} ${currencyWanted.value}`;
        amountWanted.value = (amountIntroduced.value*rate).toFixed(2);
    })
    .catch(error =>{
        showError(error);
    });
}

function positiveAmount(){
    if(amountIntroduced.value<0){
        amountIntroduced.value=Math.abs(amountIntroduced.value);
    }
}

calculate();

//Event Listener
currencyIntroduced.addEventListener('change', calculate);
amountIntroduced.addEventListener('input', calculate);
currencyWanted.addEventListener('change', calculate);
amountWanted.addEventListener('input', calculate);
swap.addEventListener('click', () => {
    const temp = currencyIntroduced.value;
    currencyIntroduced.value = currencyWanted.value;
    currencyWanted.value = temp;
    calculate();
});