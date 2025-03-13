 
Steps to run the test case after making changes in the salesperson-app :

1. Make the changes in the salesperson-app.
2. Delete the target folder.
3. Delete the salesperson-app in docker-desktop.
4. Delete the mysql in docker-desktop.
5. Run the command mvn package -DskipTests=true
6. docker compose up
7. Run the API Automated test case.