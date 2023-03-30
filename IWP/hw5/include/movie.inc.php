<?php
 
    /////// NEW FUNCTIONS TO COMPLETE ///////

    // returns the role of the user, or NULL if
    // the user is not registered or doesn't have
    // a role. The role should be stored in the
    // $_SESSION global array
    function role() {
       
    }

    // returns the error message (a string) 
    // matching the error code $errorcode
    function signup_error($errorcode) {
        
    }

    // checks if the user is logged in with role '$role_required'
    // if not, redirects the user to the signin page
    function check_login($role_required) {
       
    }

    //////////////////////////////////////////
   
    // returns the array of
    // all movie folder paths
    function all_movie_path() {
        return glob("moviedb/*");
    }

    // returns the the key of the 
    // film of folder path $movie_path
    function movie_key($movie_path) {
        return basename($movie_path);
    }

    // returns the folder path of
    // the film of key $film
    function movie_path($film) {
        return "moviedb/".$film;
    }

    // returns the movie data (the content of the
    // info.txt file) from the movie of path $movie_path
    function movie_data($movie_path) {
        return file($movie_path."/info.txt",FILE_IGNORE_NEW_LINES);
    }

    // returns the array of all the review file paths
    // of themovie of folder path $movie_path
    function all_review_path($movie_path) {
        return glob($movie_path."/review*.txt");
    }

    // returns the title from the
    // movie data $movie_data
    function title($movie_data) {
        return $movie_data[0];
    }

    // returns the year from the
    // movie data $movie_data
    function year($movie_data) {
        return $movie_data[1];	
    }

    // returns the rating from the
    // movie data $movie_data
    function rating($movie_data) {
        return $movie_data[2];
    }

    // returns the rating image path
    // from the movie data $movie_data
    function rating_image_path($movie_data) {
        if ( rating($movie_data) >= 60 )
            return "images/freshlarge.png";
        return "images/rottenlarge.png";
    }

    // returns the rating image alternative
    // from the movie data $movie_data
    function rating_image_alt($movie_data) {
        if ( rating($movie_data) >= 60 )
            return "Fresh";
        return "Rotten";
    }

    // returns the overview image path
    // from the movie of path $movie_path
    function overview_image_path($movie_path) {
        return $movie_path."/overview.png";
    }

    // returns the movie overview items (the content of the
    // overview.txt file) from the movie of path $movie_path
    function overview($movie_path) {
        return file($movie_path."/overview.txt",FILE_IGNORE_NEW_LINES);
    }

    // returns the dt part (term) of
    // the overview item $overview_item
    function dt($overview_item) {
        $array = explode(":",$overview_item);
        return $array[0];
    }

    // returns the dd part (definition) of
    // the overview item $overview_item
    function dd($overview_item) {
        $array = explode(":",$overview_item);
        return $array[1];
    }

    // returns the review data of
    // the review of path $review_path
    function review_data($review_path) {
        return file($review_path,FILE_IGNORE_NEW_LINES);
    }

    // returns the review image path of
    // the review data $review_data
    function review_image_path($review_data) {
        return strtolower(trim($review_data[1])) . ".gif";
    }

    // returns the review image alternative
    // of the review data $review_data
    function review_image_alt($review_data) {
        return trim($review_data[1]);
    }

    // returns the review image text of
    // the review data $review_data
    function review_text($review_data) {
        return $review_data[0];
    }

    // returns the review author of
    // the review data $review_data
    function review_author($review_data) {
        return $review_data[2];
    }

    // returns the review organization
    // of the review data $review_data
    function review_organization($review_data) {
        return $review_data[3];
    }

?>
