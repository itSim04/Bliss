<?php

require 'connection.php';

$gem_date = $_POST["gem_date"];
$edit_date = $_POST["edit_date"];
$owner_id = $_POST["owner_id"];
$content = $_POST["content"];
$type = $_POST["type"];

try {
	$query = $mysqli->prepare("INSERT INTO gems (gem_id, mine_date, edit_date, content, type, owner_id) VALUES (NULL, ?, ?, ?, ?, ?)");
	$query->bind_param("sssis", $gem_date, $edit_date, $content, $type, $owner_id);
	$query->execute();		
	$output["success"] = true;
	$output["error"] = 0;
}

catch(Exception $e) {
	$output["success"] = false;
	$output["error"] = $e->getMessage();
}

echo json_encode($output);
