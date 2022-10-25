<?php

require 'add_tweet.php';

if($output["inserted_id"] != -1) {

	try {
		$query = $mysqli->prepare("INSERT INTO polltweets (tweet_id, prompt, option1, option2, option3, option4) VALUES (?, ?, ?, ?, ?, ?)");
		$query->bind_param("isssss", $output["inserted_id"], $_POST["prompt"], $_POST["option1"], $_POST["option2"], $_POST["option3"], $_POST["option4"]);
		$query->execute();
		$output["success"] = true;
		$output["error"] = 0;
	}

	catch(Exception $e) {
		$output["success"] = false;
		$output["error"] = $e->getMessage();
		$query = $mysqli->prepare("DELETE FROM tweets WHERE tweet_id = ?");
		$query->bind_param("i", $output["inserted_id"]);
		$query->execute();
	}

}
print_r($output);