<?php
    // ADD CODE HERE
    
    
    ////////////////

?>

<link href="css/common.css" type="text/css" rel="stylesheet">
<link href="css/home.css" type="text/css" rel="stylesheet">

<h1>Movie reviews</h1>
		
<div id="content">
	<ul>
		<?php
			foreach (all_movie_path() as $movie_path) {
		?>
		<li>
			<img src="<?= rating_image_path(movie_data($movie_path)) ?>" alt="Rating Image">
			<a class="link" href="movie.php?film=<?= movie_key($movie_path) ?>">
			<?= title(movie_data($movie_path)) ?></a>
		</li>
		<?php } ?>
	</ul>
</div>
<?php


?>
<?php
	include('include/closing.html');
?>
