const memoFib = function() {
  let memo = {} //Create a dictonary
  return function fib(n) {
    if (n in memo) { return memo[n] } //If the key is already is the dictonary, return his value
    else { //Fibonaci math - save the result in the dictonary
      //console.log('Calcuate ' + n);
      if (n == 0) {memo[n] = 0}
      if (n <= 2) { memo[n] = 1 } 
      else { memo[n] = fib(n - 1) + fib(n - 2)} 
      return memo[n]
    }
  }
}

const memoFactorial = function() {
  let memo = {} //Create a dictonary
  return function factorial(n) {
    if (n in memo) { return memo[n] } //If the key is already is the dictonary, return his value
    else { //Factorial math - save the result in the dictonary
      //console.log('Calcuate ' + n);
      if (n == 0) { memo[n] = 1 } 
      else { memo[n] = n * factorial(n - 1)} 
      return memo[n]
    }
  }
}

//input: function with 1 input, output: return a memorize function of this function
const memoize = function(f) {
  let memo = {} //Create a dictonary
  return (n) => {
    if (n in memo) {return memo[n]} //If the key is already is the dictonary, return his value
    else {
      memo[n] = f(n); //Save the result in the dictonary
      return memo[n];
    }
  }
}
//input: function with number of inputs, output: return a memorize function of this function
const memoizeGeneral = function(f) {
  let memo = {} //Create a dictonary
  return (...args) => {
    if (args in memo) {return memo[args]} //If the key is already is the dictonary, return his value
    else {
      memo[args] = f(...args); //Save the result in the dictonary
      return memo[args];
    }
  }
}

function adder(num1,num2) {
  var content = document.getElementById("content");
  var sum = parseInt(num1.value) + parseInt(num2.value);
  content.innerHTML = num1.value + " + " + num2.value + " = " + sum;
}

//Testing - for Q1-Q4
function Q1() {
  //Remove comments from lines 7 and 21 - to test if the memorize is working
  //Output should be: Calcuate 10 Calcuate 9 Calcuate 8 Calcuate 7 Calcuate 6 Calcuate 5 Calcuate 4 Calcuate 3 Calcuate 2 Calcuate 1 55 55 Calcuate 10 Calcuate 9 Calcuate 8 Calcuate 7 Calcuate 6 Calcuate 5 Calcuate 4 Calcuate 3 Calcuate 2 Calcuate 1 3628800 3628800
  var f = memoFib();
  console.log(f(80));
  console.log(f(80));
  var p = memoFactorial();
  console.log(p(10));
  console.log(p(10));
}

function Q3() {
  //Output should be: calculate value calculate value calculate value calculate value calculate value calculate value calculate value calculate value calculate value calculate value 3628800 36288
  var memo = memoize(factorialize);
  
  function factorialize(num) {
    console.log("calculate value") //Test if value isn't already memoized
    if (num == 0) {
      return 1;
    }
    return num * memo(num - 1);
  }

  console.log(memo(10));
  console.log(memo(9));
}

function Q4() {
  //Output should be: calculate value calculate value calculate value calculate value calculate value calculate value calculate value calculate value calculate value calculate value calculate value calculate value calculate value calculate value calculate value calculate value calculate value 3628920 3628920 
  function factorialize(num) {
    console.log("calculate value") //Test if value isn't already memoized
    if (num == 0) {
      return 1;
    }
    return num * factorialize(num - 1);
  }

  function factorializeTwo(num1,num2) {
    return factorialize(num1) + factorialize(num2);
  }
  var f = memoizeGeneral(factorializeTwo)
  console.log(f(10,5));
  console.log(f(10,5))
}

Q1();
/*
Q1();
console.log("-----------------");
Q3();
console.log("-----------------");
Q4();
console.log("-----------------");*/