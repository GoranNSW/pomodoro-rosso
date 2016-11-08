<!DOCTYPE html>
<html id="center">
<head>
<script type="text/javascript"
    src="//www.gstatic.com/authtoolkit/js/gitkit.js"></script>
<link type=text/css rel=stylesheet
    href="//www.gstatic.com/authtoolkit/css/gitkit.css" />
<style>
#center {
    max-width: 800px;
    margin: auto;
    left: 1%;
    right: 1%;
    position: absolute;
}
</style>
<div>
    <h1>Welcome to the Pomodoro-Rosso team management!</h1>
    <p>If you want to join our team you must use your google account to
        sign in.</p>
</div>
<script type=text/javascript>
  window.google.identitytoolkit.signInButton(
    '#navbar',
    {
      widgetUrl: "http://localhost:8080/gitkit",
      signOutUrl: "/",
    }
  );  
</script>


</head>
<body>
    <div id="navbar"></div>
</body>
</html>

