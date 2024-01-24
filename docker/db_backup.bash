#!/usr/bin/env bash
# Script to backup LibreClinica database
#echo dn "$(dirname "$0")"
dir=$HOME/dbbackup
backupfilename=libreclinica_$(date -Iseconds).sql
echo Backing up the database to /home/azureuser/dbbackup/$backupfilename
cd "$(dirname "$0")"/.. && /usr/bin/docker compose run postgres pg_dump -d libreclinica -U clinica -h postgres>/home/azureuser/dbbackup/$backupfilename
