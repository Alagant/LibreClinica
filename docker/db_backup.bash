#!/usr/bin/env bash
# Script to backup LibreClinica database
dir="$(dirname "$0")"
dir=$HOME/dbbackup
backupfilename=libreclinica_$(date -Iseconds).sql
echo Backing up the database to /home/azureuser/dbbackup/$backupfilename
cd ${dir}/.. && /usr/bin/docker compose run postgres pg_dump -d libreclinica -U clinica -h postgres>/home/azureuser/dbbackup/$backupfilename
