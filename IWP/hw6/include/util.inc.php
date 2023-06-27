<?php

$USER_TABLE = 'users';
$SCORE_TABLE = 'scores';
$COURSE_TABLE = 'courses';
$PDO = getPDO();

// returns a PDO object
function getPDO() {
    $host = 'localhost'; # TO BE UPDATED!
    $db   = 'hw6';
    $user = 'root';           # TO BE UPDATED!
    $pass = '';   # TO BE UPDATED!
    $charset = 'utf8';

    $dsn = "mysql:host=$host;dbname=$db;charset=$charset";
    $options = [
        PDO::ATTR_ERRMODE            => PDO::ERRMODE_EXCEPTION,
        PDO::ATTR_DEFAULT_FETCH_MODE => PDO::FETCH_ASSOC,
        PDO::ATTR_EMULATE_PREPARES   => false,
    ];
    try {
        return new PDO($dsn, $user, $pass, $options);
    } catch (\PDOException $e) {
        throw new \PDOException($e->getMessage(), (int)$e->getCode());
    }
}

# below you should define as many functions as you need
# to make the code of the PHP scripts easy to write

function session_get_role(){
    if(isset($_SESSION['role'])){
        return $_SESSION['role'];
    }
    return null;
}

function session_get_userid(){
    if(isset($_SESSION['user_id'])){
        return $_SESSION['user_id'];
    }
    return null;
}

function session_get_name(){
    if(isset($_SESSION['firstname'])){
        return $_SESSION['firstname'] . " " . $_SESSION['lastname'];
    }
    return null;
}

function getcourseinfo($cid){
    $PDO = getPDO();
    $cid = $PDO->quote($cid);
    $res = $PDO->query("SELECT * FROM courses WHERE course_id = $cid");
    return $res;
}

// to get student scorelist
function getscore($uid){
    $PDO = getPDO();
    $uid = $PDO->quote($uid);
    $res = $PDO->query("SELECT * FROM scores WHERE user_id = $uid");
    return $res;
}

function courseinfo2string($PDO_course){
    $course = $PDO_course->fetch();
    $PDO = getPDO();
    $cid = $PDO->quote($course["course_id"]);
    $cnt_of_course = $PDO->query("SELECT COUNT(*) AS num FROM scores WHERE course_id = $cid");
    $cnt_of_course = $cnt_of_course->fetch()['num'];
    return $course['title']."(".$course['code'].") - ".$course['semester']." semester - ".$cnt_of_course." students";
}

// to get course list of teacher
function getcourselist($uid){
    $PDO = getPDO();
    $uid = $PDO->quote($uid);
    $res = $PDO->query("SELECT * FROM courses WHERE user_id = $uid");
    return $res;
}

// to get score list of course
function getscorelist($cid){
    $PDO = getPDO();
    $cid = $PDO->quote($cid);
    $res = $PDO->query("SELECT * FROM scores WHERE course_id = $cid");
    return $res;
}

function getuserinfo($uid){
    $PDO = getPDO();
    $uid = $PDO->quote($uid);
    $res = $PDO->query("SELECT * FROM users WHERE user_id = $uid");
    return $res;
}
?>
