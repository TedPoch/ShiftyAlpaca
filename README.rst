=========
SQL analyser
=========
Functional Documentation (WIP)
------------------------
| Ted Pochmara
| Last Updated: 12 December 2018

Summary
-------
This SQL statement analysis application should only serve as triage tool, obviating the need for a software developer to visit with a DBA for each and every SQL statement they plan to commit to their application repository. The SQL analyzer accepts a query from the developer, executes the statement as an EXPLAIN diagnostic, runs rudimentary logic to detect obvious dangers, and urges the developer to coordinate with a DBA only when danger is detected.

Functional Requirements
----------------------
1. Accept SQL statements from users in order to run EXPLAIN diagnostic on their behalf

   * Statement should be in correct syntax for DBMS in question
   
     * The application will append the EXPLAIN keyword
     
2. Application will record queries and their results in connection with user credentials
   
     * user id
     * team id
     * query date, query string, EXPLAIN results
     
   * Admin can query for history of user queries
