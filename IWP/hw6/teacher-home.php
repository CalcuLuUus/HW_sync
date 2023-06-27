<?php
	session_start();
	include("include/util.inc.php");
	$user_id = session_get_userid();
	$name = explode(" ",  session_get_name());
	$firstname = $name[0];
	$lastname = $name[1];
	$PDO_courses = getcourselist($user_id);

	include("include/opening.inc.php");
?>
		<h1>My courses</h1>
        <table>
<?php
	foreach($PDO_courses as $course){
		echo "<tr> <td>".courseinfo2string(getcourseinfo($course['course_id']))."</td> <td> <a href=\"show-class.php?course_id=".$course["course_id"]."\">view class</a></td> </tr>"; 
	}
?>
        </table>
<?php
	include("include/closing.html");
?>
