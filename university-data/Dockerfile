FROM postgres:16

ENV POSTGRES_USER=common_university_user
ENV POSTGRES_PASSWORD=password
ENV POSTGRES_DB=university

COPY src/main/resources/db/migration /docker-entrypoint-initdb.d/

EXPOSE 5432

CMD ["postgres"]
