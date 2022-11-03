<?php

require 'connection.php';


if (array_key_exists("user_id", $_POST) && array_key_exists("answer_date", $_POST) && array_key_exists("gem_id", $_POST) && array_key_exists("option_chosen", $_POST)) {

    $gem_id = $_POST["gem_id"];
    $user_id = $_POST["user_id"];
    $option = $_POST["option_chosen"];
    $answer_date = $_POST["answer_date"];

    try {

        $query = $mysqli->prepare("SELECT * FROM gems WHERE gem_id = ?");
        $query->bind_param("i", $gem_id);
        $query->execute();
        $result = $query->get_result();

        $gem = mysqli_fetch_assoc($result);

        if ($gem["type"] != 3) {

            $output["success"] = false;
            $output["error"]   = "Invalid Tweet Type";

        } else {

            $content = json_decode($gem["content"]);

            switch ($option) {

                case 1:

                    $content->option1voters = $content->option1voters + 1;
                    break;

                case 2:

                    $content->option2voters = $content->option2voters + 1;
                    break;

                case 3:

                    $content->option3voters = $content->option3voters + 1;
                    break;

                case 4:

                    $content->option4voters = $content->option4voters + 1;
                    break;
            }

            $query = $mysqli->prepare("INSERT INTO answers (user_id, gem_id, answer_date, option_chosen) VALUES (?, ?, ?, ?)");
            $query->bind_param("iisi", $user_id, $gem_id, $answer_date, $option);
            $query->execute();

            $gem = json_encode($content);

            $query = $mysqli->prepare("UPDATE gems SET content = ? WHERE gem_id = ?");
            $query->bind_param("si", $gem, $gem_id);
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
