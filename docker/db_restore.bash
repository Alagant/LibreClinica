#!/usr/bin/env bash
# Script to backup restore LibreClinica database to another state
# This is a nuke option, it will destroy all data in the database
mydir="$(dirname "$0")"
backupfilename=/docker-folder/libreclinica_2023-12-24T01_20_23-06_00.sql
${mydir}/db_backup.bash
echo Renewing database
cd ${mydir}/.. && /usr/bin/docker compose run postgres psql -d postgres -U clinica -h postgres -c "DROP DATABASE IF EXISTS libreclinica;"
cd ${mydir}/.. && /usr/bin/docker compose run postgres psql -d postgres -U clinica -h postgres -c "CREATE DATABASE libreclinica;"
echo Restoring database from $backupfilename
cd ${mydir}/.. && /usr/bin/docker compose run postgres psql -d libreclinica -U clinica -h postgres -f $backupfilename
