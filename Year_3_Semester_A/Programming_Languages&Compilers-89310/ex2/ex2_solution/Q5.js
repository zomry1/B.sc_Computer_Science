function adder(num1,num2) {
  var content = document.getElementById("content");
  var sum = parseInt(num1.value) + parseInt(num2.value);
  content.innerHTML = num1.value + " + " + num2.value + " = " + sum;
}
