<?php
    include('include/util.inc.php');
    if(session_get_role() != null){
        session_destroy();
    }
    header("Location: index.php");
    exit();
?>
