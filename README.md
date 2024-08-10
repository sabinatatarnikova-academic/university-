# university-cms

This project is designed to manage university schedules, catering to three user groups: students, teachers, and
administrators. Students can view their personalized schedules, while teachers have the capability to view and modify
their own schedules as well as those of their colleagues. Administrators have broader control, including user
management, schedule creation, and class assignments.

The database structure includes the following key tables:

- **groups**: Stores information about student groups.
- **users**: Contains user details, including type (student, teacher, or admin) and group affiliations.
- **courses**: Holds course information.
- **schedule**: Manages the overall scheduling framework for groups within specific semester dates.
- **global_classes**: Defines recurring class sessions within the schedule, including timing, day of the week, and
  regularity.
- **classes**: Represents individual class sessions generated from global classes, linked to specific courses, users,
  and locations.
- **locations**: Specifies classroom and department details for offline classes.
- **schedule_times**: Defines time slots for lectures, providing consistency across the scheduling process.

This structure enables efficient schedule management and ensures that all users have access to the necessary information
based on their roles.

### How to Run the Project

This project uses Docker and Docker Compose to set up and run the necessary services, including a PostgreSQL database
and a Spring Boot application. Follow the steps below to get the project up and running:

1. **Ensure Docker and Docker Compose are Installed**:
   - If you do not have Docker installed, please follow the installation guide on the Docker website.

2. **Navigate to the Project Directory**:
   - Open a terminal and navigate to the root directory of the project, where the `docker-compose.yml` file is located.

3. **Start the Project with Docker Compose**:
   - Run the following (```docker-compose up --build```) command to build and start all the containers specified in
     the `docker-compose.yml` file:

   - This command will build the Docker images and start the containers for both the PostgreSQL database and the Spring
     Boot application.

4. **Verify the Setup**:
   - Docker Compose will display the logs of each service in the terminal. Ensure that all services start without
     errors.
   - The application should be accessible at [http://localhost:8080](http://localhost:8080).
   - The PostgreSQL database will be available on port 5432.

5. **Stopping the Project**:
   - To stop the running containers, press `Ctrl+C` in the terminal.
   - To completely stop and remove the containers, run: ``` docker-compose down ```
