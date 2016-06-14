<?php
header("Access-Control-Allow-Origin: *");

include "database.php";

if(isset($_GET["id"]))
{
	echo json_encode(FinalizeChar($crownstones->GetRow($_GET["id"])));
}
else
{
	$all = $crownstones->All();
	
	for($i = 0; $i < count($all); $i++)
	{
		$all[$i] = FinalizeChar($all[$i]);
	}
	
	echo json_encode($all);
}
	
	
function FinalizeChar($raw)
{
	if($raw == null)
		return null;

	include "database.php";
	
	$network = $networks->GetRow($raw->networkId);
	
	$raw->network = $network;
	
	$raw->network->url = "http://timfalken.com/hr/smarthome/networks/" . $raw->networkId;
	
	unset($raw->networkId);
	
	return $raw;
}