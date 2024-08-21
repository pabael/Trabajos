const movieElement = document.getElementById('movie');
const totalPrice = document.getElementById('total');
const seats = document.querySelectorAll('.row .seat:not(.occupied)');
const container = document.querySelector('.container');

const seatsVIP = document.querySelectorAll('.row .seat.VIP');
const VIPprice = document.getElementById('VIPprice');

const currency = document.getElementById('currency');

var rate = 1;

function updateMoviesPrieces(){
    const movieTitles = document.querySelectorAll('.movie-container option');
    
    //Option: movie title, price, currency
    movieTitles[0].label=`${movieTitles[0].innerText} ${(movieTitles[0].value*rate).toFixed(2)} ${currency.value}`;
    movieTitles[1].label=`${movieTitles[1].innerText} ${(movieTitles[1].value*rate).toFixed(2)} ${currency.value}`;
    movieTitles[2].label=`${movieTitles[2].innerText} ${(movieTitles[2].value*rate).toFixed(2)} ${currency.value}`;
    movieTitles[3].label=`${movieTitles[3].innerText} ${(movieTitles[3].value*rate).toFixed(2)} ${currency.value}`;
}

//Calculate price depending of number of selected seats and the movie
function updatePrice(){
    updateMoviesPrieces();
    //Update num of regular seats selected
    const selectedSeats = document.querySelectorAll('.row .seat.selected');
    const numSelectedSeats=selectedSeats.length;

    //Update num of VIP selected seats
    const selectedVIPSeats = document.querySelectorAll('.row .seat.VIP.selected');
    const numSelectedVIPSeats=selectedVIPSeats.length;
   
    const price = movie.value*rate;
    const priceVIPS = (2*rate).toFixed(2); //VIP seats cost an extra of 2€

    VIPprice.innerText=`${priceVIPS} ${currency.value}`;
    totalPrice.innerText=`${(price*numSelectedSeats + numSelectedVIPSeats*priceVIPS).toFixed(2)} ${currency.value}`; 

    localStorageInfo();
}

//Book selected seats
function bookSeat(e){
    const seatSelected = e.target;
    if(seatSelected.classList.contains('seat') && !seatSelected.classList.contains('occupied')){
        seatSelected.classList.toggle("selected");
    }
    updatePrice();
}

//Store info in local
function localStorageInfo(){
    const selectedSeats = document.querySelectorAll('.row .seat.selected');
    const seatsIndex = [...selectedSeats].map(function(seat){
        return[...seats].indexOf(seat);
    });

    localStorage.setItem('selectedSeats', JSON.stringify(seatsIndex));
    localStorage.setItem('selectedMovie',movieElement.selectedIndex);
    localStorage.setItem('selectedMoviePrice', movieElement.value);
    localStorage.setItem('selectedCurrency', currency.value);
}

//Get data from localStorage and populate UI
function populateUI(){
    
    //Selected seats
    const selectedSeats=JSON.parse(localStorage.getItem('selectedSeats'));
    if(selectedSeats !== null && selectedSeats.length>0){
        seats.forEach((seat, index) => {
            if(selectedSeats.indexOf(index) > -1){
                seat.classList.add('selected');
            }
        })
    }
    //Movie
    const selectedMovie=localStorage.getItem('selectedMovie');
    if(selectedMovie !== null && selectedMovie.length>0){
        movieElement.selectedIndex = selectedMovie;
    }

    //Movie price
    const selectedMoviePrice=localStorage.getItem('selectedMoviePrice');
    if(selectedMoviePrice !== null && selectedMoviePrice.length>0){
        movieElement.value = selectedMoviePrice;
    }

    //Currency 
    const selectedCurrency=localStorage.getItem('selectedCurrency');
    if(selectedCurrency !== null && selectedCurrency.length>0){
        currency.value = selectedCurrency;
        changeCurrency();
    }

    updatePrice();
}

function changeCurrency(){
    fetch (`https://api.exchangerate-api.com/v4/latest/EUR`)
    .then(res => res.json())
    .then(data => {
        rate = data.rates[currency.value];
        updatePrice();
    })
    .catch(error =>{
        showError(error);
    });
}

//Show error if the fetch request fails.
function showError(error){
    errorClass.querySelector('h3').innerText= `There has been a problem with the fetch request: ${error.message}.`;
    errorClass.className = 'error-message error'
}

populateUI()
//Movie select event
movieElement.addEventListener('change', updatePrice);
//Click select event
container.addEventListener('click', (e) =>{
    bookSeat(e);
});
//Currency select event
currency.addEventListener('change', changeCurrency)
