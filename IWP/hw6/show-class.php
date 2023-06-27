<?php
	session_start();
	include("include/util.inc.php");

	$user_id = $_SESSION['user_id'];
    $course_id = $_GET['course_id'];
	$PDO_scorelist = getscorelist($course_id);
	
	include("include/opening.inc.php");	
?>
		<h1> <?= courseinfo2string(getcourseinfo($course_id))?></h1>
		<table>
			<tr>
				<th>Student ID</th><th>First name</th><th>Last name</th><th>Score 1</th><th>Score 2</th><th>Score 3</th><th>Average</th><th> </th>
			</tr>
<?php
	foreach($PDO_scorelist as $score){
		$sid = $score['user_id'];
		$s1 = $score['score1'];
		$s2 = $score['score2'];
		$s3 = $score['score3'];
		$aver = ($s1 + $s2 + $s3) / 3;
		$stu_info = getuserinfo($sid)->fetch();
		$firstname = $stu_info['firstname'];
		$lastname = $stu_info['lastname'];

		$update_path = "<a href=\"update-form.php?course_id=".$course_id."&user_id=".$sid."\">update</a>";
		echo "<tr> <td>".$sid."</td> <td>".$firstname."</td> <td>".$lastname."</td> <td>".$s1."</td> <td>".$s2."</td> <td>".$s3."</td> <td>".round($aver, 2)."</td> <td> ".$update_path."</td><tr>";
	}
?>
		</table>
		<h3><a href="teacher-home.php">Back to home</a></h3>
<?php
	include("include/closing.html");
?>
