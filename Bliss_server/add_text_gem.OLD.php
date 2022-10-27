<?php

require 'add_gem.php';

if($output["inserted_id"] != -1) {

	try {
		$query = $mysqli->prepare("INSERT INTO `textgems` (`gem_id`, `content`) VALUES (?, ?)");
		$query->bind_param("is", $output["inserted_id"], $_POST["content"]);
		$query->execute();
		$output["success"] = true;
		$output["error"] = 0;
	}

	catch(Exception $e) {
		$output["success"] = false;
		$output["error"] = $e->getMessage();
		$query = $mysqli->prepare("DELETE FROM gems WHERE gem_id = ?");
		$query->bind_param("i", $output["inserted_id"]);
		$query->execute();
	}
}
echo json_encode($output);