<!DOCTYPE html>
<html lang="en">
	<head>
		<meta charset="utf-8">
		<title>SNU</title>
		<meta name="viewport" content="width=device-width, initial-scale=1.0">
		<link rel="stylesheet" type="text/css" href="css/style.css">
	</head>
	<body>
	<div id="banner">
		<img src="images/snu.png" title="SNU">
<?php
	$role = session_get_role();
	$name = session_get_name();
	$icon = "images/student.png";
	if($role === "TEACHER"){
		$icon = "images/teacher.png";
	}
?>
	<span><a href='signout.php'><img src=<?= $icon ?> title='click to sign-out'></a> <?= $name ?></span>
	</div>
	<div id="container">
