<?php

require 'connection.php';

try {
	$query = $mysqli->prepare("SELECT * FROM tweets NATURAL JOIN polltweets");
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

echo json_encode($output);