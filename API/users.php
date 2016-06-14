<?php
header("Access-Control-Allow-Origin: *");

include "database.php";

$mode = "user";
if(isset($_GET["mode"]))
	$mode = $_GET["mode"];

if($mode == "user")
{
	if(isset($_GET["id"]))
	{
		echo json_encode(FinalizeChar($users->GetRow($_GET["id"])));
	}
	else if (isset($_GET["phoneId"]))
	{
		$id = $_GET["phoneId"];
		$user = $users->Where("`phoneId`='$id'")[0];
		
		echo json_encode(FinalizeChar($user));
	}
	else
	{
		$all = $users->All();
		
		for($i = 0; $i < count($all); $i++)
		{
			$all[$i] = FinalizeChar($all[$i]);
		}
		
		echo json_encode($all);
	}
}
else if ($mode == "prefs")
{
	if(isset($_GET["id"]))
	{
		$user = FinalizeChar($users->GetRow($_GET["id"]));
		
		if(isset($_GET["prefId"]))
		{
			if($_GET["prefId"] - 1 < count($user->prefs) && $_GET["prefId"] > 0)
				echo json_encode($user->prefs[$_GET["prefId"] - 1]);
			else
				echo "[]";
		}
		else
		{
			echo json_encode($user->prefs);
		}
	}
	else
	{
		
	}
}
	
	
function FinalizeChar($raw)
{
	include "roles.php";
	
	if($raw == null)
		return null;

	include "database.php";
	$raw->prefs = json_decode($raw->prefs);
	$raw->networks = [];
	unset($raw->password);// = "****";
	$links = $userToNetwork->Where("`userId`='$raw->id'");
	
	for($i = 0; $i < count($links); $i++)
	{
		$network = $networks->GetRow($links[$i]->networkId);
		$network->role = $roles[$links[$i]->role];
		$network->url = "http://timfalken.com/hr/smarthome/networks/" . $network->id;
		array_push($raw->networks, $network);
	}
	
	return $raw;
}