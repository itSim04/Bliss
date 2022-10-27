<?php

require 'connection.php';

$tweet_date = $_POST["tweet_date"];
$edit_date = $_POST["edit_date"];
$owner_id = $_POST["owner_id"];
$content = $_POST["content"];
$type = $_POST["type"];

try {
	$query = $mysqli->prepare("INSERT INTO tweets (tweet_id, tweet_date, edit_date, content, type, owner_id) VALUES (NULL, ?, ?, ?, ?, ?)");
	$query->bind_param("sssis", $tweet_date, $edit_date, $content, $type, $owner_id);
	$query->execute();		
	$output["success"] = true;
	$output["error"] = 0;
}

catch(Exception $e) {
	$output["success"] = false;
	$output["error"] = $e->getMessage();
}

echo json_encode($output);
