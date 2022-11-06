<?php

require 'connection.php';

if (array_key_exists("gem_id", $_GET)) {

	try {

		$query = $mysqli->prepare("SELECT DISTINCT users.user_id, users.username, diamonds.diamond_date FROM diamonds, gems, users WHERE diamonds.gem_id = ? AND diamonds.user_id = users.user_id AND diamonds.user_id");
		$query->bind_param("i", $gem_id);
		$query->execute();
		$result = $query->get_result();

		$gems = [];


		while ($row = mysqli_fetch_assoc($result)) {

			
			$gems[] = $row;

		}
		
		
		$result = [];
		$result["diamond"] = $gems;	
		$output["query_result"] = $result;
		$output["success"] = true;
		$output["error"] = 0;
		

	} catch (Exception $e) {

		$output["success"] = false;
		$output["query_result"] = 0;
		$output["error"] = $e->getMessage();
	}

} else {

	$output["success"] = false;
	$output["error"]   = "Missing Attributes";

}

echo json_encode($output);
