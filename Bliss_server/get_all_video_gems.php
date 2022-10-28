<?php

require 'connection.php';

try {
	$query = $mysqli->prepare("SELECT * FROM gems WHERE type = 2");
	$query->execute();
	$result = $query->get_result();

	$gems = [];
	while($row = mysqli_fetch_assoc($result)) {
		$gems[] = $row;
	}

	$output["success"] = true;
	$output["error"] = 0;
	$output["query_result"] = $gems;
}

catch(Exception $e) {
	$output["success"] = false;
	$output["query_result"] = 0;
	$output["error"] = $e->getMessage();
}

echo json_encode($output);