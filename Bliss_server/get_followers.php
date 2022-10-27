<?php

require 'connection.php';

try {
	$query = $mysqli->prepare("SELECT follows.user_id1 FROM follows WHERE follows.user_id2 = ?");
	$query->bind_param("i", $_POST["user_id"]);
	$query->execute();
	$result = $query->get_result();

	$followers = [];
	while($row = mysqli_fetch_assoc($result)) {
		$followers[] = $row;
	}

	$output["success"] = true;
	$output["error"] = 0;
	$output["result"] = $followers;
}

catch(Exception $e) {
	$output["success"] = false;
	$output["result"] = 0;
	$output["error"] = $e->getMessage();
}

echo json_encode($output);