<?php
    // This is the action of the form generated by
    // add_movie_form.php
    // After adding the new movie in the database
    // this script should redirect the user to the
    // home page (home.php)
    include("include/movie.inc.php");

    $movie_list = all_movie_path();
    $new_movie_path = 'moviedb/movie' . count($movie_list)+1;
    print($new_movie_path);
    mkdir($new_movie_path);

    if (is_uploaded_file($_FILES["info"]["tmp_name"])) {
        move_uploaded_file($_FILES["info"]["tmp_name"], 
       $new_movie_path . "/info.txt");
    }

    if (is_uploaded_file($_FILES["overview"]["tmp_name"])) {
        move_uploaded_file($_FILES["overview"]["tmp_name"], 
       $new_movie_path . "/overview.txt");
    }

    if (is_uploaded_file($_FILES["image"]["tmp_name"])) {
        move_uploaded_file($_FILES["image"]["tmp_name"], 
       $new_movie_path . "/overview.png");
    }

    for($i = 1; $i <= 10; $i++)
    {
        if(isset($_FILES["review" . $i]))
        {
            if (is_uploaded_file($_FILES["review".$i]["tmp_name"])) {
                move_uploaded_file($_FILES["review".$i]["tmp_name"], 
               $new_movie_path . "/review" . $i . ".txt");
            }
        }
    }

    header("Location: home.php");
    exit();

?>
