<?php

require 'connection.php';

$user_id1 = $_POST["user_id1"];
$user_id2 = $_POST["user_id2"];
$follow_date = $_POST["follow_date"];

try {

	$query = $mysqli->prepare("INSERT INTO follows (user_id1, user_id2, follow_date) VALUES (?, ?, ?)");
	$query->bind_param("iis", $user_id1, $user_id2, $follow_date);
	$query->execute();

	$query = $mysqli->prepare("UPDATE users SET followings_TEMP = followings_TEMP + 1 WHERE user_id = ?");
	$query->bind_param("i", $user_id1);
	$query->execute();

	$query = $mysqli->prepare("UPDATE users SET followers_TEMP = followers_TEMP + 1 WHERE user_id = ?");
	$query->bind_param("i", $user_id2);
	$query->execute();

	$output["success"] = true;
	$output["error"] = 0;

}

catch(Exception $e) {
	$output["success"] = false;
	$output["error"] = $e->getMessage();
}
echo json_encode($output);