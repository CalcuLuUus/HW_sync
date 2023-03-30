<?php
    // ADD CODE HERE
    
    
    ////////////////

	$movie_path = movie_path($_GET["film"]);
	$movie_data = movie_data($movie_path);
	include('include/opening.inc.php');
?>

<link href="css/common.css" type="text/css" rel="stylesheet">
<link href="css/movie.css" type="text/css" rel="stylesheet">

<a id="back" href="home.php"><img src="images/goback.png" alt="Back"></a>

<h1><?= title($movie_data) ?> (<?= year($movie_data) ?>)</h1>
		
<div id="content">
	<div id="right-content">
		<div>
			<img src="<?= overview_image_path($movie_path) ?>" alt="general overview">
		</div>

		<dl>
		<?php
			$overviews = overview($movie_path);
			foreach ($overviews as $overview) {
		?>
				<dt><?= dt($overview) ?></dt>
				<dd><?= dd($overview) ?></dd>
		<?php } ?>
		</dl>
	</div>
	<div id="left-content">
		
		<div id="left-banner">
			<img src="<?= rating_image_path($movie_data) ?>" alt="<?= rating_image_alt($movie_data) ?>">
			<?= rating($movie_data) ?>%
		</div>
		<div id="left-column">
			<?php
				$all_review_path = all_review_path($movie_path);
				$n = count($all_review_path) - (int) (count($all_review_path)/2);
				$k = 0;
				foreach ($all_review_path as $review_path) {
					if ( $k == $n ) {
						echo '</div><div id="right-column">';
					}
					$review_data = review_data($review_path);
			?>
					<p class="quote">
						<img src="images/<?= review_image_path($review_data) ?>" alt="<?= review_image_alt($review_data) ?>">
						<q>
							<?= review_text($review_data) ?>
						</q>
					</p>
					<p>
						<img src="images/critic.gif" alt="Critic">
						<?= review_author($review_data) ?><br>
						<?= review_organization($review_data) ?>
					</p>
			<?php
					$k++;
				}
				// ADD CODE HERE

				
				
				
				////////////////
			?>	
		</div>

	</div>
	<p id="pagenumber">(1-10) of 88</p>
</div>

<?php
	include('include/closing.html');
?>
