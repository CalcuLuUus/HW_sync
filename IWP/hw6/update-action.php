<?php
	session_start();
	include("include/util.inc.php");
    
	$user_id = $_GET[ "user_id" ];
	$course_id = $_GET[ "course_id" ];
    $firstname = $_GET[ "firstname" ];
    $lastname = $_GET[ "lastname" ];
    $score1 = $_GET[ "score1" ];
    $score2 = $_GET[ "score2" ];
    $score3 = $_GET[ "score3" ];
	$aver = ($score1 + $score2 + $score3) / 3; 

	$PDO = getPDO();
	$s1 = $PDO->quote($score1);
    $s2 = $PDO->quote($score2);
    $s3 = $PDO->quote($score3);
	$uid = $PDO->quote($user_id);
	$cid = $PDO->quote($course_id);
	$sql = "UPDATE scores SET score1=".$s1.",score2=".$s2.",score3=".$s3." WHERE user_id=".$uid." and course_id=".$cid.";";
	$PDO->exec($sql);

	include("include/opening.inc.php");
?>
		<h2>The student information have been updated</h2>
		<table>
			<tr>
				<th>Student ID</th><th>First name</th><th>Last name</th><th>score 1</th><th>score 2</th><th>score 3</th><th>Average</th>
			</tr>
                <?php
				echo "<tr> <td>".$user_id."</td> <td>".$firstname."</td> <td>".$lastname."</td> <td>".$score1."</td> <td>".$score2."</td> <td>".$score3."</td> <td>".round($aver, 2)."</td> <tr>";
				?>
		</table>
		<h3><a href="show-class.php?course_id=<?= $course_id ?>">Back to course page</a></h3>
<?php
	
	include("include/closing.html");
?>
