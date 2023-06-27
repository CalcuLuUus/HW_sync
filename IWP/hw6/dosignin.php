<?php
	session_start();
	include("include/util.inc.php");

	$login = $_POST['login'];
	$password = $_POST['password'];

    # TO COMPLETE
	$PDO = getPDO();
	$login = $PDO->quote($login);
	$res = $PDO->query("SELECT * FROM users WHERE email = $login;");
	
	
	$errorcode = "";
	$OK = 1;
	if($res->rowCount() === 0){
		$errorcode = "USER_NOT_FOUND";
		$OK = 0;
	}else{
		$res = $res->fetch();
		$pw = $res['password'];
		if(!password_verify($password, $pw)){
			$errorcode = "WROND_PASSWORD_OR_WRONG_USERNAME";
			$OK = 0;
		}
	}

	if($OK === 0){
		header("Location: index.php?errorcode=".$errorcode);
		exit();
	}

	$row = $res;
	$_SESSION['role'] = $row['role'];
	$_SESSION['user_id'] = $row['user_id'];
	$_SESSION['firstname'] = $row['firstname'];
	$_SESSION['lastname'] = $row['lastname'];

	if(session_get_role() == "STUDENT"){
		header("Location: student-home.php");
		exit();
	}else{
		header("Location: teacher-home.php");
		exit();
	}
	
?>
