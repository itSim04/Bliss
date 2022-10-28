<?php

require 'connection.php';

$user_id = $_POST["user_id"];
$gem_id = $_POST["gem_id"];
$diamond_date = $_POST["diamond_date"];


try {
	$query = $mysqli->prepare("INSERT INTO diamonds (user_id, gem_id, diamond_date) VALUES (?, ?, ?)");
	$query->bind_param("iis", $user_id, $gem_id, $diamond_date);
	$query->execute();
	$output["success"] = true;
	$output["error"] = 0;

}

catch(Exception $e) {
	$output["success"] = false;
	$output["error"] = $e->getMessage();
}
echo json_encode($output);