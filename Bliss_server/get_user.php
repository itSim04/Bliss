<?php

require 'connection.php';



if (array_key_exists("user_id", $_POST)) {

    $user_id = $_POST["user_id"];
    try {
        $query = $mysqli->prepare("SELECT * FROM users WHERE user_id = ?");
        $query->bind_param("i", $user_id);
        $query->execute();
        $result = $query->get_result();

        $row = mysqli_fetch_assoc($result);

        $output["success"] = true;
        $output["error"] = 0;
        $output["query_result"] = $row;
    } catch (Exception $e) {
        $output["success"] = false;
        $output["query_result"] = null;
        $output["error"] = $e->getMessage();
    }
} else {

    $output["success"] = false;
    $output["query_result"] = null;
    $output["error"] = "Missing User ID";
}

echo json_encode($output);
