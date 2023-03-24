<?php
    // you must complete this script
    include('include/movie.inc.php');
	include('include/opening.html');
    $film = $_GET['film'];
?>

<link href="css/add_error.css" type="text/css" rel="stylesheet">

<h1>Error in uploading a review for The <?= title(movie_data(movie_path($film))) ?></h1>
		
<div id="content">

	<p>
		Your review upload failed. Maybe you didn't provide all the information required, so please try again...
	</p>

</div>

<div id="addlink">
	<a href="movie.php?film=<?= $film ?>" >back to movie</a>
</div>

<div id="footer">
	 2020 &copy; Rancid Tomatoes <img src="images/fresh.gif" alt="Fresh" />
</div>

<?php
    include("include/closing.html");
?>

<!-- add your HTML below -->
