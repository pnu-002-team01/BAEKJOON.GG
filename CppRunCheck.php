<?php
$filename = $_POST[source];
$myfile = fopen("$filename","w") or die("Unable to open file!");
$txt = $_POST[content];
fwrite($myfile,$txt);
fclose($myfile);
$output;
$return_var;
$source=$_POST[source];
$output=shell_exec("cppcheck " .$source . " 2>&1");
echo "<pre>$output</pre>";
?>
