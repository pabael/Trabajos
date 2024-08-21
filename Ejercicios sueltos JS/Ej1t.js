const form = document.getElementById('form');
const password = document.getElementById('password');

//Check if the user input is correct
function checkRequired(input){
    if(input.value.trim() == '' && input.id != "password" ){ 
        showError(input, `${getFieldName(input)} is required.`);
    }else if(input.id == "username"){
        checkLength(input, 3, 15);
    }else if(input.id == "password"){
        checkPassword(input)
    }else if(input.id == "confirmation"){
        checkMatch(input, password);
    }else if(input.id == "email"){
        checkEmail(input);
    }else if(input.id =="age"){
        checkAge(input);
    }else{
        showSuccess(input);
    }
}

//Show outline green 
function showSuccess(input){
    const formControl = input.parentElement;
    formControl.className = 'form-control success';
}

//Show error message and outline red
function showError(input, message){
    const formControl = input.parentElement;
    formControl.className = 'form-control error';
    const small = formControl.querySelector('small');
    small.innerText = message;
}

//Return field name with fist letter in capital letter
function getFieldName(input){
    return input.id.charAt(0).toUpperCase() + input.id.slice(1);
}

//Check input length
function checkLength(input, min, max){
    if(input.value.length < min){
        showError(input,  `${getFieldName(input)} must be at least ${min} characters.`);
    } else if(input.value.length > max) {
        showError(input, `${getFieldName(input)} must be less than ${max} characters.`);
    } else{
        showSuccess(input);
    }    
}

//Check if email is valid. Return true if email valid.
function checkEmail(input){
    const re = /^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|.(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
    if(!re.test(String(input.value.trim()).toLowerCase())){
        showError(input, `${getFieldName(input)} is not valid.`);
    }else{
        showSuccess(input);
    }
}

//Check if the password has all requirement symbols
function checkPassword(input){
    const  capitalLetter= /[A-Z]/;
    const  lowerCase= /[a-z]/;
    const  number= /[0-9]/;   
    const  specialCharacter = /[~!@#$%^&*()-+={}[\]:;"'<>,.?/|_]/
    
    const formControl = input.parentElement;
    showSuccess(input);

    if(!capitalLetter.test(input.value)){
        formControl.className = 'form-control error';
        document.getElementById('capitalLetter').style.color="red";
    }else{
        document.getElementById('capitalLetter').style.color="rgb(10, 224, 10)";
    }

    if(!lowerCase.test(input.value)){
        formControl.className = 'form-control error';
        document.getElementById('lowerCase').style.color="red";
    }else{
        document.getElementById('lowerCase').style.color="rgb(10, 224, 10)";
    }

    if(!specialCharacter.test(input.value)){
        formControl.className = 'form-control error';
        document.getElementById('specialCharacter').style.color="red";

    }else{
        document.getElementById('specialCharacter').style.color="rgb(10, 224, 10)";
    }

    if(!number.test(input.value)){
        formControl.className = 'form-control error';
        document.getElementById('number').style.color="red";
    }else{
        document.getElementById('number').style.color="rgb(10, 224, 10)";
    }

    if(input.value.length < 8 || input.value.length > 25){
        formControl.className = 'form-control error';
        document.getElementById('lenght').style.color="red";
    } 
    else{
        document.getElementById('lenght').style.color="rgb(10, 224, 10)";
    }  
    
}

//Check if an element has the same value as another element.
function checkMatch(input1, input2){
    if(input1.value != input2.value){
        showError(input1, `It must match with ${getFieldName(input2)}.`);
    }else{
        showSuccess(input1);
    }
}

//Check if the age is between 0 and 1000
function checkAge(input){
    if(!((input.value>0) && (input.value<1000))){
        showError(input,  `Introduce a number between 0 and 1000.`);
    }else{
        showSuccess(input);
    } 
}

form.addEventListener('submit', function(e){
    e.preventDefault();
    const inputs = Array.prototype.slice.call(document.getElementsByClassName('form-control')); 
    inputs.forEach(function(input){
        checkRequired(input.querySelector('input'));
    });
 });
    