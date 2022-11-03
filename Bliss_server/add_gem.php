<?php

require 'connection.php';

if (array_key_exists("mine_date", $_POST) && array_key_exists("edit_date", $_POST) && array_key_exists("owner_id", $_POST) && array_key_exists("content", $_POST) && array_key_exists("type", $_POST)) {
   
    $gem_date = $_POST["mine_date"];
    $edit_date = $_POST["edit_date"];
    $owner_id = $_POST["owner_id"];
    $content = $_POST["content"];
    $type = $_POST["type"];

    try {


        if(!array_key_exists("root_id", $_POST)) {
           
            $query = $mysqli->prepare("INSERT INTO gems (`gem_id`, `mine_date`, `edit_date`, `content`, `type`, `owner_id`) VALUES (NULL, ?, ?, ?, ?, ?)");
            $query->bind_param("sssis", $gem_date, $edit_date, $content, $type, $owner_id);
            $query->execute();
            $gem_id = $mysqli->insert_id;

            $query = $mysqli->prepare("UPDATE gems SET root_id = ? WHERE gem_id = ?");
            $query->bind_param("ii", $gem_id, $gem_id);
            $query->execute();



        } else {

            $query = $mysqli->prepare("INSERT INTO gems (gem_id, mine_date, edit_date, content, type, owner_id, root_id) VALUES (NULL, ?, ?, ?, ?, ?, ?)");
            $query->bind_param("sssisi", $gem_date, $edit_date, $content, $type, $owner_id, $_POST["root_id"]);
            $query->execute();
            $gem_id = $mysqli->insert_id;
            

            

        }

        

        if (array_key_exists("root_id", $_POST)) {

            $query = $mysqli->prepare("UPDATE gems SET comments_TEMP = comments_TEMP + 1 WHERE gem_id = ?");
			$query->bind_param("i", $_POST["root_id"]);
			$query->execute();

        }

        $query = $mysqli->prepare("SELECT *, 

        CASE WHEN (SELECT EXISTS(SELECT * FROM diamonds WHERE diamonds.user_id = ? && diamonds.gem_id = gems.gem_id)) THEN true 
        ELSE false 
        END as is_diamonded, 
        
        CASE WHEN (SELECT NOT EXISTS(SELECT * FROM answers WHERE answers.user_id = ? && answers.gem_id = gems.gem_id)) THEN 0 
        ELSE (SELECT option_chosen FROM answers WHERE answers.user_id = ? && answers.gem_id = gems.gem_id)
        END as is_voted 
        
        FROM gems WHERE gem_id = ?");
        $query->bind_param("iiii", $owner_id, $owner_id,  $owner_id, $gem_id);
        $query->execute();
        $result = $query->get_result();

        $row = mysqli_fetch_assoc($result);

        $result = [];
		$result["gem"] = [$row];
        $output["query_result"] = $result;

        $output["success"] = true;
        $output["error"] = 0;

    } catch (Exception $e) {

        $output["success"] = false;
        $output["error"] = $e->getMessage();

    }
} else {

    $output["success"] = false;
    $output["error"]   = "Missing Attributes";
    
}

echo json_encode($output);
