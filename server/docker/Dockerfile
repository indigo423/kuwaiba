# Consider using a specific version of Alpine Linux instead of the latest version to ensure consistent behavior across deployments
FROM alpine:latest
LABEL org.opencontainers.image.authors="contact@neotropic.co" \
      org.opencontainers.image.vendor="Neotropic SAS" \
      org.opencontainers.image.licenses="EPLv1,GPLv3" \
      org.opencontainers.image.title="Kuwaiba Open Network Inventory" \
      org.opencontainers.image.description="The first and only open-source network inventory system for the telecommunications industry" \
      org.opencontainers.image.version="2.1.1" \
      org.opencontainers.image.url="https://www.kuwaiba.org"

# Install dependencies
RUN apk add --no-cache fontconfig-dev openjdk11-jdk npm maven subversion unzip ttf-dejavu

# Create user "kuwaiba" so it can run all processes. Shell access will be disabled by default
RUN adduser -D -s /sbin/nologin kuwaiba

# Create base directories
# /data will store the logs, database, the process definitions, background files and attachments
# /opt/programs will contain all programs and their dependencies
RUN mkdir -p /data/logs/scheduling \
    && mkdir -p /data/logs/kuwaiba \
    && mkdir -p /data/logs/sync \
    && mkdir -p /data/db \
    && mkdir -p /data/files/attachments \
    && mkdir -p /data/img/backgrounds \
    && mkdir -p /opt/programs/kuwaiba/server \
    && mkdir -p /opt/programs/kuwaiba/components

# The working directory
WORKDIR /opt/programs

# Download Kuwaiba server and components
RUN svn checkout svn://svn.code.sf.net/p/kuwaiba/code/server/trunk/ kuwaiba/server/ \
    && svn checkout svn://svn.code.sf.net/p/kuwaiba/code/components/ace-editor-flow/ace-editor-flow-root/ace-editor-flow/ kuwaiba/components/ace-editor-flow \
    && svn checkout svn://svn.code.sf.net/p/kuwaiba/code/components/gantt-chart-flow/trunk/gantt-chart-flow-root/gantt-chart-flow/ kuwaiba/components/gantt-chart-flow \
    && svn checkout svn://svn.code.sf.net/p/kuwaiba/code/components/gcharts-flow/trunk/gcharts-flow-root/gcharts-flow/ kuwaiba/components/gcharts-flow \
    && svn checkout svn://svn.code.sf.net/p/kuwaiba/code/components/gmaps-flow/trunk/gmaps-flow-root/gmaps-flow/ kuwaiba/components/gmaps-flow \
    && svn checkout svn://svn.code.sf.net/p/kuwaiba/code/components/mxgraph-flow/mx-graph-flow-root/mx-graph-flow/ kuwaiba/components/mx-graph-flow \
    && svn checkout svn://svn.code.sf.net/p/kuwaiba/code/components/olmap-flow/trunk/olmap-flow-root/olmap-flow/ kuwaiba/components/olmap-flow \
    && svn checkout svn://svn.code.sf.net/p/kuwaiba/code/components/paper-autocomplete-flow/paper-autocomplete-flow/ kuwaiba/components/paper-autocomplete-flow \
    && svn checkout svn://svn.code.sf.net/p/kuwaiba/code/components/paper-dialog-flow/paper-dialog-flow/ kuwaiba/components/paper-dialog-flow \
    && svn checkout svn://svn.code.sf.net/p/kuwaiba/code/components/paper-toggle-button-flow/paper-toggle-button-flow/ kuwaiba/components/paper-toggle-button-flow

# Compilation and packaging of each of the components
RUN mvn -f kuwaiba/components/ace-editor-flow/ clean install \
    && mvn -f kuwaiba/components/gantt-chart-flow/ clean install \
    && mvn -f kuwaiba/components/gcharts-flow/ clean install \
    && mvn -f kuwaiba/components/gmaps-flow/ clean install \
    && mvn -f kuwaiba/components/mx-graph-flow/ clean install \
    && mvn -f kuwaiba/components/olmap-flow/ clean install \
    && mvn -f kuwaiba/components/paper-autocomplete-flow/ clean install \
    && mvn -f kuwaiba/components/paper-dialog-flow/ clean install \
    && mvn -f kuwaiba/components/paper-toggle-button-flow/ clean install

# Compilation and packaging of Kuwaiba server
RUN mvn -f kuwaiba/server/ --no-transfer-progress clean install \
    && mvn --also-make --projects webclient -f kuwaiba/server/ -Pproduction --no-transfer-progress install

# Move the Kuwaiba server jar to the working directory
RUN mv kuwaiba/server/webclient/target/kuwaiba_server_2.1.1-stable.jar  ./

# Extract sample database. Use 01_empty_kuwaiba.db.zip if you want a completely clean database
RUN unzip kuwaiba/server/dbs/03_data_sample_kuwaiba.db.zip && \
    mv kuwaiba.db /data/db/

# Move process definitions
RUN mv kuwaiba/server/samples/procman/ /data/processEngine/

# Clean up
RUN rm -rf /opt/programs/kuwaiba

# Remove dependencies
RUN apk del npm maven subversion unzip

# Set permissions for user kuwaiba
RUN chown -R kuwaiba:kuwaiba /data /opt/programs

# Expose the ports. 8080 will give you access to the application (web interface), while
# 8081 will give you access to the SOAP-based web service interface
EXPOSE 8080 8081

# Switch to user kuwaiba
USER kuwaiba
# Set the command to start Kuwaiba and redirect the output to a log file
CMD ["sh", "-c", "java -jar /opt/programs/kuwaiba_server_2.1.1-stable.jar > /data/logs/kuwaiba_$(date +%Y%m%d_%H%M%S).log 2>&1"]