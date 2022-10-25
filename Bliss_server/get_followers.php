<?php

require 'connection.php';

try {
	$query = $mysqli->prepare("SELECT follows.user_id1 FROM follows WHERE follows.user_id2 = ?");
	$query->bind_param("i", $_POST["user_id"]);
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