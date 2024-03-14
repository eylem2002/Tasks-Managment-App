<?php

    $servername = "localhost";
	$username = "root";
	$password= "12345678";
	$db_name = "Rosie";
	
	$ID = $_POST["id"];
	
	
	$conn = new mysqli($servername,$username,$password,$db_name);
	
	if($conn->connect_error){
		
		
		//die("not connected successfully :(");
	
	}else{
		
		//echo "connected successfully ";
		
		$sql = "DELETE FROM user WHERE Id = '$ID'";
	
	if($conn -> query($sql) === TRUE){
		
		
		
		 //echo "the record deleted successfully :)";
		 
				$task["error"] = false;
				$task["message"]= "Account Deleted successfully";
				
				echo json_encode($task);
	}else{
		
		
		//echo "Error: ".$sql."<br>".$conn ->error;
		
		        $task["error"] = true;
				$task["message"]= "something goes wrong!";
				
				echo json_encode($task);
	}
		
	}
	
	

?>