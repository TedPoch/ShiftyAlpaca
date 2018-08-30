***ShiftyAlpaca***
A Java/Spring application aimed at performing safety/speed checks on SQL database queries
| Univ. of Utah, School of Computing
| Master's of Software Development Capstone
| Ted Pochmara

_______________________________________
PROBLEM DOMAIN & MINIMUM VIABLE PRODUCT
An local corporation's technical operations team seeks an application that will enable the company’s software developers to perform an initial analysis of new SQL queries before use in production. The tool will determine whether queries are safe and efficient, recommending collaboration with a database admin (DBA) as necessary. Currently, a team of DBAs performs these analyses manually on behalf of 150 software developers from various teams. While the reviews are a necessary precaution, it’s no surprise that they impact software development velocity and eat up valuable DBA resources.
_______________
SOLUTION VALUE:
This tool will serve to mitigate toil on the DBA team by transforming a manual, repetitive, automatable task into a self-service process for use by software developers. It will free up DBAs to focus on more valuable pursuits such as database monitoring, optimization, and modernization.

A. MVP

 1. The scope of the project is highly open-ended and scalable. Tentative plan includes the following features:

  * Determine if SQL queries are making effective use of the RDBMS schema design, specifically the existing indexes
  * Analyzer will account for table sizes and other characteristics when analyzing queries
  * Blocked queries should be easily detectable

_____________
STRETCH GOALS
There are several opportunities to expand the base project, time and resources permitting. The application could be rolled into a larger suite of automated database analysis tools that offer a playbook of changes to recommend to software developers.

And while it would considerably extend the scope of the project, I see potential in providing not only automated query analysis, but also an automated schema change feature with an accompanying monitoring mechanism. Both would continue to involve the DBA team, but from an “approve and observe” standpoint instead of the traditional DBA gatekeeper model.


