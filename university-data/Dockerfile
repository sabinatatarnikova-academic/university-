# Use the official PostgreSQL image from the Docker Hub
FROM postgres:16

# Set environment variables
ENV POSTGRES_USER=university_user
ENV POSTGRES_PASSWORD=password
ENV POSTGRES_DB=university

# Copy any necessary SQL files to initialize the database
COPY src/main/resources/db/migration /docker-entrypoint-initdb.d/

# Expose the default PostgreSQL port
EXPOSE 5432

# Set the default command to run PostgreSQL
CMD ["postgres"]