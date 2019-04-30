<?php
	$filename = $_POST[source];
	$myfile = fopen("./cppsource/".$filename,"w") or die("Unable to open file!");
	$txt = $_POST[content];
	fwrite($myfile,$txt);
	fclose($myfile);
	$output;
	$output=shell_exec("cppcheck ./cppsource/".$filename." 2>&1");
	echo "<pre>$output</pre>";
	$write = fopen("./cppresult/".$filename.".txt","w");
	fwrite($write, $output);
	fclose($write);
?>