<?php
    // ADD CODE HERE
    
    
    ////////////////
    
    $target_dir = "moviedb/";
    
    $movie_number = count(glob($target_dir."*")) + 1;
    $movie_dirname = $target_dir . "movie" . $movie_number;
    mkdir($movie_dirname);
    # text information about the movie
    move_uploaded_file($_FILES["info"]["tmp_name"], $movie_dirname . "/info.txt");
    # text overview about the movie
    move_uploaded_file($_FILES["overview"]["tmp_name"], $movie_dirname . "/overview.txt");
    # image overview about the movie
    move_uploaded_file($_FILES["image"]["tmp_name"], $movie_dirname . "/overview.png");
    for ( $i = 1; $i <= 10; $i++ ) {
      if ( is_uploaded_file($_FILES["review$i"]["tmp_name"]) )
        move_uploaded_file($_FILES["review$i"]["tmp_name"], $movie_dirname . "/review$i.txt");
      else {
        break;
      }
    }
    header("Location: home.php");   
?>
