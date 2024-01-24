#!/usr/bin/env bash
# Script to backup restore LibreClinica database to another state
# This is a nuke option, it will destroy all data in the database
#echo dn "$(dirname "$0")"
backupfilename=/docker-folder/libreclinica_2023-12-24T01_20_23-06_00.sql
./db_backup.bash
echo Renewing database
cd "$(dirname "$0")"/.. && /usr/bin/docker compose run postgres psql -d postgres -U clinica -h postgres -c "DROP DATABASE IF EXISTS libreclinica;"
cd "$(dirname "$0")"/.. && /usr/bin/docker compose run postgres psql -d postgres -U clinica -h postgres -c "CREATE DATABASE libreclinica;"
echo Restoring database from $backupfilename
cd "$(dirname "$0")"/.. && /usr/bin/docker compose run postgres psql -d libreclinica -U clinica -h postgres -f $backupfilename
