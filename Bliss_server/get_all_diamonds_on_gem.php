<?php

require 'connection.php';

try {
	$query = $mysqli->prepare("SELECT DISTINCT users.user_id, users.username, diamonds.diamond_date FROM diamonds, gems, users WHERE diamonds.gem_id = ? AND diamonds.user_id = users.user_id AND diamonds.user_id");
	$query->bind_param("i", $_POST["gem_id"]);
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