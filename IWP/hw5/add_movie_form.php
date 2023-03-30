<?php
    // ADD CODE HERE
    
    
    ////////////////

?>

<link href="css/common.css" type="text/css" rel="stylesheet">
<link href="css/form.css" type="text/css" rel="stylesheet">

<h1>Add a movie</h1>
		
<div id="content">
 
<form method="post" action="add_movie.php" enctype="multipart/form-data">
	
	<div>Movie information</div>

	<label for="info">Info</label><input id="info" name="info" type="file" accept=".txt" required="required"><br>
	<label for="overview">Overview</label><input id="overview" name="overview" type="file" accept=".txt" required="required"><br>
	<label for="image">Image</label><input id="image" name="image" type="file" accept=".png" required="required"><br>

	<div>Reviews</div>

	<label for="review1">Review 1</label><input id="review1" name="review1" type="file" accept=".txt" required="required"><br>

<?php
	for ( $i = 2; $i <= 10; $i++ )
		echo "	<label for='review$i'>Review $i</label><input id='review$i' name='review$i' type='file' accept='.txt'><br>\n";
?>

	<div>
		<input id="submit" name="submit" type="submit" value="Submit">
	</div>

</form>

</div>

<?php
	include('include/closing.html');
?>
