<?php
	include('include/movie.inc.php');
	$film = $_GET["film"];
	$movie_path = movie_path($film);
	$movie_data = movie_data($movie_path);
	include('include/opening.inc.php');
?>

<link href="css/common.css" type="text/css" rel="stylesheet">
<link href="css/add_error.css" type="text/css" rel="stylesheet">

<h1>Error in uploading a review for <?= title($movie_data) ?></h1>
		
<div id="content">

	<p>
		Your review upload failed. Maybe you didn't provide all the information required, so please try again...
	</p>

</div>

<div id="addlink">
	<a href="movie.php?film=<?= $film ?>">back to movie</a>
</div>

<?php
	include('include/closing.html');
?>
