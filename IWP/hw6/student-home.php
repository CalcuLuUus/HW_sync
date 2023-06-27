<?php
	session_start();
	include("include/util.inc.php");
	$user_id = session_get_userid();
	$PDO_scores = getscore($user_id);
	$name = explode(" ",  session_get_name());
	$firstname = $name[0];
	$lastname = $name[1];

	include("include/opening.inc.php");		
?>
		<h1>My courses</h1>
		
<?php
	foreach($PDO_scores as $score){
		$cid = $score['course_id'];
		$s1 = $score['score1'];
		$s2 = $score['score2'];
		$s3 = $score['score3'];
		$aver = ($s1 + $s2 + $s3) / 3;
		echo "<h3>" . courseinfo2string(getcourseinfo($cid)) . "</h3>";
		echo "<table>";
		echo "<tr> <th>Student ID</th> <th>First name</th> <th>Last name</th> <th>scores1</th> <th>scores2</th> <th>scores3</th> <th>Average</th> </tr>";
		echo "<tr> <td>".$score['user_id']."</td> <td>".$firstname."</td> <td>".$lastname."</td> <td>".$s1."</td> <td>".$s2."</td> <td>".$s3."</td> <td>".round($aver, 2)."</td> <tr>";
		echo "</table>";
	}
?>
<?php
	include("include/closing.html");
?>
