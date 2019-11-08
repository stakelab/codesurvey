# CodeSurvey

CodeSurvey allows to easily setup a survey for assessing code understandability.

## Before running
To setup the survey, you need to populate the SQL script `sql/seed.sql`. This script should:

- Add the snippets to evaluate
- Add the questions about the snippets
- Add the possible answers to each question
- Add other related classes

See `sql/structure.sql` for details.

## Run
To run the application, both docker and docker-compose are needed. Once installed, use the following command to run everything in background:

`docker-compose up -d`

This will build the webapp and create two containers: one with the webapp itself and one with a MySQL database (already configured with the schema and the tables). To stop the application, simply run:

`docker-compose down`

## Security
Note that the default configuration is mostly for development and testing purposes: extra security configuration may be needed for production.
