<!DOCTYPE html>
<html>
<head>
<script type="text/javascript" src="//www.gstatic.com/authtoolkit/js/gitkit.js"></script>
<link type="text/css" rel="stylesheet" href="//www.gstatic.com/authtoolkit/css/gitkit.css" />

<script type="text/javascript">
function load() {
  var config = {
  "widgetUrl": "http://localhost:8080/gitkit",
  "signInSuccessUrl": "/",
  "signOutUrl": "/",
  "oobActionUrl": "/",
  "apiKey": "AIzaSyB3ry4tDLxM3UrRU4zDaX5pZBQn7lbwgPM",
  "siteName": "Pomodoro site",
  "signInOptions": ["password","google"]
};
  // The HTTP POST body should be escaped by the server to prevent XSS
  window.google.identitytoolkit.start(
      '#gitkitWidgetDiv', // accepts any CSS selector
      config);
}
</script>
<script type="text/javascript" src="//apis.google.com/js/client.js?onload=load"></script>
</head>
<body>
<!-- Placeholder for the GAT widget panels -->
<div id="gitkitWidgetDiv"></div>
</body></html>

