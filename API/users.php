<?php

include "database.php";

if(isset($_GET["id"]))
	echo json_encode($users->GetRow($_GET["id"]));
else
	echo json_encode($users->All());