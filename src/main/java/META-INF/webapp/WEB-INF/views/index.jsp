<!DOCTYPE html>
<html>
<head>
<script type="text/javascript" src="//www.gstatic.com/authtoolkit/js/gitkit.js"></script>
<link type=text/css rel=stylesheet href="//www.gstatic.com/authtoolkit/css/gitkit.css" />
<div>
	<p>
		Welcome to the Pomodoro!
	</p>
</div>
<script type=text/javascript>
  window.google.identitytoolkit.signInButton(
    '#navbar', // accepts any CSS selector
    {
      widgetUrl: "http://localhost:8000/gitkit",
      signOutUrl: "/",
    }
  );  
</script>
</head>
<body>
<div id="navbar"></div>
</body>
</html>

