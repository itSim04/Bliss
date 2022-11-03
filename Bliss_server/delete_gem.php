<?php

require 'connection.php';

if (array_key_exists("gem_id", $_POST)) {

    $gem_id = $_POST["gem_id"];
    
    try {

        $query = $mysqli->prepare("DELETE FROM gems WHERE gem_id = ?");
        $query->bind_param("i", $gem_id);
        $query->execute();
        

        $output["success"] = true;
        $output["error"] = 0;

    } catch (Exception $e) {

        $output["success"] = false;
        $output["error"] = $e->getMessage();
    }

} else {

    $output["success"] = false;
    $output["error"] = "Missing Gem ID";

}

echo json_encode($output);
