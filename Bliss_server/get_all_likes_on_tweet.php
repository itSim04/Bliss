<?php

require 'connection.php';

try {
	$query = $mysqli->prepare("SELECT DISTINCT users.user_id, users.username, likes.like_date FROM likes, tweets, users WHERE likes.tweet_id = ? AND likes.user_id = users.user_id AND likes.user_id");
	$query->bind_param("i", $_POST["tweet_id"]);
	$query->execute();
	$result = $query->get_result();

	$tweets = [];
	while($row = mysqli_fetch_assoc($result)) {
		$tweets[] = $row;
	}

	$output["success"] = true;
	$output["error"] = 0;
	$output["result"] = $tweets;
}

catch(Exception $e) {
	$output["success"] = false;
	$output["result"] = 0;
	$output["error"] = $e->getMessage();
}

print_r($output);