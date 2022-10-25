<?php

require 'connection.php';

$user_id = $_POST["user_id"];
$tweet_id = $_POST["tweet_id"];
$like_date = $_POST["like_date"];


try {
	$query = $mysqli->prepare("INSERT INTO likes (user_id, tweet_id, like_date) VALUES (?, ?, ?)");
	$query->bind_param("ss", $user_id, $tweet_id, $like_date);
	$query->execute();
	$output["success"] = true;
	$output["error"] = 0;

}

catch(Exception $e) {
	$output["success"] = false;
	$output["error"] = $e->getMessage();
}
print_r($output);