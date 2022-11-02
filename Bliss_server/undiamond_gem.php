<?php

require 'connection.php';

if (array_key_exists("user_id", $_POST) && array_key_exists("gem_id", $_POST)) {

	$user_id = $_POST["user_id"];
	$gem_id = $_POST["gem_id"];

	try {

		$query = $mysqli->prepare("SELECT * FROM diamonds WHERE user_id = ? AND gem_id = ?");
		$query->bind_param("ii", $user_id, $gem_id);
		$query->execute();
		$result = $query->get_result();

		if (mysqli_num_rows($result) <= 0) {

			$output["success"] = false;
			$output["error"]   = "Diamond does not Exist";
			
		} else {

			$query = $mysqli->prepare("DELETE FROM diamonds WHERE user_id = ? AND gem_id = ?");
			$query->bind_param("ii", $user_id, $gem_id);
			$query->execute();

			$query = $mysqli->prepare("UPDATE gems SET diamonds_TEMP = diamonds_TEMP - 1 WHERE gem_id = ?");
			$query->bind_param("i", $gem_id);
			$query->execute();

			$output["success"] = true;
			$output["error"] = 0;
		}
	} catch (Exception $e) {

		$output["success"] = false;
		$output["error"] = $e->getMessage();
	}
} else {

	$output["success"] = false;
	$output["error"]   = "Missing Attributes";
}
echo json_encode($output);
