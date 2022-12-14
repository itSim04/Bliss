<?php

require 'connection.php';

if (array_key_exists("user_id", $_GET)) {

	try {

		$user_id = $_GET["user_id"];

		if (array_key_exists("root_id", $_GET)) {
			$query = $mysqli->prepare("SELECT *, 

			CASE WHEN (SELECT EXISTS(SELECT * FROM diamonds WHERE diamonds.user_id = ? && diamonds.gem_id = gems.gem_id)) THEN true 
			ELSE false 
			END as is_diamonded, 
			
			CASE WHEN (SELECT NOT EXISTS(SELECT * FROM answers WHERE answers.user_id = ? && answers.gem_id = gems.gem_id)) THEN 0 
			ELSE (SELECT option_chosen FROM answers WHERE answers.user_id = ? && answers.gem_id = gems.gem_id)
			END as is_voted 
			
			FROM gems WHERE root_id = ? && gem_id != ?  ");
			$query->bind_param("iiiii", $user_id, $user_id, $user_id, $_GET["root_id"], $_GET["root_id"]);
		} else {
			$query = $mysqli->prepare("SELECT *, 

			CASE WHEN (SELECT EXISTS(SELECT * FROM diamonds WHERE diamonds.user_id = ? && diamonds.gem_id = gems.gem_id)) THEN true 
			ELSE false 
			END as is_diamonded, 
			
			CASE WHEN (SELECT NOT EXISTS(SELECT * FROM answers WHERE answers.user_id = ? && answers.gem_id = gems.gem_id)) THEN 0 
			ELSE (SELECT option_chosen FROM answers WHERE answers.user_id = ? && answers.gem_id = gems.gem_id)
			END as is_voted 
			
			FROM gems WHERE root_id = gem_id");
			$query->bind_param("iii", $user_id, $user_id, $user_id);
		}
		$query->execute();
		$result = $query->get_result();

		$subquery = "SELECT * FROM users WHERE user_id = 0 ||";
		$gems = [];
		$users = [];

		while ($row = mysqli_fetch_assoc($result)) {

			$gems[] = $row;
			$subquery .= " user_id = " . $row["owner_id"] . " ||";
		}

		$subquery = substr($subquery, 0, -3);

		$query = $mysqli->prepare($subquery);
		$query->execute();
		$result = $query->get_result();

		$users = [];

		while ($row = mysqli_fetch_assoc($result)) {

			$users[] = $row;
		}

		$output["success"] = true;
		$output["error"] = 0;

		$result = [];
		$result["user"] = $users;
		$result["gem"] = $gems;
		$output["query_result"] = $result;
	} catch (Exception $e) {

		$output["success"] = false;
		$output["query_result"] = 0;
		$output["error"] = $e->getMessage();
	}
} else {

	$output["success"] = false;
	$output["query_result"] = null;
	$output["error"] = "Missing User ID";
}
echo json_encode($output);
