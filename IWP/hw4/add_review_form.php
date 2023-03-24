<?php
	include('include/movie.inc.php');
	$film = $_GET["film"];
	$movie_path = movie_path($film);
	$movie_data = movie_data($movie_path);
	include('include/opening.html');
?>

<link href="css/home.css" type="text/css" rel="stylesheet">
<link href="css/form.css" type="text/css" rel="stylesheet">

<h1>Add a review for movie <?= title($movie_data) ?></h1>
		
<div id="content">

<form method="get" action="add_review.php">
	<div>Personal information</div>

	<label for="name">Name</label><input name="name" type="text" required="required"><br>
	<label for="organization">Organization</label><input name="organization" type="text" required="required"><br>
	
	<div>Your rating</div>
	
	<img src="images/fresh.gif" alt="Fresh">FRESH <input type="radio" name="rating" value="FRESH" checked="checked">
  	<img src="images/rotten.gif" alt="Rotten">ROTTEN <input type="radio" name="rating" value="ROTTEN">
  	
	<div>Your review</div>
	
	<textarea id="review" name="review" rows="10" cols="95" required="required"></textarea>
	<input type="hidden" name="film" value="<?= $film ?>">
	
	<div>
		<input id="submit" name="submit" type="submit" value="Submit">
	</div>
</form>

</div>

<?php
	include('include/closing.html');
?>
