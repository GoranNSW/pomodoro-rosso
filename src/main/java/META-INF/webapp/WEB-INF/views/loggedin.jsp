<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Welcome!</title>
</head>
<body>
<div id="navbar"></div>
<p>
    <br>Logged in as: ${email}
</p>

<p>If you wish to join our community enter your username:</p>

    
<script type="text/javascript">
var email = "${email}"
if (email == null) {
	alert("Email is null");
}
</script>

<button onclick="myFunction()">Click me</button>

<p id="demo"></p>

<script>

function myFunction() {    
    
    if ("${username}" != "") {
    	document.getElementById("demo").innerHTML = "Hello ${username}";
    	//alert("Hi ${username}");
    	
    } else {
    	alert("You must enter username.");
    }
}
</script>

<script type="text/javascript">
function IsEmpty(){
	  if(document.forms['user_form'].question.value === "")
	  {
	    alert("Please enter usernamme");
	    return false;
	  }
	    return true;
	}
</script> 
    <p><font color="red">${errorMessage}</font></p>
    <form name="user_form" action="/loggedin" method="POST">
        Username : <input id="insert_username" onclick="return IsEmpty();" name="username" type="text" /> <input type="submit" value="Join"/>
    </form>
    
<table>
  <c:forEach items="${products}" var="product">
    <tr>
      <td><c:out value="${product.tareWeight}" /></td>
      <td><c:out value="${product.barCode}" /></td>
    </tr>
  </c:forEach>
</table>

</body>
</html>