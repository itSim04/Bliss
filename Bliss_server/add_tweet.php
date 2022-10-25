<?php

require 'connection.php';

$tweet_date = $_POST["tweet_date"];
$edit_date = $_POST["edit_date"];
$owner_id = $_POST["owner_id"];

try {
	$query = $mysqli->prepare("INSERT INTO `tweets` (`tweet_id`, `tweet_date`, `edit_date`, `owner_id`) VALUES (NULL, ?, ?, ?)");
	$query->bind_param("sss", $tweet_date, $edit_date, $owner_id);
	$query->execute();
	$output["inserted_id"] = $mysqli->insert_id;
}

catch(Exception $e) {
	$output["success"] = false;
	$output["error"] = $e->getMessage();
	$output["inserted_id"] = -1;
}
