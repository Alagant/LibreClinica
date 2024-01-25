#!/usr/bin/env bash
# Adds function and trigger for PID
mydir="$(dirname "$0")"
echo Adding function and trigger for PID
cd ${mydir}/.. && /usr/bin/docker compose run postgres psql -d libreclinica -U clinica -h postgres -f /docker-folder/PID_fun_and_trigger.sql