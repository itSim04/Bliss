<?php

require 'connection.php';

if (array_key_exists("mine_date", $_POST) && array_key_exists("edit_date", $_POST) && array_key_exists("owner_id", $_POST) && array_key_exists("content", $_POST) && array_key_exists("type", $_POST)) {
   
    $gem_date = date('y-m-d H:i:s', time());//$_POST["edit_date"];
    $edit_date = $_POST["edit_date"];
    $owner_id = $_POST["owner_id"];
    $content = $_POST["content"];
    $type = $_POST["type"];

    try {

        $query = $mysqli->prepare("INSERT INTO gems (gem_id, mine_date, edit_date, content, type, owner_id) VALUES (NULL, ?, ?, ?, ?, ?)");
        $query->bind_param("sssis", $gem_date, $edit_date, $content, $type, $owner_id);
        $query->execute();

        $gem_id = $mysqli->insert_id;

        $query = $mysqli->prepare("SELECT FROM Gems WHERE gem_id = ?");
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
    $output["error"]   = "Missing Attributes";
    
}

echo json_encode($output);
