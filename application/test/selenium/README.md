General
-------
These testcases can be run from the Firefox Selenium IDE.
To run test, open a folder and open the testsuite in the Selenium IDE, the correct
testcases will be loaded with the testsuite.

Requirements
------------
The app must run on 127.0.0.1:9000

Requirements for each testsuite
-------------------------------
* database: 
	* logged in as an ADMINISTRATOR user
* login:
	* not logged in, user exists with id = test13 and password = test1, Run speed != fast
* forgotpwd: 
	* not logged in, user exists with id = test13, no email address and no classes, language = Dutch
* user-settings:	 
	* logged in as user with id = test13, who has name = test and language = Dutch, password = test1
* classmgmt: 
	* logged in as a TEACHER user, teacher account tteacher to add as help teacher, pupil account ttester to add, existing school and class 
* usermgmt:
	* logged in as an ADMINISTRATOR user, pupil account with pwd=albert on 2nd pace in user overview	 
* competitionmgmt:
	* logged in as an ADMINISTRATOR user, competition=Test competition with questions=Test bundel for delete and edit
* statistics:
	* logged in as PUPIL user (avandam)
