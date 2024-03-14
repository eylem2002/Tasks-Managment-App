<?php 

$conn = mysqli_connect("localhost", "root", "12345678", "Rosie");
if($conn)
{
   
 
    $sql="Select * from tasks";
    
    $result=$conn->query($sql);
    if($result==TRUE){

        while($row=$result ->fetch_assoc())
        {
            $item["taskn"]=$row["taskName"];
            $item["desc"]=$row["description"];
            $item["timeT"]=$row["timeToDo"];
            $item["timeC"]=$row["timeCreate"];
            $task[]=$item;


        }

        echo json_encode($task);

        
        
        }
        else {
            $item["taskn"]="No Name";

            $item["desc"]="No description";

            $item["timeT"]="No Time";

            $item["timeC"]=="No date";

            $task[]=$item;
           

            echo json_encode($task);
            
            
        } 
    }
    else {
    
        echo"Failed Connection".sqli_error($conn);
    }
    
    

?>