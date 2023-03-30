<?php
	include('include/movie.inc.php');
	include('include/opening.inc.php');
?>
<link href="css/common.css" type="text/css" rel="stylesheet">
<link href="css/form.css" type="text/css" rel="stylesheet">

<h1>Sign-up to Rancid Tomatoes</h1>
		
<div id="content">

<form method="post" action="signup.php">

    <div>Your name</div>

    <div><input type="text" name="name" required></div>
    
    <div>Your organization</div>

    <div><input type="text" name="organization" required></div>
 
	<div>Your role</div>
    
    <div>
	    Reviewer <input type="radio" name="rating" value="reviewer" checked="checked">
  	    Editor <input type="radio" name="rating" value="editor">
    </div>

    <hr>

    <div>Choose a login (minimum 4 characters, letters and digits)</div>

    <div><input type="text" name="login" required></div>

    <div>Choose a password (minimum 5 characters)</div>

    <div><input type="password" name="password1" required></div>

    <div>Repeat password</div>

    <div><input type="password" name="password2" required></div>

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
