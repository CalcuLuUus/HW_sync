<?php
    session_start();
    include("include/util.inc.php");

	$user_id = $_GET['user_id'];
	$course_id = $_GET['course_id'];
	$stu_info = getuserinfo($user_id)->fetch();
	$firstname = $stu_info['firstname'];
	$lastname = $stu_info['lastname'];
	
	$PDO = getPDO();
	$score = $PDO->query("SELECT * FROM scores WhERE user_id = $user_id and course_id = $course_id")->fetch();
	$s1 = $score['score1'];
	$s2 = $score['score2'];
	$s3 = $score['score3'];
	$aver = ($s1 + $s2 + $s3) / 3;

	include("include/opening.inc.php");	
?>
		<h1>Update student data</h1>
		<h3><?= courseinfo2string(getcourseinfo($course_id)) ?></h3>
		<table>
			<tr>
				<th>Student ID</th><th>First name</th><th>Last name</th><th>score 1</th><th>score 2</th><th>score 3</th><th>Average</th><th> </th>
			</tr>
			<tr>
            <form action="update-action.php" method="get">
				<input type="text"	name="user_id" hidden value=<?= $user_id ?>>
				<input type="text"	name="course_id" hidden value=<?= $course_id ?>>
				<td> 
					<?= $user_id ?>
				</td>
				<td>
					<input type="text" name="firstname" value=<?= $firstname ?>>
				</td>
				<td>
					<input type="text" name="lastname" value=<?= $lastname ?>>
				</td>
				<td>
					<input type="text" name="score1" value=<?= $s1 ?>>
				</td>
				<td>
					<input type="text" name="score2" value=<?= $s2 ?>>
				</td>
				<td>
					<input type="text" name="score3" value=<?= $s3 ?>>
				</td>
				<td> <?= round($aver,2) ?> </td>
				<td> <input type="submit" value="update"> </td>
            </form>
			</tr>
		</table>
<?php
	include("include/closing.html");
?>
