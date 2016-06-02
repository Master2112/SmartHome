<?php

include "database.php";

if(isset($_GET["id"]))
{
	echo json_encode(FinalizeChar($users->GetRow($_GET["id"])));
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