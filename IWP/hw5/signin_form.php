<?php
	include('include/movie.inc.php');
	include('include/opening.inc.php');
?>
<link href="css/common.css" type="text/css" rel="stylesheet">
<link href="css/form.css" type="text/css" rel="stylesheet">

<h1>Sign-in to Rancid Tomatoes</h1>
		
<div id="content">

<form method="post" action="signin.php">

    <div>login</div>

    <div><input type="text" name="login" required></div>
    
    <div>password</div>

    <div><input type="password" name="password" required></div>
	
	<div>
		<input id="submit" name="submit" type="submit" value="Submit">
    </div>

<?php
    // ADD CODE HERE
    
    
    ////////////////
?>
   
</form>

</div>

<?php
	include('include/closing.html');
?>
