<?php

include "database.php";

if(isset($_GET["id"]))
	echo json_encode($networks->GetRow($_GET["id"]));
else
	echo json_encode($networks->All());