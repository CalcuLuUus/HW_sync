<!DOCTYPE html>
<html>

<head>
    <title>Rancid Tomatoes</title>
    <meta charset="utf-8">
    <link href="movie.css" type="text/css" rel="stylesheet">
</head>

<?php
$movie = $_GET["film"];
$info_path = "moviedb/" . $movie;
$overview = $info_path . "/overview.png";
$info = file($info_path . "/info.txt");
$title = $info[0];
$year = $info[1];
$rating_perc = $info[2];
$review_txt_path_array = glob($info_path . "/review*.txt");
$num_of_review = count($review_txt_path_array);

// print all the comment into two sections
// $path : file path
// $is_left : print into left section
function print_comment($path, $is_left)
{
    $review_txt_path_array = glob($path . "/review*.txt");
    $num_of_review = count($review_txt_path_array);
    $st = 0;
    $ed = $num_of_review;
    if ($is_left) {
        $ed = ceil($num_of_review / 2);
    } else {
        $st = ceil($num_of_review / 2);
    }

    for ($i = $st; $i < $ed; $i++) {
        $review_txt = file($review_txt_path_array[$i]);
        $review_content = $review_txt[0];
        $review_img_src = "images/" . strtolower(trim($review_txt[1])) . ".gif";
        $review_name = $review_txt[2];
        $review_pub = $review_txt[3];

        echo "<p class=\"comment\">";
        echo "<img src= $review_img_src alt=\"Rotten\">";
        echo "<q> $review_content </q>";
        echo "</p>";
        echo "<p class=\"infoandicon\">";
        echo "<img src=\"images/critic.gif\" alt=\"Critic\">";
        echo " $review_name <br>";
        echo "$review_pub ";
        echo "</p>";

    }
}
?>

<body>
    <div id="headline">
        <img src="images/rancidbanner.png" alt="Rancid Tomatoes">
    </div>

    <div id="backhome">
        <a href="home.html">
            <img src="images/goback.png" alt="Arrow to home">
        </a>
    </div>

    <h1 id="title">
        <?= $title . "(" . $year . ")" ?>
    </h1>

    <div id="overall">

        <div id="rightsection">

            <div id="poster">
                <img src=<?= $overview ?> alt="general overview">
            </div>

            <div id="intro">
                <dl>
                    <?php
                    // print all the overviwe as dt/dd format
                    $overview_txt = file($info_path . "/overview.txt");
                    foreach ($overview_txt as $textline) {
                        $split_res = explode(':', $textline);
                        $dt_txt = $split_res[0];
                        $dd_txt = $split_res[1];
                        ?>
                        <dt>
                            <?= $dt_txt ?>
                        </dt>
                        <dd>
                            <?= $dd_txt ?>
                        </dd>
                    <?php } ?>
                </dl>
            </div>

        </div>

        <div id="leftsection">

            <div id="headofcomment">
                <?php
                $pic = "images/";
                if ($rating_perc >= 60) {
                    $pic = $pic . "freshlarge.png";
                } else {
                    $pic = $pic . "rottenlarge.png";
                }
                ?>

                <img src=<?= $pic ?> alt="Rotten">
                <?= $rating_perc ?>%
            </div>

            <div id="commentarea">

                <div id="leftcommentarea">
                    <?php print_comment($info_path, true) ?>
                </div>

                <div id="rightcommentarea">
                    <?php print_comment($info_path, false) ?>
                </div>

            </div>

        </div>

        <div id="bottom">
            <p>(1-
                <?= $num_of_review ?>) of
                <?= $num_of_review ?>
            </p>
        </div>

    </div>




</body>

</html>