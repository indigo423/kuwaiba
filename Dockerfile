FROM eclipse-temurin:11.0.25_9-jdk-noble

# Create user "kuwaiba" so it can run all processes. Shell access will be disabled by default
RUN adduser --system --home /opt/programs kuwaiba && \
    mkdir /data && \
    chown -R kuwaiba /data

USER kuwaiba

# Create base directories
# /data will store the logs, database, the process definitions, background files and attachments
# /opt/programs will contain all programs and their dependencies
RUN mkdir -p /data/logs/scheduling \
             /data/logs/kuwaiba \
             /data/logs/sync \
             /data/db \
             /data/files/attachments \
             /data/img/backgrounds

# The working directory
WORKDIR /opt/programs

ADD --chown=kuwaiba server/webclient/target/kuwaiba_server_2.1.2-SNAPSHOT-stable.jar  /opt/programs

# Extract sample database. Use 01_empty_kuwaiba.db.zip if you want a completely clean database
ADD --chown=kuwaiba kuwaiba.db/ /data/db/kuwaiba.db

# Move process definitions
ADD --chown=kuwaiba server/samples/procman/ /data/processEngine/

# Expose the ports. 8080 will give you access to the application (web interface), while
# 8081 will give you access to the SOAP-based web service interface
EXPOSE 8080 8081

# Switch to user kuwaiba
USER kuwaiba
# Set the command to start Kuwaiba and redirect the output to a log file
CMD ["sh", "-c", "java -jar /opt/programs/kuwaiba_server_2.1.2-SNAPSHOT-stable.jar"]

LABEL org.opencontainers.image.authors="contact@neotropic.co" \
      org.opencontainers.image.vendor="Neotropic SAS" \
      org.opencontainers.image.licenses="EPLv1,GPLv3" \
      org.opencontainers.image.title="Kuwaiba Open Network Inventory" \
      org.opencontainers.image.description="The first and only open-source network inventory system for the telecommunications industry" \
      org.opencontainers.image.version="2.1.2-SNAPSHOT" \
      org.opencontainers.image.url="https://www.kuwaiba.org"
