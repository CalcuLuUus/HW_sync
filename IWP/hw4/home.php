<?php
	include('include/movie.inc.php');
	include('include/opening.html');
?>

<link href="css/home.css" type="text/css" rel="stylesheet">

<h1>Movie reviews</h1>
		
<div id="content">
	<ul>
		<?php
            // you must complete this part with code to generate
            // the list of links to available movies
			$moive_list = all_movie_path();
            foreach ($moive_list as $filename) {?>
				<li>
					<a class="link" href="movie.php?film=<?= $filename ?>"> <?= title(movie_data("moviedb/" . $filename))?></a>
				</li>
			<?php }
        ?>
	</ul>
</div>

<!-- This is a new link element for adding a new movie -->
<div id="addlink">
	<a href="add_movie_form.php">Add a new movie in the database</a>
</div>

<?php
	include('include/closing.html');
?>
