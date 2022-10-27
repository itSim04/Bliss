<?php

require 'connection.php';

try {
	$query = $mysqli->prepare("SELECT follows.user_id2 FROM follows WHERE follows.user_id1 = ?");
	$query->bind_param("i", $_POST["user_id"]);
	$query->execute();
	$result = $query->get_result();

	$followings = [];
	while($row = mysqli_fetch_assoc($result)) {
		$followings[] = $row;
	}

	$output["success"] = true;
	$output["error"] = 0;
	$output["result"] = $followings;
}

catch(Exception $e) {
	$output["success"] = false;
	$output["result"] = 0;
	$output["error"] = $e->getMessage();
}

echo json_encode($output);