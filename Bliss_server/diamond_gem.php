<?php

require 'connection.php';

if (array_key_exists("user_id", $_POST) && array_key_exists("gem_id", $_POST) && array_key_exists("diamond_date", $_POST)) {

	$user_id = $_POST["user_id"];
	$gem_id = $_POST["gem_id"];
	$diamond_date = $_POST["diamond_date"];

	try {

		
		$query = $mysqli->prepare("SELECT * FROM diamonds WHERE user_id = ? AND gem_id = ?");
		$query->bind_param("ii", $user_id, $gem_id);
		$query->execute();
		$result = $query->get_result();

		if (mysqli_num_rows($result) > 0) {

			$output["success"] = false;
			$output["error"]   = "Diamond already Exists";

		} else {

			$query = $mysqli->prepare("INSERT INTO diamonds (user_id, gem_id, diamond_date) VALUES (?, ?, ?)");
			$query->bind_param("iis", $user_id, $gem_id, $diamond_date);
			$query->execute();

			$query = $mysqli->prepare("UPDATE gems SET diamonds_TEMP = diamonds_TEMP + 1 WHERE gem_id = ?");
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
