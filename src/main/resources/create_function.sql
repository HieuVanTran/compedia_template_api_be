DELIMITER //

CREATE OR REPLACE FUNCTION getFullPath ( resourePath VARCHAR(255), staticContext VARCHAR(255), fallbackPath VARCHAR(255) )
RETURNS VARCHAR(255)

BEGIN

	DECLARE fullPath varchar(255);

	if resourePath is not null then
		set fullPath = concat(staticContext, resourePath);
	else 
		set fullPath = concat(staticContext, fallbackPath);
	end if;
   
	RETURN fullPath;

END; //

DELIMITER ;
