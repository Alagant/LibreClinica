#!/usr/bin/env bash
# Test the database queries
mydir="$(dirname "$0")"
echo Testing database queries
cd ${mydir}/.. && /usr/bin/docker compose run postgres psql -d libreclinica -U clinica -h postgres -f /docker-folder/PID_fun_and_trigger.sql