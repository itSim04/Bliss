<?php

require 'add_tweet.php';

if($output["inserted_id"] != -1) {

	try {
		$query = $mysqli->prepare("INSERT INTO `imagetweets` (`tweet_id`, `img_src`) VALUES (?, ?)");
		$query->bind_param("is", $output["inserted_id"], $_POST["content"]);
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