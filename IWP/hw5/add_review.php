<?php
    // ADD CODE HERE
    
    
    ////////////////
	
	$name = $_SESSION[ 'name' ];
	$organization = $_SESSION[ 'organization' ];
	
	$rating = $_GET["rating"];
	$review = trim(str_replace("\n"," ",$_GET["review"]));
	
	if ( empty($review) ) {
		include("add_review_error.php");
		die();
	}
	$target_dir = "moviedb/" . $_GET["film"];
	$review_number = count(glob($target_dir."/review*.txt")) + 1;
	$review_path = $target_dir . "/review" . $review_number . ".txt";
	$text = "";
	# text review
	$text = $text . $review . "\n";
	# rating
	$text = $text . $rating . "\n";
	# name of the reviewer
	$text = $text . $name . "\n";
	# organization of the reviewer
	$text = $text . $organization;
	file_put_contents($review_path, $text);
	header("Location: movie.php?film=" . $_GET["film"]);
?>
