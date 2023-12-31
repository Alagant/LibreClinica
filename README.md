LibreClinica
============

# Docker System Requirements


| Application Server | Java       | Database      | 
|--------------------|------------|---------------|
| ```tomcat:7-jdk8-slim```           | ```maven:3.5.0-jdk-8``` | ```postgres:11``` |



_the real open source electronic data capture (EDC) for clinical studies._

[LibreClinica](https://libreclinica.org) is the community driven successor of OpenClinica. It is an open source clinical trial software for Electronic Data Capture (EDC) and Clinical Data Management (CDM). 

### Getting Started

Check out [LibreClinica Documentation](https://libreclinica.org/documentation) to learn how to [install](https://libreclinica.org/documentation/install.html), [validate](https://libreclinica.org/documentation#Tests) and [use](https://libreclinica.org/documentation/manuals.html) the software.

#### System Requirements

These versions of required software packages are currently available in Debian 10 (Buster) Linux.

| Application Server | Java       | Database      | 
|--------------------|------------|---------------|
| Tomcat 9           | OpenJDK 11 | PostgreSQL 11 |

> **_NOTE:_** LibreClinica SOAP web API is legacy, not tested and not actively developed.

### Development

[LibreClinica Aims](https://libreclinica.org/goals.html) define main activities where community members can actively participate in the development process. In case of interest in contributing into the codebase, check out [LibreClinica Developer Documentation](https://libreclinica-docs.readthedocs.io) to learn how to [set up the development workstation](https://libreclinica-docs.readthedocs.io/en/latest/dev/dev-machine.html) and understand the [branching strategy](https://libreclinica-docs.readthedocs.io/en/latest/dev/developer.html) that is used to control the contribution workflows.

### Installation
To set up a permanent server, you can copy the file install/Libreclinica_docker.service to the folder /etc/systemd/system and run `systemctl enable Libreclinica_docker.service` to enable the service. Then you can start the service with `systemctl start Libreclinica_docker.service`. The service will automatically start on boot.
### Database backup and migration
If you want to back up your LibreClinica database, you can use the following command:
`docker compose run postgres pg_dump -d libreclinica -U clinica -h postgres>libreclinica_$(date -Iseconds).sql`
Note that Windows does not support file names with colons, so you might want to replace the colons with underscores.
To restore a database, you can use the following command:
`docker compose run postgres psql -d libreclinica -U clinica -h postgres -f libreclinica<backup time>.sql`,
for example:
`docker compose run postgres psql -d libreclinica -U clinica -h postgres -f libreclinica_2023-12-24T01_20_23-06_00.sql`,

### Contributions
                          
LibreClinica is open source project that values input from contributors:

* Julia Bley, University Hospital RWTH Aachen
* Thomas Hillger, University Hospital RWTH Aachen
* Gerben Rienk Visser, Trial Data Solutions
* Tomas Skripcak, DKFZ Partner Site Dresden - member of the German Cancer Consortium (DKTK)
* Ralph Heerlein, ReliaTec GmbH
* Christian Hänsel, ReliaTec GmbH
* Otmar Bayer, ReliaTec GmbH 
